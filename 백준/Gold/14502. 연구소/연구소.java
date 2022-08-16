import java.io.*;
import java.util.*;

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
//    public static final int ADDED_WALL = 3;
    static int N;
    static int M;
    static int[][] board;
    static int answer = 0;
//    static int wallCount = 0;
    static int infectCount = 0;
//    static int initialInfected = 0;
    static int[][] mapper = {{1, -1, 0, 0}, {0, 0, -1, 1}};
    static List<Coordinate> infected = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        final int ADDED_WALL = 3;
        int initialInfected = 0;
        int wallCount = 0;

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

                if (board[i][j] == 2) {
                    initialInfected++;
                    infected.add(new Coordinate(i,j));
                }
            }
        }

        setWallDFS(0, 0, 0);

        bw.write((answer - initialInfected - wallCount - ADDED_WALL) + "");
        bw.flush();
        bw.close();
    }

    // 벽 3개 dfs로 지정. 각 경우의 수마다 bfs
    private static void setWallDFS(int depth, int row, int col) {
        if (depth == 3) {
            answer = Math.max(answer, bfs());
            return;
        }

        if (col == M) {
            setWallDFS(depth, row + 1, 0);
            return;
        }

        if (row == N) {
            return;
        }

        if (board[row][col] == 0) {
            board[row][col] = 1;
            setWallDFS(depth + 1, row, col + 1);
            board[row][col] = 0;
        }

        //0 인경우, 아닌 경우 모두 그냥 넘어가는 경우도 있어야
        setWallDFS(depth, row, col + 1);
    }

    private static int bfs() {
        Queue<Coordinate> queue = new LinkedList<>();

        // 감염 위치만 큐에 넣기
        for (Coordinate coordinate : infected) {
            queue.add(coordinate);
        }

        infectCount = 0;
        while (!queue.isEmpty()) {
            Coordinate point = queue.poll();
            int x = point.x;
            int y = point.y;

            for (int i = 0; i < 4; i++) {
                int nx = x + mapper[0][i];
                int ny = y + mapper[1][i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
                    continue;
                }

                // 0인 경우만 감염된다.
                if (board[nx][ny] == 0) {
                    board[nx][ny] = -1;
                    infectCount++;
                    queue.add(new Coordinate(nx, ny));
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == -1) {
                    board[i][j] = 0;
                }
            }
        }

        return (N * M) - infectCount;
    }

}
/*
## 조건
0 빈칸 , 1 벽, 2 바이러스
안전영역 -> 벽과 바이러스 제외한 0 부분
2의 개수는 2보다 크거나 같다.
벽은 꼭 3개를 세워야 한다.

## 시간 2초
3 <= N,M <= 8  ; 최대 8*8 -> 64칸
벽 3개 선택 -> N6제곱
--> 2의 18제곱  * bfs(N제곱 * 주변탐색) : 모두 탐색해도 충분하다.

## 풀이
### 큐에 넣는 조건?
감염된 위치만 큐에 먼저 넣어놓고, 주변에 0을 감염시키는 방식으로 구현한다. 0,1,2와 겹치지 않는 숫자값을 입력 배열에 집어넣고
연산 후 다시 초기화시켜주는 방식을 사용하면 메모리와 시간을 절약할 수 있다.
 */