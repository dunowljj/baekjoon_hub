import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static boolean[][] canGo;
    static int N;
    static int M;
    static int answer = 0; //시작점 포함
    static int[][] mapper = {{1, 0, -1, 0}, {0, 1, 0, -1}};
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 0 = false , 1 = true
        // canGo -> 1~N, 1~M 사용
        canGo = new boolean[N + 2][M + 2];
        for (int i = 1; i <= N; i++) {
            String inputLine = br.readLine();
            for (int j = 0; j < M; j++) {
                canGo[i][j + 1] = (inputLine.charAt(j) - '0') == 0 ? false : true;
            }
        }

        // 시작점을 다시 방문 못하도록 미리 처리한 후, 시작점부터 탐색
        canGo[1][1] = false;
        bfs(1, 1);

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }
    static void bfs(int x, int y) {
        Queue<Point> queue = new LinkedList();
        queue.offer(new Point(x, y));

        while (!queue.isEmpty()) {

            // 현재 위치
            Point point = queue.poll();
            x = point.x;
            y = point.y;
            int depth = point.depth;

            // 갈수 있는 위치를 모두 큐에 넣기
            for (int i = 0; i < 4; i++) {
                int nx = x + mapper[0][i];
                int ny = y + mapper[1][i];
                int nd = point.depth + 1;

                // 해당 좌표에 갈 수 있으면 큐에 넣고 방문 불가 처리
                if (canGo[nx][ny]) {
                    queue.offer(new Point(nx, ny, nd));
                    canGo[nx][ny] = false; // 이제 못감


                    // 도착점에 도착 시 탈출, 현재 깊이 return
                    if (nx == N && ny == M) {
                        answer = nd;
                        return;
                    }
                }
            }
        }

    }
}
/*
- 1 == 이동 가능 경로
- (1,1) -> (N, M) 까지 이동하는 최소이동 칸 수 (2 <= N,M <= 100)
- 서로 인접한 칸만 이동
- 이동 칸수는 맨 처음 칸, 도착 위치를 포함한다.

최대 100*100, 1이 10000개라면?

너비우선으로 하면 다른가? -> 언제 카운트? 깊이마다 카운트해주고, 종료조건을 달아서 최소의 칸 수를 구한다.

 */
class Point{
    int x;
    int y;
    int depth = 1;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
    }
}