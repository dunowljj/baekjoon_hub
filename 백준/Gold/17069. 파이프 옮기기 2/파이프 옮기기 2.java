import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int WALL = 1;
    private static boolean[][] isWall;
    private static long[][][] dp;
    private static int N;

    private static final int  HORIZONTAL = 0;
    private static final int  DIAGONAL = 1;
    private static final int  VERTICAL = 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        isWall = new boolean[N][N];
        dp = new long[3][N][N];
        dp[HORIZONTAL][0][1] = 1L;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                if (Integer.parseInt(st.nextToken()) == WALL) {
                    isWall[i][j] = true;
                }
            }
        }

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (y == 0 && x == 0) continue;

                if (isWall[y][x]) continue;

                for (int dir = 0; dir < 3; dir++) {
                    if (dir == HORIZONTAL) {
                        // 수평 이동
                        if (canMove(HORIZONTAL, y, x)) dp[HORIZONTAL][y][x + 1] += dp[dir][y][x];

                        // 대각선 이동
                        if (canMove(DIAGONAL, y, x)) dp[DIAGONAL][y + 1][x + 1] += dp[dir][y][x];
                    }

                    if (dir == DIAGONAL) {
                        // 수평 이동
                        if (canMove(HORIZONTAL, y, x)) dp[HORIZONTAL][y][x + 1] += dp[dir][y][x];

                        // 대각선 이동
                        if (canMove(DIAGONAL, y, x)) dp[DIAGONAL][y + 1][x + 1] += dp[dir][y][x];

                        // 수직
                        if (canMove(VERTICAL, y, x)) dp[VERTICAL][y + 1][x] += dp[dir][y][x];
                    }

                    if (dir == VERTICAL) {
                        // 대각선 이동
                        if (canMove(DIAGONAL, y, x)) dp[DIAGONAL][y + 1][x + 1] += dp[dir][y][x];

                        // 수직
                        if (canMove(VERTICAL, y, x)) dp[VERTICAL][y + 1][x] += dp[dir][y][x];
                    }
                }
            }
        }

        System.out.print(dp[0][N - 1][N - 1] + dp[1][N - 1][N - 1] + dp[2][N - 1][N - 1]);
    }

    private static boolean canMove(int nextDir, int y, int x) {
        if (nextDir == HORIZONTAL) return (x + 1 < N)
                && !isWall[y][x + 1];

        if (nextDir == DIAGONAL) return (y + 1 < N && x + 1 < N)
                && !isWall[y][x+1]
                && !isWall[y+1][x]
                && !isWall[y+1][x+1];

        if (nextDir == VERTICAL) return (y + 1 < N)
                && !isWall[y + 1][x];

        return false;
    }
}