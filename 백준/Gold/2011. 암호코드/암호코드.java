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
        dp = new int[n];

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

        for (int i = 0; i < n - 1; i++) {
            int val1 = password.charAt(i) - '0';
            int val2 = password.charAt(i + 1) - '0';

            // '0' 2개 연속 -> 불가능
            if (val1 == 0 && val2 == 0) {
                System.out.print(0);
                return;
            }

            // '0' 앞에 1,2가 없음 -> 불가능
            if (val1 > 2 && val2 == 0) {
                System.out.print(0);
                return;
            }
        }

        dp[0] = 1;
        int v1 = password.charAt(0) - '0';
        int v2 = password.charAt(1) - '0';
        if (v1 == 1 || (v1 == 2 && v2 <= 6)) dp[1] = 1;

        for (int i = 1; i < n; i++) {
            int val = password.charAt(i) - '0';

            if (val != 0) dp[i] += dp[i - 1];

            if (i < n - 1) {
                int nextVal = password.charAt(i + 1) - '0';

                if (val == 1 || (val == 2 && nextVal <= 6)) {
                    dp[i + 1] += dp[i - 1];
                }
            }

            dp[i] %= MOD;
        }

        System.out.print(dp[n - 1]);
    }
}

/**
 * 매번 1_000_000으로 나눠야함
 */