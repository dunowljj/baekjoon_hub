import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] visited = new int[100_001];

        // 시작 1 -> 순간이동시 1 처리, 마지막에 1빼기
        visited[N] = 1;
        bfs(N, K, visited);

        bw.write((visited[K] - 1)+"");
        bw.flush();
        bw.close();
    }

    static void bfs(int N, int K, int[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(N);

        while (!queue.isEmpty()) {
            N = queue.poll();

            if (N == K) {
                return;
            }

            // 순간이동
            while ((N * 2 <= K + 1 && visited[N * 2] == 0)) {
                visited[N * 2] = visited[N];
                queue.add(N * 2);
            }

            // -1
            if (N != 0 && visited[N - 1] == 0) {
                visited[N - 1] = visited[N] + 1;
                queue.add(N - 1);
            }

            // N이 더 크면 빼기만 해야됨
            if (N > K) continue;


            // + 1
            if (N + 1 <= K && visited[N + 1] == 0) {
                visited[N + 1] = visited[N] + 1;
                queue.add(N + 1);
            }


        }
    }
}
/*
순간이동이 초를 소모하지 않기 때문에, 많은 횟수를 소모해도 최솟값의 반열에 도달할 수 있는데,
현재 bfs탐색이라 한번씩 번갈아가며 실행이 된다.

순간이동은 초를 소모하지 않는다. N이 K의 log2K가 되도록 만들기만 하면, 0초만에 도착이 가능하다.
혹은 N이 K+1혹은 K-1의 약수가 되면 1초만에 도착이 가능하다.

1)역으로 K에서 N을 구하는 방법이 있다.
2)while문으로 순간이동을 안될떄까지 실행시키는 방법도 있다.
 */