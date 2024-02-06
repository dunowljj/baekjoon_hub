import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        int[] parent = new int[N + 1];
        for (int i = 1; i < parent.length; i++) parent[i] = i;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            pq.offer(new Edge(a, b, cost));
        }

        int cost = 0;
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();

            if (find(parent, edge.a) != find(parent, edge.b)) {
                union(parent, edge.a, edge.b);
                cost += edge.cost;
            }

        }

        System.out.print(cost);
    }

    private static int find(int[] parent, int no) {
        if (parent[no] == no) return no;
        else return parent[no] = find(parent, parent[no]);
    }

    private static void union(int[] parent, int a, int b) {
        int p1 = find(parent, a);
        int p2 = find(parent, b);

        if (p1 != p2) {
            parent[p1] = p2;
        }
    }

    static class Edge implements Comparable<Edge> {
        int a;
        int b;
        int cost;

        public Edge(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return this.cost - e.cost;
        }
    }
}
/**
 * 모든 컴퓨터 직간접적 연결 필요
 * 최소 비용 -> MST
 *
 * 최대 비용 합 10억 (int)
 */