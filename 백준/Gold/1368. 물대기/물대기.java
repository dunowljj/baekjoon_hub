import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        parent = new int[N + 1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        List<Edge> edges = new ArrayList<>();

        // 우물파는 비용을 가상노드 0으로 한다.
        for (int i = 1; i <= N; i++) {
            int cost = Integer.parseInt(br.readLine());
            edges.add(new Edge(i, 0, cost));
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int c = Integer.parseInt(st.nextToken());

                if (i < j) {
                    edges.add(new Edge(i, j, c));
                }
            }
        }

        Collections.sort(edges, Comparator.comparingInt(c -> c.cost));

        int total = 0;
        for (Edge edge : edges) {

            if (find(edge.n1) != find(edge.n2)) {
                union(edge);
                total += edge.cost;
            }
        }

        System.out.print(total);
    }

    private static int find(int no) {
        if (parent[no] == no) return no;
        else return parent[no] = find(parent[no]);
    }

    private static void union(Edge edge) {
        int p1 = find(edge.n1);
        int p2 = find(edge.n2);

        parent[p2] = p1;
    }

    static class Edge {
        int n1;
        int n2; // n2가 -1이면 우물
        int cost;

        public Edge(int n1, int n2, int cost) {
            this.n1 = n1;
            this.n2 = n2;
            this.cost = cost;
        }
    }
}
/**
 * mst인데 우물을 팔 것인지, 연결할 것인지 선택해야함.
 * 우물 가는 비용을 0번 노드로 설정해서 mst구하면 끝이다.
 */