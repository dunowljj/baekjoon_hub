import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());


        int[] costs = new int[n];
        for (int i = 0; i < n; i++) {
            int cost = Integer.parseInt(br.readLine());
            costs[i] = cost;
        }

        int[] dp = new int[k + 1];


        for (int i = 0; i < costs.length; i++) {
            if (costs[i] > k) continue;
            dp[costs[i]]++;

            for (int j = 1; j < dp.length; j++) {
                if (dp[j] == 0 || j + costs[i] > k) continue;

                dp[j + costs[i]] += dp[j];
            }

//            print(dp);
        }


        System.out.print(dp[k]);
    }

    private static void print(int[] dp) {
        for (int i = 1; i < dp.length; i++) {
            System.out.print(dp[i] + " ");
        }
        System.out.println();
    }

}

/**
 * 동전 사용의 순서만 다른 것은 같은 경우이다.
 *
 *
 */