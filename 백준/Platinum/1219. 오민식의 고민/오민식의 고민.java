import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Node {
        int start;
        int end;
        int cost;

        public Node(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }
    }

    static List<Node>[] graph;
    static long[] costs;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int startNo = Integer.parseInt(st.nextToken());
        int endNo = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] incomes = new int[N];
        costs = new long[N];

        graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }
//        graph = new ArrayList();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[start].add(new Node(start, end, cost));
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            incomes[i] = Integer.parseInt(st.nextToken());
        }

        if (hasInfiniteCycle(startNo, N, incomes) && costs[endNo] == Long.MAX_VALUE) {
            System.out.println("Gee");
            System.exit(0);
        }

        if (costs[endNo] == Long.MIN_VALUE) {
            System.out.println("gg");
            System.exit(0);
        }

        System.out.println(costs[endNo] + incomes[startNo]);
    }

    private static void printCost() {
        for (long cost : costs) {
            System.out.print(cost + " ");
        }
        System.out.println();
    }

    private static boolean hasInfiniteCycle(int src, int N, int[] incomes) {
        boolean cycle = false;
        Arrays.fill(costs, Long.MIN_VALUE);
        costs[src] = 0;

        for (int i = 0; i < N + 100; i++) {
            for (int start = 0; start < N; start++) {
                for (Node next : graph[start]) {
                    int income = incomes[next.end] - next.cost;

                    if (costs[start] == Long.MAX_VALUE) {
                        costs[next.end] = Long.MAX_VALUE;
                    }

                    else if (costs[start] != Long.MIN_VALUE && costs[next.end] < costs[start] + income) {
                        costs[next.end] = costs[start] + income;

                        if (i >= N) {
                            costs[next.end] = Long.MAX_VALUE;
                            cycle = true;
                        }
                    }
                }
            }
        }

        return cycle;
    }
}

/*
82분 +
 */

/*
- 도시는 방문마다 돈을 벌게 해준다.
- 모든 교통 수단은 주어진 방향으로만 이용될 수 있다. -> 버는돈 - 비용 = 가중치
- 여러번 이용 가능.
    - 양수 사이클 -> Gee
    - 도착 불가능 -> gg
    - 음수 사이클 -> 탐색법 사용?
 */