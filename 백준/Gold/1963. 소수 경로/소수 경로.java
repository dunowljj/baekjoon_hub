import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static boolean[][][][] prime = new boolean[10][10][10][10];
    static boolean[] visited = new boolean[10_000];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        getPrime();

        int T = Integer.parseInt(br.readLine());

        StringTokenizer st;

        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            int value = (bfs(start, end));
            bw.write((value == -1 ? "Impossible\n" : value + "\n"));
        }




        bw.flush();
        bw.close();
    }

    private static void getPrime() {
        for (int i = 2; i <= Math.sqrt(10_000); i++) {
            int thousand = i / 1000;
            int hundred = i / 100 % 10;
            int ten = i / 10 % 10;
            int one = i % 10;

            if (prime[thousand][hundred][ten][one]) continue;

            for (int j = i * i; j < 10_000; j+= i) {
                thousand = j / 1000;
                hundred = j / 100 % 10;
                ten = j / 10 % 10;
                one = j % 10;
                prime[thousand][hundred][ten][one] = true;
            }
        }
    }

    private static int bfs(int start, int end) {
        Queue<Integer> queue = new LinkedList<>();
        visited = new boolean[10_000];
        queue.offer(start);
        visited[start] = true;

        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {

                int curr = queue.poll();

                if (curr == end) {
                    return count;
                }

                int thousand = curr / 1000;
                int hundred = curr / 100 % 10;
                int ten = curr / 10 % 10;
                int one = curr % 10;

                for (int i = 0; i < 10; i++) {
                    if (i != 0 && !prime[i][hundred][ten][one]) {
                        int num = (i * 1000) + (hundred * 100) + (ten * 10) + one;

                        if (!visited[num]) {
                            visited[num] = true;
                            queue.offer(num);
                        }
                    }

                    if (!prime[thousand][i][ten][one]) {

                        int num = (thousand * 1000) + (i * 100) + (ten * 10) + one;

                        if (!visited[num]) {
                            visited[num] = true;
                            queue.offer(num);
                        }
                    }

                    if (!prime[thousand][hundred][i][one]) {

                        int num = (thousand * 1000) + (hundred * 100) + (i * 10) + one;

                        if (!visited[num]) {
                            visited[num] = true;
                            queue.offer(num);
                        }
                    }

                    if (!prime[thousand][hundred][ten][i]) {

                        int num = (thousand * 1000) + (hundred * 100) + (ten * 10) + i;

                        if (!visited[num]) {
                            visited[num] = true;
                            queue.offer(num);
                        }
                    }
                }
            }
            count++;
        }
        return -1;
    }

}
/*
## 시간복잡도
소수 구하기 10000개, 최대 간선 10000개


## 풀이

1000 미만 허용 x -> 첫 자리가 0이 아니어야 한다.

소수 체크로 1~10000 배열 생성

3가지 수가 같은 경우의 노드끼리 간선이 연결되어 있다.

bfs라 먼저 도착한게 가장 적게 변환한것이다.

4차원배열 -> 불가능한경우 -> 1자리만 다른 경우가 존재하지 않을때
 */