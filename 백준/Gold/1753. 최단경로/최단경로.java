import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String INF = "INF";
    static int[] dist;
    static List<Node>[] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(br.readLine());

        dist = new int[V + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        adj = new ArrayList[V + 1];
        for (int i = 0; i < V + 1; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            adj[u].add(new Node(v, w));
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.dist));
        boolean[] visited = new boolean[V + 1];
        pq.offer(new Node(K, 0));
        dist[K] = 0;

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (visited[now.no]) continue;
            visited[now.no] = true;

            for (Node next : adj[now.no]) {
                if (dist[next.no] > dist[now.no] + next.dist) {
                    dist[next.no] = dist[now.no] + next.dist;
                    pq.offer(new Node(next.no, dist[next.no]));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < dist.length; i++) {
            if (dist[i] != Integer.MAX_VALUE) sb.append(dist[i]);
            else sb.append(INF);

            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }

    static class Node {
        int no;
        int dist;

        public Node(int no, int dist) {
            this.no = no;
            this.dist = dist;
        }
    }
}
