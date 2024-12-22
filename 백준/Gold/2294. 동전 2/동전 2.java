import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int IMPOSSIBLE = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] dp = new int[k + 1]; // k원일때 사용한 동전의 개수

        for (int i = 0; i < n; i++) {

            int cost = Integer.parseInt(br.readLine());
            if (cost <= k) dp[cost] = 1;

            for (int costSum = 1; costSum <= k; costSum++) {
                if (dp[costSum] == 0) continue;
                if (costSum + cost > k) continue;

                if (dp[costSum + cost] == 0 || dp[costSum + cost] > dp[costSum] + 1) {
                    dp[costSum + cost] = dp[costSum] + 1;
                }
            }
        }

       /* for (int i = 0; i <= k; i++) {
            System.out.print(dp[i] +" ");
        }*/

        if (dp[k] == 0) System.out.println(IMPOSSIBLE);
        else System.out.print(dp[k]);

    }
}
