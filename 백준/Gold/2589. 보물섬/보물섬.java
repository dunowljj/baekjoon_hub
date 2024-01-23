import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static final int SEA = -1;
    private static final int[] dy = {-1, 1, 0, 0};
    private static final int[] dx = {0, 0, -1, 1};

    private static int row;
    private static int col;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());

        int[][] origin = new int[row][col];

        for (int i = 0; i < row; i++) {
            String line = br.readLine();

            for (int j = 0; j < col; j++) {
                if (line.charAt(j) == 'W') {
                    origin[i][j] = SEA;
                }
            }
        }

        int maxDistance = 0;
        int[][] grid = null;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (origin[i][j] != SEA) {
                    grid = new int[row][col];
                    maxDistance = Math.max(maxDistance, getMaxDistance(grid, origin, new Point(i, j)));
                }
            }
        }

        System.out.print(maxDistance);
    }

    private static int getMaxDistance(int[][] grid, int[][] origin, Point start) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(start);
        grid[start.y][start.x] = 0;

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = now.y + dy[i];
                int nx = now.x + dx[i];

                if (outOfIndex(ny, nx) || origin[ny][nx] == SEA // 바다(-1)인 경우 스킵
                        || grid[ny][nx] != 0 || ny == start.y && nx == start.x)
                {
                    continue;
                }

                queue.offer(new Point(ny, nx));
                grid[ny][nx] = grid[now.y][now.x] + 1;
            }
        }

//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                System.out.print(grid[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println();

        return findMaxDistance(grid);
    }

    private static boolean outOfIndex(int ny, int nx) {
        return ny < 0 || ny >= row || nx < 0 || nx >= col;
    }

    private static int findMaxDistance(int[][] grid) {
        int max = -1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                max = Math.max(grid[i][j], max);
            }
        }
        return max;
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
/**
 * n^4 -> 2500*2500
 */