import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int stairCount = Integer.parseInt(br.readLine());

        int[] scores = new int[stairCount];
        int[] dp = new int[stairCount]; //dp[n]은 n번째 계단을 밟았을때 시점의 최댓값

        for (int i = 0; i < stairCount; i++) {
            scores[i] = Integer.parseInt(br.readLine());
        }

        if (stairCount <= 2) {
            int sum = 0;
            for (int score : scores) {
                sum += score;
            }

            System.out.print(sum);
            System.exit(0);
        }

        dp[0] = scores[0];
        dp[1] = scores[0] + scores[1];
        dp[2] = Math.max(scores[0], scores[1]) + scores[2]; // 첫계단을 무조건 밟으므로 한가지 경우만 존재

        for (int i = 3; i < stairCount; i++) {
            dp[i] = Math.max(
                    dp[i - 2],
                    dp[i - 3] + scores[i - 1]
            ) + scores[i];

//            dp[i] = Math.max(dp[i - 1], dp[i]);
        }

        // 마지막지점에 dp[n - 1]으로 갱신하지 않기 위해 따로 빼줌
        // 마지막 계단을 밟아야하기 떄문이다.
//        for (int i = 0; i < stairCount; i++) {
//            System.out.print(dp[i] + " ");
//        }
//        System.out.println();

        System.out.print(dp[stairCount - 1]);
    }
}
/**
 * 한번에 한계단 or 두계단 가능 -> 세칸이상 뛰기는 불가
 * 연속 세 계단을 모두 밟아서는 안됨, 시작점은 계단 아님
 * 마지막 계단 무조건 밟아야 -> 뒤부터 탐색해도 될듯
 *
 *
 *
 * 0 1 2 3 4
 * o o x o o
 * o o x o x
 * o x o o x
 * o x o x o

 *  n-4 n-3 n-2 n-1  n
 *   o   x   o   o   x
 *   o   o   x   o   x
 *   x   o   x   o   x
 *   o   o   x   o   o
 *   x   o   x   o   o -> dp[n-3] + scores[n-1] + scores[n]
 *   o   x   o   x   o
 *   x   o   o   x   o
 *
 *   dp[n] -> dp[n-2] + scores[n]
 *   dp[n] -> Math.max(dp[n-1], dp[n])
 *   10 20 15 25 10 20
 */

