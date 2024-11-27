import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        Counsel[] counsels = new Counsel[N + 1];
        int[] dp = new int[N + 2]; // dp[n]: n일차에 받을수 있는 최대 금액

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int T = Integer.parseInt(st.nextToken());
            int P = Integer.parseInt(st.nextToken());

            counsels[i] = new Counsel(T, P);
        }


        for (int i = 1; i <= N; i++) {
            int time = counsels[i].time;
            int cost = counsels[i].cost;
            int payDay = i + time - 1;

//            printDp(N, dp);
            dp[i + 1] = Math.max(dp[i + 1], dp[i]);

            // 기한 초과시 상담 불가
            // 예시에서 5일날 상담이 가능하듯, 마지막날 상담을 완료만 한다면 상담 가능하다.
            // 상담완료날(payDay)  다음부터 추가상담이 가능하므로 dp배열은 다음날을 기준으로한다.
            if (payDay <= N && dp[payDay + 1] < dp[i] + cost) {
                dp[payDay + 1] = dp[i] + cost;
            }
        }

//        printDp(N, dp);
        System.out.print(dp[N + 1]);
    }

    private static void printDp(int N, int[] dp) {
        for (int j = 1; j < dp.length; j++) {
            System.out.print(dp[j]+",");
        }
        System.out.println();
    }

    static class Counsel {
        int time;
        int cost;

        public Counsel(int time, int cost) {
            this.time = time;
            this.cost = cost;
        }
    }
}
