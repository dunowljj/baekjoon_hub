import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] dp = new int[N + 2];

        for (int day = 1; day <= N; day++) {
            st = new StringTokenizer(br.readLine());

            int requiredDay = Integer.parseInt(st.nextToken());
            int pay = Integer.parseInt(st.nextToken());

            // 돈 최댓값의 갱신은 상담완료 다음날 기준으로
            if (day + requiredDay <= N + 1 && dp[day + requiredDay] < dp[day] + pay) {
                dp[day + requiredDay] = dp[day] + pay;
            }

            dp[day + 1] = Math.max(dp[day + 1], dp[day]);
        }

        System.out.print(dp[N + 1]);
    }
}
