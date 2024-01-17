import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static final int[][] mapper = {
            {2, -2, 1, -1, 2, -2, 1, -1},
            {-1, -1, -2, -2, 1, 1, 2, 2}
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            int l = Integer.parseInt(br.readLine());

            Point start = new Point(br.readLine());
            Point end = new Point(br.readLine());

            System.out.println(bfs(start, end, l));
        }
    }

    private static int bfs(Point start, Point end, int l) {
        boolean[][] visited = new boolean[l][l];
        Queue<Point> queue = new LinkedList<>();

        visited[start.y][start.x] = true;
        queue.offer(start);

        int move = -1; // 처음 위치는 움직임이 없는 상태이기때문에 0으로 만들기위해 -1로 초기화
        while (!queue.isEmpty()) {
            int size = queue.size();
            move++;

            for (int i = 0; i < size; i++) {
                Point now = queue.poll();

                if (now.equals(end)) return move;

                for (int k = 0; k < 8; k++) {
                    int ny = now.y + mapper[0][k];
                    int nx = now.x + mapper[1][k];

                    if (ny >= l || ny < 0 || nx >= l || nx < 0 || visited[ny][nx]) continue;

                    queue.offer(new Point(ny, nx));
                    visited[ny][nx] = true;
                }
            }
        }

        return move;
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public Point(String location) {
            String[] yx = location.split(" ");
            this.y = Integer.parseInt(yx[0]);
            this.x = Integer.parseInt(yx[1]);
        }

        @Override
        public boolean equals(Object p) {
            return this.y == ((Point) p).y && this.x == ((Point) p).x;
        }

        @Override
        public int hashCode() {
            int result = y;
            result = 31 * result + x;
            return result;
        }
    }
}
