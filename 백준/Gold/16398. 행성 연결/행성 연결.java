import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

import static java.util.Comparator.comparingInt;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        if (N == 1) {
            System.out.print(0);
            System.exit(0);
        }

        int[] parent = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            parent[i] = i;
        }
        // 크루스칼 방식 사용시, 반절만 초기화하고 진행하면 된다.
        PriorityQueue<Flow> pq = new PriorityQueue<>(comparingInt(Flow::getCost));

        // 0행과 0열엔 빈값만 있다. -> 1행부터 탐색
        for (int i = 0; i < N; i++) {
            String[] split = br.readLine().split(SPACE);

            // i==j 인부분 이후만 탐색, 대칭되는 부분도 무시한다.
            for (int j = i + 1; j < N; j++) {
                int cost = Integer.parseInt(split[j]);
                pq.offer(new Flow(i, j, cost));
            }
        }


        long minCost = 0;
        while (!pq.isEmpty()) {
            Flow now = pq.poll();

            if (find(parent, now.startNo) != find(parent, now.endNo)) {
                union(parent, now.startNo, now.endNo);
                minCost += now.cost;
            }
        }

        System.out.print(minCost);
    }

    private static int find(int[] parent, int no) {
        if (parent[no] == no) return no;
        else return parent[no] = find(parent, parent[no]);
    }

    private static void union(int[] parent, int startNo, int endNo) {
        int p1 = find(parent, startNo);
        int p2 = find(parent, endNo);

        if (p1 != p2) {
            parent[p2] = p1;
        }
    }

    static class Flow {
        int startNo;
        int endNo;
        int cost;

        public Flow(int startNo, int endNo, int cost) {
            this.startNo = startNo;
            this.endNo = endNo;
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