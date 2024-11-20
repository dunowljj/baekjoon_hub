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

        List<Integer>[] adjList = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            adjList[i] = new ArrayList<>();
        }

        int[] parent = new int[N + 1];
        int[] height = new int[N + 1];

        // 루트 기본값 설정
//        parent[1] = 0;
//        height[1] = 0;

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            adjList[n1].add(n2);
            adjList[n2].add(n1);
        }

        checkParentAndHeight(height, parent, adjList, 1);

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            System.out.println(findLCA(parent, height, n1, n2));
        }
    }

    private static int findLCA(int[] parent, int[] height, int n1, int n2) {
        while (n1 != n2) {
            while (height[n1] < height[n2]) {
                n2 = parent[n2];
            }

            while (height[n1] > height[n2]) {
                n1 = parent[n1];
            }

            if (n1 != n2) {
                n1 = parent[n1];
            }
        }

        // 같은 높이인데 다른 노드인 경우 어떻게하는가?
        // 같은 높이인데 2번 올라가야 공통 조상인 경우는?

        return n1;
    }

    private static void checkParentAndHeight(int[] height, int[] parent, List<Integer>[] adjList, int start) {
        for (int next : adjList[start]) {
            if (next == parent[start]) continue; // 부모인 경우 갱신 x

            parent[next] = start;
            height[next] = height[start] + 1; //height가 클수록 아래에 있다.

            checkParentAndHeight(height, parent, adjList, next);
        }
    }
}
/**
 * 루트 노드는 1번임
 * 이것을 활용해서 모든 노드의 높이를 찾는다.
 */