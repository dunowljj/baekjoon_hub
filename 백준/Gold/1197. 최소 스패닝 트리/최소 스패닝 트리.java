import javax.management.remote.rmi.RMIJRMPServerImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        List<Edge>[] adjList = new ArrayList[V + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            adjList[A].add(new Edge(B, C));
            adjList[B].add(new Edge(A, C));
        }

        boolean[] visited = new boolean[V + 1];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingLong(Edge::getWeight));
        pq.offer(new Edge(1, 0));

        long minCost = 0;
        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            if (visited[now.end]) continue;
            visited[now.end] = true;
            minCost += now.weight;

            for (Edge next : adjList[now.end]) {
                if (!visited[next.end]) {
                    pq.offer(next);
                }
            }
        }

        System.out.print(minCost);
    }

    static class Edge {
        int end;
        long weight;

        public Edge(int end, int weight) {
            this.end = end;
            this.weight = weight;
        }

        public long getWeight() {
            return weight;
        }
    }
}
