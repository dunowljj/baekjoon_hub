import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static final int MOD = 15746;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] dp = new int[N + 1];
        dp[0] = 1;
        dp[1] = 1;
//        dp[2] = 2; -> N이 1일 경우를 대비해 dp[2]를 초기화하는 대신 dp[0]값을 지정

        for (int n = 2; n <= N; n++) {
            dp[n] = (dp[n - 1] % MOD + dp[n - 2] % MOD);
        }

        System.out.print(dp[N] % MOD);
    }
}

/*
1 -> 1
2 -> 11, 00
3 -> 111, 100, 001
4 -> 1111, 1100, 0011, 1001, 0000
5 -> 11111, 11100, 11001, 10011, 10000, 00111, 00100, 00001
dp[n] = dp[n-2] + dp[n-1];

f(n) = f(n-1) + f(n-2)

 */

