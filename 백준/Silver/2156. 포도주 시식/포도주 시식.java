import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());


        int[] wine = new int[n];
        int[] dp = new int[n]; // [잔의 번호][연속되어 마신 수]
        for (int i = 0; i < n; i++) {
            int amount = Integer.parseInt(br.readLine());
            wine[i] = amount;
        }

        int max = 0;

        if (n <= 2) {
            for (int amount : wine) {
                max += amount;
            }

            System.out.print(max);
            System.exit(0);
        }

        dp[0] = wine[0];
        dp[1] = wine[0] + wine[1];
        dp[2] = Math.max(
                Math.max(wine[0], wine[1]) + wine[2],
                dp[1]
        );




        // dp[n] = n번째 포도주를 마셨을때 해당 시점에서 마실 수 있는 포도주 양의 최댓값
        // dp[n]의 값은 dp[n - 1]을 마신 경우의 수도 포함해 계산한 최댓값이다.
        // 그렇기때문에 dp[n]의 계산에 dp[n - 1]을 사용하면 연속해서 3잔을 먹는 경우까지 포함된 계산이므로 안된다.

        for (int i = 3; i <= n - 1; i++) {
            dp[i] = Math.max(dp[i - 3] + wine[i - 1], dp[i - 2]) + wine[i];
            dp[i] = Math.max(dp[i - 1], dp[i]);
        }

        System.out.print(dp[n - 1]);
    }
}