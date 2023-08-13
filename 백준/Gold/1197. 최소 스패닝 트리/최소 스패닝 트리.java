import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        long weight;
        int vertex;

        public Node(long weight, int vertex) {
            this.weight = weight;
            this.vertex = vertex;
        }

        public long getWeight() {
            return weight;
        }
    }
    static List<Node>[] graph;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        graph = new ArrayList[V + 1];

        for (int i = 0; i < V + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            graph[A].add(new Node(C, B));
            graph[B].add(new Node(C, A));
        }

        visited = new boolean[V + 1];
        long total = 0L;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingLong(Node::getWeight));
        pq.offer(new Node(0L, 1));

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (visited[now.vertex]) continue;
            visited[now.vertex] = true;

            total += now.weight;

            List<Node> nexts = graph[now.vertex];

            for (int i = 0; i < nexts.size(); i++) {
                Node next = nexts.get(i);
                pq.offer(new Node(next.weight, next.vertex));
            }
        }

        System.out.print(total);
    }
}
