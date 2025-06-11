import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        Integer[] dp = new Integer[1500]; // [고객의수] == 가격
        dp[0] = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int customerCount = Integer.parseInt(st.nextToken());

            for (int j = 0; j < dp.length; j++) {
                if (dp[j] == null) continue;

                if (j + customerCount < dp.length) {
                    if (dp[j + customerCount] == null) dp[j + customerCount] = dp[j] + cost;
                    else dp[j + customerCount] = Math.min(dp[j + customerCount], dp[j] + cost);
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int i = C; i < dp.length; i++) {
            if (dp[i] == null) continue;
            answer = Math.min(dp[i], answer);
        }

        System.out.print(answer);
    }
}
/**
 * 적어도 C명, 도시 개수 N개
 * C 1~1000
 * N 1~20
 * 각 비용과 고객의 수 1~100
 *
 * 1명에 100 -> 1000명 -> 10만
 * 최소로 유치하는 고객 단위가 100인 경우도 있을 수 있음.
 */