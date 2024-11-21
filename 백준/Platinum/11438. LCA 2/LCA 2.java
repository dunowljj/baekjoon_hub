import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int K = (int) (Math.log(N) / Math.log(2));

        List<Integer>[] adjList = new ArrayList[N + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adjList[a].add(b);
            adjList[b].add(a);
        }

        int[][] parent = new int[N + 1][K + 1]; parent[1][0] = 0;
        int[] depth = new int[N + 1]; // 루트노드 1의 depth는 0이다.

        findDepth(parent, depth, adjList, 1);
        findParent(parent, N, K);

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            sb.append(findLCA(parent, depth, K, a, b))
                    .append(System.lineSeparator());
        }

        System.out.println(sb.toString().trim());
    }

    private static void findDepth(int[][] parent, int[] depth, List<Integer>[] adjList, int start) {
        for (int next : adjList[start]) {
            if (next == parent[start][0]) continue;

            parent[next][0] = start;
            depth[next] = depth[start] + 1;
            findDepth(parent, depth, adjList, next);
        }
    }

    // 실패 -> 낮은 k부터 순차적으로 채워야한다.
//    private static void findParent(int[][] parent, int N, int K) {
//        for (int n = 2; n <= N; n++) { //루트는 탐색 안해도됨
//            for (int k = 1; k <= K; k++) {
//                parent[n][k] = parent[parent[n][k - 1]][k - 1];
//            }
//        }
//    }

    private static void findParent(int[][] parent, int N, int K) {
        for (int k = 1; k <= K; k++) {
            for (int n = 2; n <= N; n++) { //루트는 탐색 안해도됨
                parent[n][k] = parent[parent[n][k - 1]][k - 1];
            }
        }
    }

    private static int findLCA(int[][] parent, int[] depth, int K, int a, int b) {
        // depth[a]가 더 크도록 a,b를 설정
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        // 높이 맞추기
        for (int k = K; k >= 0; k--) {
            if ((1 << k) <= depth[a] - depth[b]) {
                a = parent[a][k];
            }
        }

        if (a == b) return a;

        // 공통조상 직전까지 올라가기
        for (int k = K; k >= 0; k--) {
            if (parent[a][k] != parent[b][k]) {
                a = parent[a][k];
                b = parent[b][k];
            }
        }

        return parent[a][0];
    }
}
