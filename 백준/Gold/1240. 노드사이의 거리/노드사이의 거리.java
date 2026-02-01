import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[][] adj;
    static int N,M;
    static int[] depths;
    static int[] parents;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        adj = new int[N + 1][N + 1];

        // 인접 그래프 생성
        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            adj[a][b] = weight;
            adj[b][a] = weight;
        }

        // 1번을 루트로 설정, root의 깊이와 부모 1로 설정
        depths = new int[N + 1];
        parents = new int[N + 1];
        parents[1] = 1;
        depths[1] = 1;
        initHeights(1, 2);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            int dist = findDist(start, end);

            sb
                    .append(dist)
                    .append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }

    private static void initHeights(int now, int height) {
        for (int next = 1; next < N + 1; next++) {
            if (adj[now][next] == 0) continue;
            if (depths[next] != 0) continue;

            depths[next] = height;
            parents[next] = now;
            initHeights(next, height + 1);
        }
    }

    private static int findDist(int start, int end) {
        int minDepth = Math.min(depths[start], depths[end]);

        int[] pd1 = find(start, minDepth);
        int[] pd2 = find(end, minDepth);

        int dist = pd1[1] + pd2[1];

        // 공통 부모가 아니라면, 한칸씩 같이 올라간다.
        int p1 = pd1[0];
        int p2 = pd2[0];

        while (p1 != p2) {
            dist += adj[p1][parents[p1]] + adj[p2][parents[p2]];

            p1 = parents[p1];
            p2 = parents[p2];
        }

        return dist;
    }

    private static int[] find(int now, int minDepth) {
        int upCount = depths[now] - minDepth;

        int parent = now;
        int dist = 0;
        while (upCount-- > 0) {
            parent = parents[now];
            dist += adj[now][parent];

            now = parent;
        }

        return new int[]{parent, dist};
    }

}
/**
 * N,M 최대 1000
 * 트리 구조 -> 루트를 찾을 방법은 없다.
 * - 특정 노드를 루트라고 지정하고, 기준으로 depth를 지정할 수는 있다.
 * - 즉, 두 노드의 공통 부모까지의 거리를 구하면 된다.
 */