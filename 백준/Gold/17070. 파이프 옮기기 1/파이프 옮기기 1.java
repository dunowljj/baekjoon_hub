import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {

    public static final int EMPTY = 0;
    public static final int WALL = 1;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int DIAGONAL = 2;

    private static Integer[][][] dp;
    private static boolean[][] isWall;
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        isWall = new boolean[N][N];
        dp = new Integer[N][N][3]; // [a][b][0~2] 0~2 모양으로 a,b에 파이프가 놓인 상태

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                if (Integer.parseInt(st.nextToken()) == WALL) {
                    isWall[i][j] = true;
                }
            }
        }

        // 현재 어느방향이든 대각선은 무조건 포함 -> 맨 끝값을 대각선만 1로 지정하여 어떤 방향에서 도달하든 1씩만 가져가도록 설정
        dp[N - 1][N - 1][HORIZONTAL] = 0;
        dp[N - 1][N - 1][VERTICAL] = 0;
        dp[N - 1][N - 1][DIAGONAL] = 1;
        dfs(0, 1, HORIZONTAL);

        System.out.print(dp[0][1][HORIZONTAL] + dp[0][1][DIAGONAL]);
//        printAll();
    }

//    private static void printAll() {
//        System.out.println("vertical");
//
//        for (int i = 0; i < dp.length; i++) {
//            for (int j = 0; j < dp[i].length; j++) {
//                if (dp[i][j] == null) System.out.print("- ");
//                else {
//                    int sum = 0;
////                    if (Objects.nonNull(dp[i][j][HORIZONTAL])) sum += dp[i][j][HORIZONTAL];
//                    if (Objects.nonNull(dp[i][j][VERTICAL])) sum += dp[i][j][VERTICAL];
////                    if (Objects.nonNull(dp[i][j][DIAGONAL])) sum += dp[i][j][DIAGONAL];
//
//                    if (sum > 10) System.out.print(sum + " ");
//                    else System.out.print(" "+ sum +" ");
//                }
//            }
//            System.out.println();
//        }
//        System.out.println();
//        System.out.println("Horizontal");
//        for (int i = 0; i < dp.length; i++) {
//            for (int j = 0; j < dp[i].length; j++) {
//                if (dp[i][j] == null) System.out.print("- ");
//                else {
//                    int sum = 0;
//                    if (Objects.nonNull(dp[i][j][HORIZONTAL])) sum += dp[i][j][HORIZONTAL];
////                    if (Objects.nonNull(dp[i][j][VERTICAL])) sum += dp[i][j][VERTICAL];
////                    if (Objects.nonNull(dp[i][j][DIAGONAL])) sum += dp[i][j][DIAGONAL];
//
//                    if (sum > 10) System.out.print(sum + " ");
//                    else System.out.print(" "+ sum +" ");
//                }
//            }
//            System.out.println();
//        }
//
//        System.out.println();
//        System.out.println("DIAGONAL");
//        for (int i = 0; i < dp.length; i++) {
//            for (int j = 0; j < dp[i].length; j++) {
//                if (dp[i][j] == null) System.out.print("- ");
//                else {
//                    int sum = 0;
////                    if (Objects.nonNull(dp[i][j][HORIZONTAL])) sum += dp[i][j][HORIZONTAL];
////                    if (Objects.nonNull(dp[i][j][VERTICAL])) sum += dp[i][j][VERTICAL];
//                    if (Objects.nonNull(dp[i][j][DIAGONAL])) sum += dp[i][j][DIAGONAL];
//
//                    if (sum > 10) System.out.print(sum + " ");
//                    else System.out.print(" "+ sum +" ");
//                }
//            }
//            System.out.println();
//        }
//
//        System.out.println();
//        System.out.println("SUM");
//        for (int i = 0; i < dp.length; i++) {
//            for (int j = 0; j < dp[i].length; j++) {
//                if (dp[i][j] == null) System.out.print("- ");
//                else {
//                    int sum = 0;
//                    if (Objects.nonNull(dp[i][j][HORIZONTAL])) sum += dp[i][j][HORIZONTAL];
//                    if (Objects.nonNull(dp[i][j][VERTICAL])) sum += dp[i][j][VERTICAL];
//                    if (Objects.nonNull(dp[i][j][DIAGONAL])) sum += dp[i][j][DIAGONAL];
//
//                    if (sum > 10) System.out.print(sum + " ");
//                    else System.out.print(" "+ sum +" ");
//                }
//            }
//            System.out.println();
//        }
//    }

    private static int dfs(int y, int x, int nowDir) {
        if (nowDir == HORIZONTAL) {
            if (dp[y][x][HORIZONTAL] != null && dp[y][x][DIAGONAL] != null) {
                return dp[y][x][HORIZONTAL] + dp[y][x][DIAGONAL];
            }

        } else if (nowDir == VERTICAL) {
            if (dp[y][x][VERTICAL] != null && dp[y][x][DIAGONAL] != null) {
                return dp[y][x][VERTICAL] + dp[y][x][DIAGONAL];
            }

        } else if (nowDir == DIAGONAL) {
            if (dp[y][x][HORIZONTAL] != null  && dp[y][x][VERTICAL] != null && dp[y][x][DIAGONAL] != null) {
                return dp[y][x][HORIZONTAL] + dp[y][x][VERTICAL] + dp[y][x][DIAGONAL];
            }
        }

        if (nowDir == HORIZONTAL) {
            dp[y][x][HORIZONTAL] = 0;
            dp[y][x][DIAGONAL] = 0;

            // 우측
            if (canMove(y, x, HORIZONTAL))  {
                dp[y][x][HORIZONTAL] += dfs(y, x + 1, HORIZONTAL);
            }

            // 대각
            if (canMove(y, x, DIAGONAL)) {
                dp[y][x][DIAGONAL] += dfs(y + 1, x + 1, DIAGONAL);
            }

            return dp[y][x][HORIZONTAL] + dp[y][x][DIAGONAL];

        } else if (nowDir == VERTICAL) {
            dp[y][x][VERTICAL] = 0;
            dp[y][x][DIAGONAL] = 0;

            // 아래
            if (canMove(y, x, VERTICAL))  {
                dp[y][x][VERTICAL] += dfs(y + 1, x, VERTICAL);
            }

            // 대각
            if (canMove(y, x, DIAGONAL))  {
                dp[y][x][DIAGONAL] += dfs(y + 1, x + 1, DIAGONAL);
            }

            return dp[y][x][VERTICAL] + dp[y][x][DIAGONAL];

//        } else if (nowDir == DIAGONAL) {
        } else {
            dp[y][x][HORIZONTAL] = 0;
            dp[y][x][VERTICAL] = 0;
            dp[y][x][DIAGONAL] = 0;

            // 우측
            if (canMove(y, x, HORIZONTAL))  {
                int ny = y;
                int nx = x + 1;

                dp[y][x][HORIZONTAL] += dfs(ny, nx, HORIZONTAL);
            }

            // 아래
            if (canMove(y, x, VERTICAL))  {
                int ny = y + 1;
                int nx = x;

                dp[y][x][VERTICAL] += dfs(ny, nx, VERTICAL);
            }

            // 대각
            if (canMove(y, x, DIAGONAL))  {
                dp[y][x][DIAGONAL] += dfs(y + 1, x + 1, DIAGONAL);
            }

            return dp[y][x][HORIZONTAL] + dp[y][x][VERTICAL] + dp[y][x][DIAGONAL];
        }

//        printAll();
//        System.out.println("y = " + y);
//        System.out.println("x = " + x);
//        System.out.println("nowDir = " + nowDir);
//        System.out.println();
    }

    private static boolean isOutOfIndex(int ny, int nx) {
        return ny < 0 || ny >= N || nx < 0 || nx >= N;
    }

    private static boolean canMove(int y, int x, int nextDir) {
        if (nextDir == DIAGONAL) {
            return !isOutOfIndex(y + 1, x + 1) &&
                    !isWall[y + 1][x] && !isWall[y][x + 1] && !isWall[y + 1][x + 1];
        }

        if (nextDir == HORIZONTAL) {
            return !isOutOfIndex(y, x + 1) &&
                    !isWall[y][x + 1];
        }

        if (nextDir == VERTICAL) {
            return !isOutOfIndex(y + 1, x) &&
                    !isWall[y + 1][x];
        }

        return false;
    }

    private static boolean isBlockedBottom(int ny, int nx) {
        return ny == N - 1 && nx != N - 1;
    }

    private static boolean isBlockedRight(int ny, int nx) {
        return ny != N - 1 && nx == N - 1;
    }
}


/**
 * 파이프는 최초 (1,1), (1,2)에 위치
 * - (1,2)를 기준으로 3칸 중 한칸으로 이동가능
 * - 맨 끝 도달시
 *
 * - 가로 -> 오른쪽 혹은 대각선
 * - 세로 -> 아래 혹은 대각선
 * - 대각선의 경우 3가지 경우 모두 갈수 있음
 *
 *
 *
 *
 * 벽이 아닌 부분도 마찬가지이다. dp값을 활용할때, 수직/수평/대각선인 경우를 나눠서 수집해야한다.
 * 그렇지 않으면 활용한 dp값이 다른 방향일때의 값까지 포함해버린다.
 */