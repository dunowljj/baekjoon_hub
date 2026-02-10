import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";
    static int[][] adjArr;
    static int[] parent;
    static boolean[] connected;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        adjArr = new int[n + 1][n + 1];

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            union(x, y);
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                adjArr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        List<Edge> edges = new ArrayList<>();
        for (int i = 2; i <= n; i++) { // 1번 pc와의 연결은 고려하지 않는다.
            for (int j = i + 1; j <= n; j++) {
                if (find(i) == find(j)) continue;

                edges.add(new Edge(i, j, adjArr[i - 1][j - 1]));
            }
        }

        Collections.sort(edges, Comparator.comparingInt(e -> e.cost));

        int X = 0;
        int K = 0;
        StringBuilder answer = new StringBuilder();
        StringBuilder connectedNos = new StringBuilder();
        for (Edge edge : edges) {
            if (union(edge.x, edge.y)) {
                X += edge.cost;
                K++;
                connectedNos.append(edge.x).append(SPACE).append(edge.y).append(System.lineSeparator());
            }
        }

        answer.append(X).append(SPACE).append(K).append(System.lineSeparator())
                .append(connectedNos.toString().trim());

        System.out.print(answer);
    }

    private static boolean union(int x, int y) {
        int px = find(x);
        int py = find(y);

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
        int cost;

        public Edge(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}

/**
 * 1번은 본사 메인컴이고, 1번과 모두 연결되어 있음.
 *
 * [연결의 끊김]
 * 1) 1번이랑만 연결된 고립된 pc의 연결 끊김 -> 다른 pc와 추가 연결 필요
 * 2) 1번 제외한 pc끼리의 특정 연결 끊김 -> 1번경유해서 어짜피 다 연결됨.
 *
 * [컴퓨터의 고장]
 * 1) 1번이 고장난 경우 -> 나머지 컴퓨터들끼리 알아서 모두 연결되어야 한다.
 * 2) 1번 제외한 특정 컴퓨터가 고장난 경우 -> 1번을 경유해서 어짜피 연결된다.
 *
 * 결국 1번을 제외하고 mst만들기
 */