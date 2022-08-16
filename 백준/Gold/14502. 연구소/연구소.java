import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Point1 {
    int x;
    int y;

    public Point1(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static final int ADDED_WALL = 3;
    static int N;
    static int M;
    static int[][] board;
    static int answer = 0;
    static int wallCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());

                if (board[i][j] == 1) {
                    wallCount++;
                }
            }
        }

        safeZoneDFS(0, 0, 0);

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }

    // 벽 3개 dfs로 지정. 각 경우의 수마다 bfs
    private static void safeZoneDFS(int depth, int row, int col) {
        if (depth == 3) {
            answer = Math.max(answer, bfs());
            return;
        }

        if (col == M) {
            safeZoneDFS(depth, row + 1, 0);
            return;
        }

        if (row == N) {
            return;
        }

        if (board[row][col] == 0) {
            board[row][col] = 1;
            safeZoneDFS(depth + 1, row, col + 1);
            board[row][col] = 0;
        }

        //0 인경우, 아닌 경우 모두 그냥 넘어가는 경우도 있어야
        safeZoneDFS(depth, row, col + 1);
    }

    private static int bfs() {
        Queue<Point1> queue = new LinkedList<>();
        boolean[][] infected = new boolean[N][M];

        int[][] mapper = {{1, -1, 0, 0}, {0, 0, -1, 1}};

        int infectCount = 0;
        // 감염 위치 찾아서 넣기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == 2) {
                    infected[i][j] = true;
                    infectCount++;
                    queue.add(new Point1(i, j));
                }
            }
        }


        while (!queue.isEmpty()) {
            Point1 point = queue.poll();
            int x = point.x;
            int y = point.y;


            for (int i = 0; i < 4; i++) {

                int nx = x + mapper[0][i];
                int ny = y + mapper[1][i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
                    continue;
                }

                // 현재 위치 감염 -> 애초에 감염된 것만 넣음 -> 처음 넣는 것은 감염이 아닐수도 있음
                // 주변이 벽이 아니고 비감염이면 감염처리
                if (board[nx][ny] != 1 && !infected[nx][ny]) {
                    infected[nx][ny] = true;
                    infectCount++;
                    queue.add(new Point1(nx, ny));
                }
            }
        }

        /*int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!infected[i][j] && board[i][j] == 0) {
                    count++;
                }
            }
        }*/
        return (N * M) - wallCount - infectCount - ADDED_WALL;
    }

}