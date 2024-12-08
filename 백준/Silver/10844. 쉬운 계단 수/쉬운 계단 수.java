import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final int MOD = 1_000_000_000;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long[][] dp = new long[N + 1][10];
        for (int i = 1; i < 10; i++) {
            dp[1][i] = 1L;
        }

        for (int digit = 1; digit < N; digit++) {
            for (int num = 0; num < 10; num++) {
                if (num == 0) {
                    dp[digit + 1][num + 1] += dp[digit][num];
                    dp[digit + 1][num + 1] %= MOD;
                } else if (num == 9) {
                    dp[digit + 1][num - 1] += dp[digit][num];
                    dp[digit + 1][num - 1] %= MOD;
                } else {
                    dp[digit + 1][num + 1] += dp[digit][num];
                    dp[digit + 1][num + 1] %= MOD;

                    dp[digit + 1][num - 1] += dp[digit][num];
                    dp[digit + 1][num - 1] %= MOD;
                }
            }
        }

        long max = 0;
        for (int num = 0; num < dp[N].length; num++) {
            max += dp[N][num];
            max %= MOD;
        }

        System.out.print(max);
    }
}
