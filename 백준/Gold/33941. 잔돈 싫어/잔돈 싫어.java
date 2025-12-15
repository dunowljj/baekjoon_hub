import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        Integer[] dp = new Integer[500]; // n개 동전 사용 시, %500한 결과일때, 최대값
        dp[0] = 0;

        for (int i = 1; i <= N; i++) {
            int cost = Integer.parseInt(br.readLine());

            if (cost >= 20_000 || cost <= 500) continue;

            cost -= 500;

            Queue<int[]> ready = new LinkedList<>();
            for (int j = 0; j < 500; j++) {
                if (dp[j] == null) continue;

                int newCost = dp[j] + cost;
                int remain = newCost % 500;

                ready.offer(new int[]{remain, newCost});
            }

            while (!ready.isEmpty()) {
                int[] poll = ready.poll();

                int remain = poll[0];
                int newCost = poll[1];

                if (dp[remain] == null) dp[remain] = newCost;
                else dp[remain] = Math.max(newCost, dp[remain]);
            }
        }

        System.out.print(dp[0] == null ? 0 : dp[0]);
    }
}
/**
 *
 */