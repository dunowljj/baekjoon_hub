import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());


        int[] wine = new int[n];
        int[][] dp = new int[n][3]; // [잔의 번호][연속되어 마신 수]
        for (int i = 0; i < n; i++) {
            int amount = Integer.parseInt(br.readLine());
            wine[i] = amount;
        }

        if (n <= 2) {
            int sum = 0;
            for (int amount : wine) {
                sum += amount;
            }

            System.out.println(sum);
            System.exit(0);
        }

        dp[0][0] = 0;
        dp[0][1] = wine[0];
        dp[1][1] = wine[1];
        dp[1][2] = dp[0][1] + dp[1][1];

        for (int i = 1; i < n; i++) {
            dp[i][1] = wine[i];
        }

        // [i][2] 전 잔과 이번잔을 모두 마셨을때
        // [i][1] 전 잔은 안마시고, 이번 잔을 마셨을때
        // [i][0] 전 잔 무관하게 이번 잔을 안마셨을때
        for (int i = 2; i < n; i++) {
            dp[i][2] = dp[i - 1][1] + wine[i];
            dp[i][1] = Math.max(dp[i - 2][0], Math.max(dp[i - 2][1], dp[i - 2][2])) + wine[i];
            dp[i][0] = Math.max(dp[i - 1][0], Math.max(dp[i - 1][1], dp[i - 1][2]));
        }

//        for (int i = 0; i < dp.length; i++) {
//            System.out.print(i + "  ");
//        }
//        System.out.println();
//
//        for (int i = 0; i < dp.length; i++) {
//            System.out.print(dp[i][1]+" ");
//        }
//        System.out.println();
//
//        for (int i = 0; i < dp.length; i++) {
//            System.out.print(dp[i][2]+" ");
//        }
//        System.out.println();

        int max = 0;
        for (int i = n - 3; i < n; i++) {
            max = Math.max(
                    Math.max(dp[i][0], dp[i][1]),
                    Math.max(dp[i][2], max)
            );
        }

        System.out.print(max);
    }
}

/**
 *
 * 현재 연속되어 0,1,2번 마신 상태일때, 최댓값을 구하자
 *
 * 연속 3잔 마시기 불가
 * w   1 5 10 18 99  55
 * d0  0 0 0  0  0   0
 * d1  1 5 10 18 109 55
 * d2  0 6 15 28 117
 *
 *
 *     - 5 10  -  99  55
 * 1   10 18
 * 1 5    18 99
 * 1   10 18    5
 *   5 10    99 5
 * 1      18 99
 *
 *
 * 1 5
 * 1
 *   5
 * - -
 *
 */