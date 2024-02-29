import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());


        List<Edge>[] adjList = new ArrayList[N + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            adjList[a].add(new Edge(b, c));
            adjList[b].add(new Edge(a, c));
        }

        boolean[] visited = new boolean[N + 1];
        PriorityQueue<Edge> pq = new PriorityQueue<>(comparingInt(Edge::getCost));
        pq.offer(new Edge(1, 0));

        int maxCost = Integer.MIN_VALUE;
        int totalCost = 0;
        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            if (visited[now.end]) continue;
            visited[now.end] = true;

            maxCost = Math.max(maxCost, now.cost);
            totalCost += now.cost;

            for (Edge next : adjList[now.end]) {
                if (!visited[next.end]) {
                    pq.offer(next);
                }
            }
        }

        System.out.print(totalCost - maxCost);
    }

    static class Edge {
        int end;
        int cost;

        public Edge(int end, int cost) {
            this.end = end;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }
    }
}
