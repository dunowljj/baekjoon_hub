import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static int W,H,K;
    public static final int OBSTACLE = 1;

    public static final int[][] mapper = {{0,0,-1,1},{1,-1,0,0}};
    public static final int[][] hMapper = {{2,2,1,1,-2,-2,-1,-1},{-1,1,-2,2,-1,1,-2,2}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        K = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        boolean[][] isBlock = new boolean[H][W];
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W; j++) {
                int val = Integer.parseInt(st.nextToken());
                if (val == OBSTACLE) {
                    isBlock[i][j] = true;
                }
            }
        }

        boolean[][][] visited = new boolean[H][W][K + 1];
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(0, 0, 0));
        visited[0][0][0] = true;

        int move = 0;
        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Point now = queue.poll();

                if (now.y == H - 1 && now.x == W - 1) {
                    System.out.print(move);
                    return;
                }

                for (int dir = 0; dir < mapper[0].length; dir++) {
                    int ny = now.y + mapper[0][dir];
                    int nx = now.x + mapper[1][dir];

                    if (ny < 0 || ny >= H || nx < 0 || nx >= W) continue;
                    if (isBlock[ny][nx]) continue;
                    if (visited[ny][nx][now.k]) continue;
                    visited[ny][nx][now.k] = true;

                    queue.offer(new Point(ny, nx, now.k));
                }

                if (now.k >= K) continue;

                for (int dir = 0; dir < hMapper[0].length; dir++) {
                    int ny = now.y + hMapper[0][dir];
                    int nx = now.x + hMapper[1][dir];

                    if (ny < 0 || ny >= H || nx < 0 || nx >= W) continue;
                    if (isBlock[ny][nx]) continue;
                    if (visited[ny][nx][now.k+1]) continue;
                    visited[ny][nx][now.k+1] = true;

                    queue.offer(new Point(ny, nx, now.k + 1));
                }
            }
            move++;
        }
        System.out.print(-1);
    }

    static class Point {
        int y;
        int x;

        int k;

        public Point(int y, int x, int k) {
            this.y = y;
            this.x = x;
            this.k = k;
        }
    }
}
/**
 *  W,H 1~200
 *  K 0~30
 */