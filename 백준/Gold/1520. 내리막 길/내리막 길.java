import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final int[][] mapper = {{-1, 1, 0, 0}, {0, 0, 1, -1}};
    private static int count = 0;

    private static int M;
    private static int N;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        int[][] height = new int[M][N];
        int[][] dp = new int[M][N];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                height[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }

        System.out.print(
                dfs(0, 0, height, dp)
        );

//        printGrid(dp);
    }

    private static void printGrid(int[][] dp) {
        System.out.println();

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (dp[i][j] == - 1) System.out.print("- ");
                else System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int dfs(int y, int x, int[][] height, int[][] dp) {
        if (y == M - 1 && x == N - 1) {
            return 1;
        }

        if (dp[y][x] != -1) return dp[y][x];

        dp[y][x] = 0;
        for (int i = 0; i < 4; i++) {
            int ny = y + mapper[0][i];
            int nx = x + mapper[1][i];

            if (ny < 0 || ny >= M || nx < 0 || nx >= N) continue;

            if (height[y][x] > height[ny][nx]) {
                dp[y][x] += dfs(ny, nx, height, dp);
            }
        }

        return dp[y][x];
    }
}
/*
 */