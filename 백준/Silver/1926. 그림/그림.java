import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int[][] mapper = {{0, 0, -1, 1}, {-1, 1, 0, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        boolean[][] colored = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                colored[i][j] = Integer.parseInt(st.nextToken()) == 1 ? true : false;
            }
        }

        int max = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (colored[i][j]) {
                    count++;
                    max = Math.max(bfs(colored, new Point(i, j), n, m), max);
                }
            }
        }

        System.out.println(count);
        System.out.print(max);
    }

    private static int bfs(boolean[][] colored, Point start, int n, int m) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(start);

        int count = 1;
        colored[start.y][start.x] = false;
        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];

                if (ny < 0 || ny >= n || nx < 0 ||  nx >= m
                        || !colored[ny][nx]) continue;

                queue.offer(new Point(ny, nx));
                colored[ny][nx] = false;
                count++;
            }
        }

        return count;
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
