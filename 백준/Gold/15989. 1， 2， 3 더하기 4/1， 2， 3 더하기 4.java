import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < T; i++) {
            int n = Integer.parseInt(br.readLine());
            int[] dp = new int[n + 1];
            Arrays.fill(dp, 1);

            for (int j = 0; j <= n; j++) {
                if ((j + 2) <= n) {
                    dp[j + 2] += dp[j];
                }
            }

            for (int j = 0; j <= n; j++) {
                if ((j + 3) <= n) {
                    dp[j + 3] += dp[j];
                }
            }

            answer.append(dp[n])
                    .append(System.lineSeparator());
        }

        System.out.print(answer.toString().trim());
    }
}
