import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static final int MAX = 100_000;
    public static final int NULL = -1;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        for (int tc = 0; tc < 3; tc++) {
            int N = Integer.parseInt(br.readLine());

            int totalCost = 0;

            int[] costs = new int[N];
            int[] counts = new int[N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                costs[i] = Integer.parseInt(st.nextToken());
                counts[i] = Integer.parseInt(st.nextToken());

                totalCost += costs[i] * counts[i];
            }

            if (totalCost % 2 == 1) {
                sb.append(0).append(System.lineSeparator());;
                continue;
            }

            int half = totalCost / 2;

            dp = new int[half + 1];
            Arrays.fill(dp, -1);
            dp[0] = 0;

            for (int i = 0; i < N; i++) {
                int cost = costs[i];
                int count = counts[i];

                for (int j = 0; j <= half; j++) {
                    if (dp[j] >= 0) {
                        dp[j] = count;
                    } else if (j - cost >= 0 && dp[j - cost] > 0) {
                        dp[j] = dp[j - cost] - 1;
                    }
                }
            }

            if (dp[half] == NULL) {
                sb.append(0).append(System.lineSeparator());;
            } else {
                sb.append(1).append(System.lineSeparator());;
            }
        }

        System.out.print(sb.toString().trim());
    }
}
