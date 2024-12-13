import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        Integer[] dp = new Integer[N + 1];
        dp[N] = 0;

        for (int i = N; i >= 1; i--) {
            if (dp[i] == null) continue;

            if (i % 3 == 0) {
                if (dp[i / 3] == null || dp[i / 3] > dp[i] + 1) {
                    dp[i / 3] = dp[i] + 1;
                }
            }

            if (i % 2 == 0) {
                if (dp[i / 2] == null || dp[i / 2] > dp[i] + 1) {
                    dp[i / 2] = dp[i] + 1;
                }
            }

            if (dp[i - 1] == null || dp[i - 1] > dp[i] + 1) {
                dp[i - 1] = dp[i] + 1;
            }
        }

        System.out.print(dp[1]);
    }
}
