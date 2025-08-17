import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    private static int N,M;
    private static final Integer[] dp = new Integer[101];
    private static final Map<Integer, Integer> move = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            move.put(x, y);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            move.put(u, v);
        }

        dp[1] = 0;
        playGame(1, 1);

        System.out.print(dp[100]);
    }

    private static void playGame(int loc, int count) {
        if (count > 100) return;

        for (int step = 1; step <= 6; step++) {
            int nextLoc = loc + step;

            if (nextLoc > 100) return;

            // 사다리 혹은 뱀이 존재하는 경우 위치 이동
            while (move.containsKey(nextLoc)) {
                nextLoc = move.get(loc + step);
            }

            if (dp[nextLoc] == null || dp[nextLoc] > dp[loc] + 1) {
                dp[nextLoc] = dp[loc] + 1;
                playGame(nextLoc, count + 1);
            }
        }
    }
}