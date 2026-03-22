import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        int[] A = new int[N];
        int[] dp = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
            dp[i] = 1;
        }
        
        int max = 1;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (A[j] < A[i]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }

            max = Math.max(dp[i], max);
        }

        System.out.print(max);
    }
}
/**
 * 10 20 30 5 6 7 8 40
 * 1  2  3  1
 */
