import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Integer[] dp = new Integer[K + 1];
        dp[0] = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int W = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());

            for (int j = K; j - W >= 0; j--) {
                if (dp[j - W] == null) continue;

                if (dp[j] == null) dp[j] = dp[j - W] + V;
                else dp[j] = Math.max(dp[j], dp[j - W] + V);
            }
        }

        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] == null) continue;
            max = Math.max(max, dp[i]);
        }

        System.out.print(max);
    }
}
