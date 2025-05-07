import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        int[] fallingLocs = new int[T + 1];
        for (int i = 1; i <= T; i++) {
            fallingLocs[i] = Integer.parseInt(br.readLine()) - 1;
        }

        int[][] dp = new int[T + 1][W + 1];

        for (int time = 1; time <= T; time++) {
            int fruit = fallingLocs[time];

            for (int move = 0; move <= Math.min(time, W); move++) {
                int loc = move % 2;

                // 같은 위치에서 받기
                if (loc == fruit) {
                    dp[time][move] = Math.max(dp[time][move], dp[time - 1][move] + 1);
                } else {
                    dp[time][move] = Math.max(dp[time][move], dp[time - 1][move]);
                }

                // 이동해서 받기 (가능할 때만)
                if (move > 0) {
                    if (loc == fruit) {
                        dp[time][move] = Math.max(dp[time][move], dp[time - 1][move - 1] + 1);
                    } else {
                        dp[time][move] = Math.max(dp[time][move], dp[time - 1][move - 1]);
                    }
                }
            }
        }

        int answer = 0;
        for (int move = 0; move <= W; move++) {
            answer = Math.max(answer, dp[T][move]);
        }

        System.out.print(answer);
    }
}
