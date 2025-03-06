import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<App> apps = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int using = Integer.parseInt(st.nextToken());
            apps.add(new App(using));
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int cost = Integer.parseInt(st.nextToken());
            apps.get(i).cost = cost;
        }

        int[][] dp = new int[N][100*100 + 1]; // [N][cost] N번째까지 고려했을때 비용에 대한 최대 메모리 제거량
        int answer = Integer.MAX_VALUE;

        int using = apps.get(0).using;
        int cost = apps.get(0).cost;
        for (int j = cost; j < dp[0].length; j++) {
            dp[0][j] = using;
        }
        if (M <= using) answer = apps.get(0).cost;

        for (int i = 1; i < N; i++) {
            App app = apps.get(i);

            for (int j = 0; j < dp[i].length; j++) {
                // i번째 앱이 제거되지 않았을때 최댓값
                if (j - app.cost >= 0) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - app.cost] + app.using);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }

                if (dp[i][j] >= M) {
                    answer = Math.min(answer, j);
                }
            }
        }
        System.out.print(answer);
    }

    static class App {
        int using;
        int cost;

        public App(int using, int cost) {
            this.using = using;
            this.cost = cost;
        }

        public App(int using) {
            this.using = using;
        }
    }
}
/**
 * M 만큼만 제거한 최소 비용을 어떻게 구할까?
 * dp[x] -> x만큼 메모리를 제거했을때, 드는 최소 비용
 * 단, x가 M을 초과하는 경우가 최소비용일수도 있겠다.
 *
 * N 1~100
 * M 1~1000만
 * 각 m도 1 ~ 1000만
 * -> M은 모든 앱의 메모리 사용량의 합보다 작음
 * c 0~100
 *
 * 배열로 하면 100 *1000만으로 10억인데
 *
 */