import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {
        StringBuilder answer = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int numOfCase = Integer.parseInt(br.readLine());

        for (int i = 0; i < numOfCase; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()); // 컴퓨터 개수 == 노드 수
            int d = Integer.parseInt(st.nextToken()); // 의존성 개수 == 간선 수
            int c = Integer.parseInt(st.nextToken()); // 해킹당한 컴퓨터 == 탐색 시작점

            // init graph
//            boolean[] visited = new boolean[n + 1];
            int[] costs = new int[n + 1];
            Arrays.fill(costs, Integer.MAX_VALUE);

            List<Node>[] graph = new ArrayList[n + 1];
            for (int j = 0; j < n + 1; j++) {
                graph[j] = new ArrayList<>();
            }

            // init edge
            for (int j = 0; j < d; j++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int s = Integer.parseInt(st.nextToken());

                // a -> b 의존 :: b -> a 간선 존재하며 비용 s초
                graph[b].add(new Node(a, s));
            }

            // 시작 컴퓨터 체크
//            visited[c] = true;
            costs[c] = 0;

            // 다익스트라
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
            pq.offer(new Node(c, 0));

            while (!pq.isEmpty()) {
                Node now = pq.poll();
                for (Node next : graph[now.no]) {
                    if (costs[next.no] > costs[now.no] + next.cost) {
//                        visited[next.no] = true;
                        costs[next.no] = costs[now.no] + next.cost;
                        pq.offer(new Node(next.no, costs[next.no]));
                    }
                }
            }

            int max = 0;
            int count = 0;
            for (int cost : costs) {
                if (cost == Integer.MAX_VALUE) continue;

                count++;
                max = Math.max(max, cost);
            }

            // 정답 만들기
            answer.append(count)
                    .append(" ")
                    .append(max)
                    .append("\n");
        }

        System.out.print(answer);
    }

    private static void dijkstra(List<Node>[] graph, boolean[] visited, int start) {

    }

    static class Node {
        int no;
        int cost;

        public Node(int no, int cost) {
            this.no = no;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }
    }
}

/*
케이스 최대 100

VlogE?

단순 탐색이 아니었다. -> a노드를 b,c 노드가 의존한다고 했을때, 감염까지 시간이 각기 다를 수 있다.
-> 다익스트라 후에 가장 오래걸린 경로를 찾아야 한다.
-> 주의할 점은 방문체크를 하면 감염전파시간이 고려되지 않는다.
 */
