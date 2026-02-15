import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";
    public static final int ZERO = 0;

    static int N, M, K;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        initParent();

        List<Edge> edges = new ArrayList<>();
        for (int weight = 1; weight <= M; weight++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            edges.add(new Edge(x, y, weight));
        }

        Collections.sort(edges, Comparator.comparingInt(e -> e.weight));

        StringBuilder scores = new StringBuilder();
        int start = 0;
        while (K-- > 0) {
            int totalWeight = 0;
            int count = 0;
            initParent();

            for (int i = start; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                if (union(edge)) {
                    totalWeight += edge.weight;
                    count++;
                }
            }

            if (count == N - 1) {
                scores.append(totalWeight).append(SPACE);
                start++;

            } else {
                while (K-- >= 0) {
                    scores.append(ZERO).append(SPACE);
                }
                break;
            }
        }

        System.out.print(scores.toString().trim());
    }

    private static void initParent() {
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
    }

    private static boolean union(Edge edge) {
        int px = find(edge.x);
        int py = find(edge.y);

        if (px != py) {
            parent[px] = py;
            return true;
        }

        return false;
    }

    private static int find(int no) {
        if (parent[no] == no) return no;
        return parent[no] = find(parent[no]);
    }

    static class Edge {
        int x;
        int y;
        int weight;

        public Edge(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }
    }
}
/**
 * 각 턴 마다,
 * 1) MST 체크
 * - MST 비용 점수에 반영.
 * - 완성 불가 시 0점
 *
 * 2) 턴 종료 시
 * - 가장 작은 간선 제거
 * - 제거된 간선 사용 불가.
 *
 *
 * ### 최종 풀이
 * - 크루스칼 알고리즘 사용하여 mst 생성
 * - 각 턴마다 맨 처음 사용한 최소 간선 제거하면서 새로운 mst 생성 시도
 * 크루스칼 10^6 * log10^6 * K(100)
 *
 */