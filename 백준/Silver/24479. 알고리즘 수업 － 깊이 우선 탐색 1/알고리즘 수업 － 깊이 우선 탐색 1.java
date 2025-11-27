import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static List<Integer>[] adj;
    private static int[] answer;
    private static boolean[] visited;
    private static int N,M,R,seq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        seq = 1;

        answer = new int[N + 1];
        visited = new boolean[N + 1];
        adj = new ArrayList[N + 1];
        for (int i = 1; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            adj[u].add(v);
            adj[v].add(u);
        }

        for (int i = 1; i < adj.length; i++) {
            Collections.sort(adj[i]);
        }

        dfs(R);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < answer.length; i++) {
            sb.append(answer[i]).append(System.lineSeparator());
        }

        System.out.print(sb);
    }

    private static void dfs(int now) {
        answer[now] = seq++;
        visited[now] = true;

        for (int next : adj[now]) {
            if (!visited[next]) dfs(next);
        }
    }
}
