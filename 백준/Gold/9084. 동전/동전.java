import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());

            st = new StringTokenizer(br.readLine());
            int[] costs = new int[N];
            for (int i = 0; i < N; i++) {
                costs[i] = Integer.parseInt(st.nextToken());
            }

            int M = Integer.parseInt(br.readLine());
            int[] dp = new int[M + 1];
            dp[0] = 1;

            for (int i = 0; i < costs.length; i++) {
                int cost = costs[i];

                for (int j = cost; j < dp.length; j++) {
                    dp[j] += dp[j - cost];
                }
            }

            sb.append(dp[M]).append(System.lineSeparator());
        }

        System.out.print(sb);
    }
}
