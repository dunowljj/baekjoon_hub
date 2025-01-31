import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int R, C;
    private final static int[][] mapper = {{0, 0, -1, 1}, {1, -1, 0, 0}};
    private static final char FIRE = 'F';
    private static final char JIHOON = 'J';
    private static final char EMPTY = '.';

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        boolean[][] canMove = new boolean[R][C]; // .만 움직일 수 있는 공간이다.
        Queue<Point> fires = new LinkedList<>();
        Queue<Point> bfsQueue = new LinkedList<>();

        for (int y = 0; y < R; y++) {
            String line = br.readLine();
            for (int x = 0; x < C; x++) {
                char ch = line.charAt(x);
                if (ch == FIRE) {
                    fires.offer(new Point(y, x));
                }

                // 어짜피 초기 위치에 돌아올 이유가 없으므로 false
                if (ch == JIHOON) {
                    bfsQueue.offer(new Point(y, x));
                }

                if (ch == EMPTY) {
                    canMove[y][x] = true;
                }
            }
        }

        int time = 0;
        while (!bfsQueue.isEmpty()) {
            spreadFire(fires, canMove);

            int size = bfsQueue.size();
            for (int i = 0; i < size; i++) {
                Point now = bfsQueue.poll();

                if (now.y == 0 || now.y == R - 1 || now.x == 0 || now.x >= C - 1) {
                    System.out.print(time + 1);
                    return;
                }

                for (int dir = 0; dir < mapper[0].length; dir++) {
                    int ny = now.y + mapper[0][dir];
                    int nx = now.x + mapper[1][dir];

                    if (!canMove[ny][nx]) continue;
                    canMove[ny][nx] = false;

                    bfsQueue.offer(new Point(ny, nx));
                }
            }

            time++;
        }

        System.out.print("IMPOSSIBLE");
    }

    private static void spreadFire(Queue<Point> fires, boolean[][] canMove) {
        int size = fires.size();

        for (int i = 0; i < size; i++) {
            Point now = fires.poll();

            for (int dir = 0; dir < mapper[0].length; dir++) {
                int ny = now.y + mapper[0][dir];
                int nx = now.x + mapper[1][dir];

                if (ny < 0 || ny >= R || nx < 0 || nx >= C || !canMove[ny][nx]) continue;
                canMove[ny][nx] = false;

                fires.offer(new Point(ny, nx));
            }
        }
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
 * 가장자리 탈출
 * 시작 시 인접해도 지훈이가 불타지 않음 -> 지훈이가 이동한 직후 불이 퍼진다고 봐야할듯
 *
 * 불과 지훈이는 벽을 통과못함
 */