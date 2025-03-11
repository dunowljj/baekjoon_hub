import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static int N,M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        int[][] adj = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                adj[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        Set<Integer> dests = new HashSet<>();
        int start = 0;
        for (int i = 0; i < M; i++) {
            int city = Integer.parseInt(st.nextToken());
            dests.add(city);
            start = city;
        }


        boolean canVisitAll = bfs(start, adj, dests, new boolean[N + 1]);

        if (canVisitAll) System.out.print("YES");
        else System.out.print("NO");
    }

    private static boolean bfs(int start, int[][] adj, Set<Integer> dests, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        dests.remove(start);

        while (!queue.isEmpty()) {
            int now = queue.poll();


            for (int next = 1; next <= N; next++) {
                if (adj[now][next] == 1 && !visited[next]) {
                    visited[next] = true;
                    dests.remove(next);

                    queue.offer(next);
                }
            }
        }

        return dests.size() == 0;
    }
}

/**
 * N 200이하
 * M 1000이하
 *
 * 계획에서 각 위치에 대해 도달 가능한지 탐색하면 된다.
 */