import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final int MOD = 1_000_000;

    private static int n;
    private static String password;
    private static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        password = br.readLine();
        n = password.length();
        dp = new int[n + 1];

        // 시작이 0인 경우 불가능
        if (password.charAt(0) == '0') {
            System.out.print(0);
            return;
        }

        // 길이 1
        if (n == 1) {
            System.out.print(1);
            return;
        }

        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            int curr = password.charAt(i - 1) - '0';
            int prev = password.charAt(i - 2) - '0';

            if (curr != 0) dp[i] += dp[i - 1];

            if (prev == 1 || (prev == 2 && curr <= 6)) {
                dp[i] += dp[i - 2];
            }

            dp[i] %= MOD;
        }

        System.out.print(dp[n]);
    }
}

/**
 * 매번 1_000_000으로 나눠야함
 */