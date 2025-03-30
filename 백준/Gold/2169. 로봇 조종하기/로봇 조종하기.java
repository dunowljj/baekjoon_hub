import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static int N,M;
    public static int[][] mars,dp,temp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        mars = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                mars[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[N][M];
        temp = new int[2][M];

        dp[0][0] = mars[0][0];
        for (int c = 1; c < M; c++) {
            dp[0][c] = dp[0][c-1] + mars[0][c];
        }

        // 500 * 1000 * 1000?
        for (int r = 1; r < N; r++) {

            // 왼 -> 오
            temp[0][0] = dp[r - 1][0] + mars[r][0];
            for (int c = 1; c < M; c++) {
                temp[0][c] = Math.max(temp[0][c - 1], dp[r - 1][c]) + mars[r][c];
            }

            // 왼 <- 오
            temp[1][M - 1] = dp[r - 1][M - 1] + mars[r][M - 1];
            for (int c = M - 2; c >= 0; c--) {
                temp[1][c] = Math.max(temp[1][c + 1], dp[r - 1][c]) + mars[r][c];
            }

            for (int c = 0; c < M; c++) {
                dp[r][c] = Math.max(temp[0][c], temp[1][c]);
            }
        }

        System.out.print(dp[N-1][M-1]);
    }
}
/**
 * - 위로 이동 불가
 * - 한번 탐사하면 다시 탐사하지 않는다.
 * - 탐사 지역 가치의 합이 최대가 되도록하기
 *
 * 위로 가지 않고, 돌아가더라도 모든 경우를 탐색해야한다.
 * 위로 가지 않기 때문에, dp배열에 최댓값을 갱신하면서 이동하면, 재방문 없이 탐사 가능하다.
 * 좌우로 반복해서 이동하는 경우를 제외해야한다.
 *
 *
 * 이전 방향의 최대값을 어떻게 구할까?
 *
 * 방향을 체크하면서 한줄씩 탐색을 해야한다. bfs, dfs로 해결하려면, 이전 줄의 왼/오른 방향 횟수를 세야하고 메모리도 초과할듯
 * 한줄씩 해결한다. 왼쪽, 오른쪽으로 가는 방향에 따른 dp값을 각각 구하고, 더 큰값으로 갱신한다.
 */