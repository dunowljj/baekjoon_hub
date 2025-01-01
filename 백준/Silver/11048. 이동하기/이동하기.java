import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    private static Integer[][] maze;
    private static Integer[][] dp;

    private static int N;
    private static int M;
    private static final int[][] mapper = {{1, 0, 1}, {0, 1, 1}};


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        maze = new Integer[N][M];
        dp = new Integer[N][M];

        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());

            for (int x = 0; x < M; x++) {
                int count = Integer.parseInt(st.nextToken());
                maze[y][x] = count;
            }
        }

        dp[0][0] = maze[0][0];
        System.out.print(recur(N - 1, M - 1));
    }

    private static int recur(int y, int x) {
        if (dp[y][x] != null) {
            return dp[y][x];
        }

        dp[y][x] = maze[y][x];
        int max = 0;
        for (int i = 0; i < 3; i++) {
            int ny = y - mapper[0][i];
            int nx = x - mapper[1][i];

            if (ny < 0 || ny >= N || nx < 0 || nx >= M) continue;

            max = Math.max(recur(ny, nx), max);
        }
        dp[y][x] += max;

        return dp[y][x];
    }
}

/**
 * dfs + dp
 * bfs + dp
 */