import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static List<Integer>[] adj;
    static List<Integer>[] reverseAdj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        adj = new ArrayList[N + 1];
        reverseAdj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
            reverseAdj[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a < b, a -> b
            adj[a].add(b);
            reverseAdj[b].add(a);
        }

        int count = 0;
        for (int i = 1; i <= N; i++) {

            int bigger = search(i, adj, new boolean[N + 1]) - 1;
            int smaller = search(i, reverseAdj, new boolean[N + 1]) - 1;

            if (bigger + smaller == N - 1) count++;
        }

        System.out.print(count);
    }

    private static int search(int now, List<Integer>[] graph, boolean[] visited) {
        if (graph[now].size() == 0) return 1;

        int count = 1;
        for (int next : graph[now]) {
            if (visited[next]) continue;
            visited[next] = true;
            count += search(next, graph, visited);
        }

        return count;
    }
}
/**
 * 자신의 키가 몇 번째 인가?
 * -> 정방향 개수 + 역방향 개수가 N-1개이면 정확히 파악 가능.
 *
 * 250000 * 500
 * 125 * 10^6 * 2
 */
