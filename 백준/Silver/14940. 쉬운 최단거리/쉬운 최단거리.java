import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final int[][] mapper = {{0, 0, -1, 1}, {1, -1, 0, 0}};

    public static final int BLOCK = -1;
    public static final int CANNOT_APPROACH = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] answer = new int[n][m];

        Point start = new Point();
        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < m; x++) {
                int landStatus = Integer.parseInt(st.nextToken());

                if (landStatus == 0) {
                    answer[y][x] = BLOCK;
                }

                if (landStatus == 2) { // 목적지를 시작점으로 각 지점 탐색
                    answer[y][x] = 0;
                    start.y = y;
                    start.x = x;
                }
            }
        }

        boolean[][] visited = new boolean[n][m];
        Queue<Point> queue = new LinkedList<>();
        queue.offer(start);
        visited[start.y][start.x] = true;

        while (!queue.isEmpty()) {
            Point p = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = p.y + mapper[0][i];
                int nx = p.x + mapper[1][i];

                if (ny < 0 || ny >= n || nx < 0 || nx >= m
                || visited[ny][nx] || answer[ny][nx] == BLOCK) continue;

                visited[ny][nx] = true;
                answer[ny][nx] = answer[p.y][p.x] + 1;

                queue.offer(new Point(ny, nx));
            }
        }


        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (y == start.y && x == start.x) sb.append(0);
                else if (answer[y][x] == BLOCK) sb.append(0);
                else if (answer[y][x] == CANNOT_APPROACH) sb.append(-1);
                else sb.append(answer[y][x]);

                sb.append(' ');
            }
            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }

    static class Point {
        int y;
        int x;

        public Point() {}

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}

/**
 * 탐색법, 다익스트라
 */