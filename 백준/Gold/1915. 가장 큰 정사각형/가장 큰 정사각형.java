import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int n;
    private static int m;

    private static int[][] dp;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int minSide = Math.min(n, m);

        // [y][x][l] -> y,x를 시작으로 오른, 아래 방향으로 길이가 ㅣ인 정사각형이 존재하는지
        dp = new int[n + 1][m + 1];

        int maxLen = 0;
        for (int y = 1; y <= n; y++) {
            String line = br.readLine();

            for (int x = 1; x <= m; x++) {
                dp[y][x] = line.charAt(x - 1) - '0';

                if (dp[y][x] == 1) {
                    dp[y][x] = Math.min(
                            dp[y - 1][x - 1],
                            Math.min(dp[y - 1][x], dp[y][x - 1])
                    ) + 1;

                    maxLen = Math.max(dp[y][x], maxLen);
                }
            }
        }

        System.out.print(maxLen * maxLen);
    }
}
/**
 * 1000 * 1000 -> 100만
 *
 * 모든 100만개의 요소를 bfs로 탐색한다면...
 *
 * dp를 사용하면 100만개의 요소를 탐색하되, 각 위치에 대해 4가지 경우의 수만 점검하면 된다.
 * 메모리 초과가 나온다. boolean 3차원 배열 생성 시 20억 바이트 -> 2gb
 * int 2차원 배열을 쓰고, 길이를 값에 저장하여 활용하자. -> 400만 바이트 -> 4mb
 */
