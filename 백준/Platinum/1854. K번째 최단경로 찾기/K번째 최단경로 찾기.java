import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static PriorityQueue<Integer>[] dists;
    static List<Node>[] adj;
    static int n,m,k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dists = new PriorityQueue[n + 1];
        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            dists[i] = new PriorityQueue<>(Comparator.reverseOrder());
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            adj[a].add(new Node(b, c));
        }

        // k번째 최단 경로가 존재하지 않으면 -1 출력
        // 1번 -> 1번 경로가 -1이 될 수 있음. k번째가 아니라서.
        // 자기 자신까지 거리가 0인건 맞음.
        dists[1].add(0);
        dijkstra(1);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (dists[i].size() < k) sb.append(-1);
            else sb.append(dists[i].peek());

            sb.append(System.lineSeparator());
        }

        System.out.print(sb);
    }

    private static void dijkstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt((n) -> n.weight));
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();


            for (Node next : adj[curr.no]) {

                PriorityQueue<Integer> nodePQ = dists[next.no];

                int newWeight = curr.weight + next.weight;
                if (nodePQ.size() < k) {
                    nodePQ.offer(newWeight);
                    pq.offer(new Node(next.no, newWeight));
                } else if (nodePQ.peek() > newWeight) {
                    nodePQ.poll();
                    nodePQ.offer(newWeight);
                    pq.offer(new Node(next.no, newWeight));
                }

            }
        }
    }

    static class Node {
        int no;
        int weight;

        public Node(int no, int weight) {
            this.no = no;
            this.weight = weight;
        }
    }
}
