import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static List<Integer>[] adjList;
    private static Integer[][] dp;

    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        // [n][01] -> n번 노드가 루트라 가정 시,
        // 0 : n번 노드가 어답터가 아닌경우 어답터 최소 수
        // 1 : n번 노드가 어답터인 경우 어답터 최소 수
        dp = new Integer[N + 1][2];
        visited = new boolean[N + 1];

        adjList = new ArrayList[N + 1];
        for (int i = 1; i < N + 1; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // 사실상 트리라고만 했지 어느쪽이 부모인지 모르기때문에 무방향이라 가정하고 방문체크를 해야한다.
            adjList[u].add(v);
            adjList[v].add(u);
        }

        dfs(1);
        System.out.print(Math.min(dp[1][0], dp[1][1]));
    }

    private static void dfs(int now) {
        visited[now] = true;

        dp[now][0] = 0;
        dp[now][1] = 1;
        for (Integer next : adjList[now]) {
            if (visited[next]) continue;

            dfs(next);

            dp[now][0] += dp[next][1];
            dp[now][1] += Math.min(dp[next][0], dp[next][1]);
        }
    }


    /*// 미리 리프노드들을 dp배열에 갱신해놓고 탐색부터하기
    private static int dfs(int now, boolean[] visited) {
        if (visited[now]) {
            return Math.min(dp[now][0], dp[now][1]);
        }

        visited[now] = true;

        dp[now][0] = 0;
        dp[now][1] = 1;
        for (Integer child : adjList[now]) {
            dfs(child, visited); // 미리 dp값 갱신

            dp[now][0] += dp[child][1];
            dp[now][1] += Math.min(dp[child][1], dp[child][0]);
        }

        return Math.min(dp[now][0], dp[now][1]);
    }*/
}