import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[][] dp;
    static int[] log;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = (int)(Math.log(N) / Math.log(2)) + 1;

        dp = new int[N + 1][K];
        for (int i = 1; i <= N; i++) {
            dp[i][0] = Integer.parseInt(br.readLine());
        }

        log = new int[N + 1];
        for (int i = 2; i <= N; i++) {
            log[i] = log[i/2] + 1;
        }

        for (int k = 1; k < K; k++) {
            int size = (1 << k);
            for (int n = 1; n + size <= N + 1; n++) {
                dp[n][k] = Math.min(dp[n][k - 1], dp[n+(size/2)][k - 1]);
            }
        }


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int k = log[b - a + 1];

            sb.append(Math.min(
                            dp[a][k],
                            dp[b - (1<<k) + 1][k])
                    )
                    .append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }
}
/**
 * sparse tree 형태
 * 겹치는 부분은 min 연산이라 상관없다.
 */