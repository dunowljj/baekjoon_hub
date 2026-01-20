import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static boolean[] isVIP;
    static int[] dp;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        isVIP = new boolean[N + 1];
        dp = new int[N + 1];

        for (int i = 0; i < M; i++) {
            int fixNo = Integer.parseInt(br.readLine());
            isVIP[fixNo] = true;
        }

        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i < N + 1; i++) {
            dp[i] = dp[i - 1];

            if (!(isVIP[i - 1] || isVIP[i])) {
                dp[i] += dp[i - 2];
            }
        }

        System.out.print(dp[N]);
    }
}