import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import static java.util.Comparator.comparingInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[][] universe = new int[N + 1][N + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 1; j < N + 1; j++) {
                universe[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        boolean[] visited = new boolean[N + 1];
        PriorityQueue<Flow> pq = new PriorityQueue<>(comparingInt(Flow::getCost));
        pq.offer(new Flow(1, 0));

        long minCost = 0;
        while (!pq.isEmpty()) {
            Flow now = pq.poll();
            if (visited[now.end]) continue;

            visited[now.end] = true;
            minCost += now.cost;

            for (int i = 1; i < universe[now.end].length; i++) {
                if (!visited[i]) {
                    pq.offer(new Flow(i, universe[now.end][i]));
                }
            }
        }

        System.out.print(minCost);
    }

    static class Flow {
        int end;
        int cost;

        public Flow(int end, int cost) {
            this.end = end;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }
    }
}
/**
 * C는 1~1억
 * 인접행렬 형식으로 주어진다.
 * 인접행렬은 최대 100만개 요소를 가진다.
 *
 * i==j 무시
 * 유지비용만 체크
 * 유지 비용은 1부터. 0이면 무시한다.
 * 대칭하는 부분은 당연히 값이 같다.
 */