import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int K = (int) (Math.log(N) / Math.log(2));

        List<Integer>[] adjList = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            adjList[i] = new ArrayList<>();
        }

        // depth[K][N] = depth[K-1][depth[K-1][N]]
        int[][] parent = new int[N + 1][K + 1];
        int[] depth = new int[N + 1];


        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            adjList[n1].add(n2);
            adjList[n2].add(n1);
        }

        checkParentAndHeight(parent, depth, adjList, 1);
        checkPowHeight(parent, depth,N, K);

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            System.out.println(findLCA(parent, depth, n1, n2, K));
        }

//        printAdjList(adjList, depth);
//        printParentArr(parent);
    }

    private static void checkParentAndHeight(int[][] parent, int[] depth,  List<Integer>[] adjList, int start) {
        for (int next : adjList[start]) {
            if (next == parent[start][0]) continue; // 부모인 경우 갱신 x

            parent[next][0] = start;
            depth[next] = depth[start] + 1;

            checkParentAndHeight(parent, depth, adjList, next);
        }
    }

    private static void checkPowHeight(int[][] parent, int[] height, int N, int K) {
        for (int k = 1; k <= K; k++) {
            for (int n = 1; n <= N; n++) {
//                if (parent[n][k - 1] == 0) continue;
                parent[n][k] = parent[parent[n][k - 1]][k - 1];
            }
        }
    }

    private static int findLCA(int[][] parent, int[] depth, int a, int b, int k) {

        // 깊이를 n1 > n2로 설정
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        // 2^n 씩 높이 맞추기
        // 2^0=1, 2^1=2라서 역순으로 하면 알아서 높이가 맞춰진다.
        for (int i = k; i >= 0; i--) {
            if ((1 << i) <= depth[a] - depth[b]) {
                a = parent[a][i];
            }
        }

//        System.out.println("da : " + depth[a]);
//        System.out.println("db : " + depth[b]);

        if (a == b) return a;

        // 조상 찾기
        // 큰 점프가 불가능하다면 알아서 안되고 더 작은 점프를 하게된다.
        for (int i = k; i >= 0; i--) {
            if (parent[a][i] != parent[b][i]) {
                a = parent[a][i];
                b = parent[b][i];
            }
        }

//        System.out.println("a = " + a);
//        System.out.println("b = " + b);
//        System.out.println("FAdepth : " + depth[a]);
//        System.out.println("FBdepth : " + depth[b]);


        return parent[a][0];
    }

    private static void printAdjList(List<Integer>[] adjList, int[] depth) {
        System.out.println("-----");
        for (int i = 0; i < adjList.length; i++) {
//            System.out.print("["+i+"] d["+ depth[i]+"] : ");
            for (int j = 0; j < adjList[i].size(); j++) {
                System.out.print(adjList[i].get(j) + ", ");
            }
            System.out.println();
        }
    }

    private static void printParentArr(int[][] parent) {
        System.out.println("----parent----");
        for (int i = 0; i < parent.length; i++) {
            for (int j = 0; j < parent[i].length; j++) {
                System.out.print("["+i+"]"+"["+j+"]:"+ parent[i][j] + ", ");
            }
            System.out.println();
        }
    }
}
/**
 * 루트 노드는 1번임
 * 이것을 활용해서 모든 노드의 높이를 찾는다.
 */