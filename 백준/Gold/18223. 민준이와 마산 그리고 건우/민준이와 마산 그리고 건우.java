import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static List<Node>[] adj;
    static int[] dist, distP;
    static int V,E,P;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken()); // 건우 위치

        adj = new ArrayList[V + 1];
        for (int i = 0; i < V + 1; i++) {
            adj[i] = new ArrayList<>();
        }

        dist = new int[V + 1];
        distP = new int[V + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(distP, Integer.MAX_VALUE);

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            adj[a].add(new Node(b, c));
            adj[b].add(new Node(a, c));
        }

        dist[1] = 0;
        distP[P] = 0;

        daijkstra(1, dist);
        daijkstra(P, distP);

        if (dist[V] == distP[1] + distP[V]) {
            System.out.print("SAVE HIM");
        } else {
            System.out.print("GOOD BYE");
        }
    }

    private static void daijkstra(int start, int[] dist) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        boolean[] visited = new boolean[V + 1];

        while (!pq.isEmpty()) {
            Node curNode = pq.poll();

            if (visited[curNode.no]) continue;
            visited[curNode.no] = true;

            for (Node next : adj[curNode.no]) {
                if (dist[next.no] > curNode.weight + next.weight) {
                    dist[next.no] = curNode.weight + next.weight;
                    pq.offer(new Node(next.no, dist[next.no]));
                }
            }
        }
    }

    static class Node implements Comparable<Node> {
        int no;
        int weight;

        public Node(int no, int weight) {
            this.no = no;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }
}
