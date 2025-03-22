import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static final long MOD = 1_000_000_000;
    public static final int MAX_BIT = (1 << 10) - 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        long[][][] dp = new long[N + 1][MAX_BIT + 1][10]; // [a][bit][b] 맨끝자리가 b인 a자리 계단수의 개수,

        for (int i = 1; i <= 9; i++) {
            dp[1][1 << i][i] = 1;
        }

        // 자릿수에 대해
        for (int i = 2; i <= N; i++) {

            for (int bit = 0; bit <= MAX_BIT; bit++) {
                // 맨끝이 k(0~9)일때
                for (int k = 0; k <= 9; k++) {

                    if (k != 0) {
                        dp[i][bit | (1 << k)][k] += dp[i - 1][bit][k - 1];
                        dp[i][bit | (1 << k)][k] %= MOD;
                    }

                    if (k != 9) {
                        dp[i][bit | (1 << k)][k] += dp[i - 1][bit][k + 1];
                        dp[i][bit | (1 << k)][k] %= MOD;
                    }
                }
            }
        }

        int answer = 0;
        for (int i = 0; i <= 9; i++) {
            answer += dp[N][MAX_BIT][i];
            answer %= MOD;
        }

        System.out.print(answer);
    }
}



/**
 * 계단 수, 그 중에서도 0~9를 모두 방문한 수를 구해야 한다.
 */