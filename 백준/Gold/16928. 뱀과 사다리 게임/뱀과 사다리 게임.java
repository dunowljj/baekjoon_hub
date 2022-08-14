import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] board;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        visited = new boolean[101];
        board = new int[101]; // 1번칸부터 시작 (1~100 사용)
        for (int i = 0; i <= 100; i++) {
            board[i] = i;
        }

        for (int i = 0; i < N + M; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int dest = Integer.parseInt(st.nextToken());
            board[idx] = dest;
        }

        int answer = bfs(1, 0);
        bw.write(answer+"");
        bw.flush();
        bw.close();
    }
    static int bfs(int loc, int count) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{loc,count});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            loc = curr[0];
            count = curr[1];

            for (int i = 1; i <= 6; i++) {

                if (loc >= 94) {
                    return count + 1;
                }

                int idx = loc + i;
                int dest = board[idx];

                if (!visited[dest]) {
                    visited[dest] = true;
                    queue.add(new int[]{dest, count + 1});
                }
                //100넘을시도 생각, 94이상일 경우
            }
        }
        return 0;
    }

}
/*
1번, 100번은 뱀이나 사다리 연결이 없다.
-> 주사위도 조작가능하기 때문에 현재 위치가 94이상일 경우, 100에 도착할 수 있다.

## 범위
1 <= N,M <= 15
경우의 수가 너무 많지 않은가?


## 풀이
단순 생각으로는 bfs가 효율적이어 보인다. 도착좌표에 대해 방문체크를 하면된다.

좌표 이동
먼저 int 배열의 해당 좌표 인덱스에 연결된 좌표 값 넣기 :  같은 값 넣어놓기 -> 100번 연산

 */