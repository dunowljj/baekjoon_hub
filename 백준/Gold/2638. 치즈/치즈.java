import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static final int INTERNAL_AIR = 0;
    public static final int CHEESE = 1;
    public static final int EXTERNAL_AIR = 2;
    static boolean[][] visited;
    static int N,M,cheeseCount;
    static int[][] grid;

    private final static int[][] mapper = {{0, 0, 1, -1}, {-1, 1, 0, 0}};


    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Queue<Point> cheeses = new LinkedList<>();
        grid = new int[N][M];
        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < M; x++) {
                int val = Integer.parseInt(st.nextToken());
                grid[y][x] = val;

                if (val == CHEESE) cheeses.offer(new Point(y, x));
                if (y == 0 || y == N - 1 || x == 0 || x == M - 1) grid[y][x] = EXTERNAL_AIR;
            }
        }

        checkInitialAir();

        cheeseCount = cheeses.size();
        int time = 0;
        while (cheeseCount > 0) {
            checkCheese(cheeses);
            time++;
        }

        System.out.print(time);
    }

    // 테두리(외부공기와 맞닿는 부분)부터 탐색하여 새로이 연결된 내부공기가 있는지 확인
    // 내부공기가 외부와 연결된다면, 외부공기로 갱신한다.
    private static void checkInitialAir() {
        visited = new boolean[N][M];
        for (int y = 1; y < N - 1; y++) {
            if (!visited[y][0]) checkAir(y, 0);
            if (!visited[y][M - 1]) checkAir(y, M - 1);
        }

        for (int x = 1; x < M - 1; x++) {
            if (!visited[0][x]) checkAir(0, x);
            if (!visited[N - 1][x]) checkAir(N - 1, x);
        }
    }

    private static void checkAir(int y, int x) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(y, x));
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];

                if (ny < 0 || ny >= N || nx < 0 || nx >= M) continue;
                if (visited[ny][nx]) continue;
                visited[ny][nx] = true;

                // 인접한 곳이 내부공기라면, 외부공기와 연결되어있음을 표시하고, 탐색을 이어간다.
                if (grid[ny][nx] == INTERNAL_AIR) {
                    queue.offer(new Point(ny, nx));
                    grid[ny][nx] = EXTERNAL_AIR;
                }
            }
        }
    }

    private static void checkCheese(Queue<Point> cheeses) {
        int size = cheeses.size();

        Queue<Point> remove = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            Point cheese = cheeses.poll();

            int count = 0;
            for (int dir = 0; dir < 4; dir++) {
                int ny = cheese.y + mapper[0][dir];
                int nx = cheese.x + mapper[1][dir];

                if (grid[ny][nx] == EXTERNAL_AIR) count++;
            }

            if (count >= 2) {
                remove.add(cheese);
            } else {
                cheeses.add(cheese);
            }
        }

        cheeseCount -= remove.size();
        while (!remove.isEmpty()) {
            Point poll = remove.poll();
            grid[poll.y][poll.x] = EXTERNAL_AIR;

            // 만약 녹은 위치에서 주변에 INTERNAL_AIR가 있다면, 외부공기로 바꾼다.
            checkAir(poll.y, poll.x);
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
 * 4변 중 2변 이상이 공기와 접촉 -> 1시간만에 녹아 없어짐.
 * 치즈 내부에 있는 공기는 외부공기가 아님
 *
 * N,M 5~100
 * 1회 전체 탐색 -> 10_000
 *
 *
 * 풀이
 * 1) 둘레먼저 탐색해서, 외부와 내부를 구분한다.
 * 2) 큐에 치즈 위치를 넣어놓고, 각 치즈 위치를 탐색하여 2면 치즈를 녹이고, 치즈 수를 센다.
 * 3) 시간 체크를 하면서 1,2를 반복한다.
 *
 * 가장자리엔 치즈가 놓이지 않는다.
 * 치즈가 녹아서 생긴 공기는 외부공기와 맞닿아 있으므로, 외부공기가 될 수 밖에 없다.
 *
 * 1)처럼 테두리에서 접근하기보다, 치즈가 녹은 위치에서 외부 공기와 접촉하는지 확인하는게 맞을 듯.
 */