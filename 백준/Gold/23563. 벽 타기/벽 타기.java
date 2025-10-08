import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int H,W,answer;
    private static int adjMove = -1;
    private static char[][] grid;
    private static boolean[][] isAdjWithWall;
    private static final int[][] mapper = {{0, 0, 1, -1}, {1, -1, 0, 0}};
    private static Point start,end;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        grid = new char[H][W];
        isAdjWithWall = new boolean[H][W];

        start = new Point();
        end = new Point();

        for (int i = 0; i < H; i++) {
            String line = br.readLine();

            for (int j = 0; j < W; j++) {
                char ch = line.charAt(j);
                grid[i][j] = ch;

                if (ch == 'S') {
                    start = new Point(i, j, 0);
                    grid[i][j] = '.';
                } else if (ch == 'E') {
                    end = new Point(i, j);
                    grid[i][j] = '.';
                }
            }
        }

        checkAdj();
        findAnswer();

        System.out.print(answer);
    }

    private static void checkAdj() {
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (grid[y][x] == '.') {
                    checkAdj(y, x);
                }
            }
        }
    }

    private static void checkAdj(int y, int x) {
        for (int i = 0; i < 4; i++) {
            int ny = y + mapper[0][i];
            int nx = x + mapper[1][i];

            if (ny < 0 || ny >= H || nx < 0 || nx >= W) continue;

            if (grid[ny][nx] == '#') {
                isAdjWithWall[y][x] = true;
            }
        }
    }

    private static void findAnswer() {
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.move));
        queue.offer(start);
        boolean[][] visited = new boolean[H][W];

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            if (visited[now.y][now.x]) continue;
            visited[now.y][now.x] = true;

            if (now.equals(end)) {
                answer = now.move;
                return;
            }

            // (이동) 인접한 '.' 탐색
            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];
                int nm = now.move + 1;

                if (ny < 0 || ny >= H || nx < 0 || nx >= W) continue;
                if (visited[ny][nx]) continue;

                if (grid[ny][nx] == '.') {
                    if (isAdjWithWall[now.y][now.x] && isAdjWithWall[ny][nx]) {
                        nm--;
                    }

                    queue.offer(new Point(ny, nx, nm));
                }
            }
        }
    }

    static class Point {
        int y;
        int x;
        int move;

        public Point() {}

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
            this.move = 0;
        }

        public Point(int y, int x, int move) {
            this.y = y;
            this.x = x;
            this.move = move;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;

            Point point = (Point) o;
            if (y != point.y) return false;
            return x == point.x;
        }

        @Override
        public int hashCode() {
            int result = y;
            result = 31 * result + x;
            return result;
        }
    }
}

/**
 * 상하좌우 1칸만 움직일 수 있다.
 * 벽을 타고 상하좌우 인접한 칸으로 이동할 수 있다.
 *
 * 즉, 벽을 타더라도 상하좌우로밖에 이동을 못한다.
 *
 *
 *  S # # #
 *  . . . .
 *  # # # E
 *  정답 : 0
 */