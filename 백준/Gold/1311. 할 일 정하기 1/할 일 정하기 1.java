import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static Integer[][] dp;
    static int[][] costs;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        costs = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                costs[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new Integer[N + 1][1 << (N + 1)];
        dp[0][0] = 0;

        for (int i = 1; i <= N; i++) {

            for (int j = 0; j < 1 << (N + 1); j++) {
                if (dp[i - 1][j] == null) continue;

                for (int k = 1; k <= N; k++) {
                    if ((j & (1 << k)) != 0) continue;

                    int nb = j | (1 << k);
                    if (dp[i][nb] == null || dp[i][nb] > dp[i - 1][j] + costs[i][k]) {
                        dp[i][nb] = dp[i - 1][j] + costs[i][k];
                    }
                }
            }
        }

        System.out.print(dp[N][(1 << (N + 1)) - 2]);
    }

}