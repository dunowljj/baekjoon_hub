import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static final char EMPTY = '.';
    private static final char WALL = '#';
    private static final char START = '@';
    private static final char FIRE = '*';
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static final int[][] mapper = {{0, 0, 1, -1}, {1, -1, 0, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());

            boolean escape = false;
            char[][] map = new char[h][w];
            boolean[][] fire = new boolean[h][w];
            boolean[][] visited = new boolean[h][w];
            Queue<Point> moveQueue = new LinkedList<>();
            Queue<Point> fireQueue = new LinkedList<>();

            for (int y = 0; y < h; y++) {
                String line = br.readLine();

                for (int x = 0; x < w; x++) {
                    char ch = line.charAt(x);

                    if (ch == START) {
                        if (y == 0 || y == h - 1 || x == 0 || x == w - 1) {
                            escape = true;
                            answer.append(1).append(System.lineSeparator());
                        }

                        visited[y][x] = true;
                        moveQueue.offer(new Point(y, x));
                    }

                    if (ch == FIRE) {
                        fireQueue.offer(new Point(y, x));
                        fire[y][x] = true;
                    }

                    map[y][x] = ch;
                }
            }

            int second = 1;
            while (!moveQueue.isEmpty() && !escape) {
                // 불 번짐
                int fireSize = fireQueue.size();
                for (int i = 0; i < fireSize; i++) {
                    Point firePoint = fireQueue.poll();

                    for (int dir = 0; dir < 4; dir++) {
                        int ny = firePoint.y + mapper[0][dir];
                        int nx = firePoint.x + mapper[1][dir];

                        if (ny < 0 || ny >= h || nx < 0 || nx >= w) continue;
                        if (fire[ny][nx] || map[ny][nx] == WALL) continue;

                        fire[ny][nx] = true;
                        fireQueue.offer(new Point(ny, nx));
                    }
                }

                // 불 번짐
                int size = moveQueue.size();
                for (int i = 0; i < size; i++) {
                    Point now = moveQueue.poll();

                    for (int dir = 0; dir < 4; dir++) {
                        int ny = now.y + mapper[0][dir];
                        int nx = now.x + mapper[1][dir];

                        if (ny < 0 || ny >= h || nx < 0 || nx >= w) continue;
                        if (visited[ny][nx] || fire[ny][nx] || map[ny][nx] == WALL) continue;

                        // 탈출 성공
                        if (ny == 0 || ny == h - 1 || nx == 0 || nx == w - 1) {
                            answer.append((second + 1)).append(System.lineSeparator());
                            escape = true;
                            break;
                        }

                        visited[ny][nx] = true;
                        moveQueue.offer(new Point(ny, nx));
                    }
                }
                second++;
            }

            if (!escape) answer.append(IMPOSSIBLE).append(System.lineSeparator());
        }

        System.out.print(answer.toString().trim());
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
 * 불이 옮겨진 칸 또는 이제 불이 붙으려는 칸으로 이동할 수 없다. -> 이게 무슨 의미?
 * 상근이의 이동이 1초가 걸린다 했으니, 도착 시점 기준으로 진입 가능 판정인 것 같다. 불이 먼저 번짐.
 */