import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}


public class Main {
    static int N;
    static int M;
    static boolean[][] cabbage;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());


        for (int c = 0; c < T; c++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken()); //가로
            N = Integer.parseInt(st.nextToken()); //세로
            int K = Integer.parseInt(st.nextToken());
            cabbage = new boolean[N][M];

            // 배추가 있는 곳 표시
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int X = Integer.parseInt(st.nextToken()); //가로
                int Y = Integer.parseInt(st.nextToken()); //세로
                cabbage[Y][X] = true;
            }

            int wormCount = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (cabbage[i][j]) {
                        cabbage[i][j] = false;
                        bfs(i, j);
                        wormCount++;
                    }
                }
            }

            bw.write(wormCount+"\n");
        }

        bw.flush();
        bw.close();
    }

    static void bfs(int i, int j) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(i, j));

        int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int x = point.x;
            int y = point.y;

            for (int dir = 0; dir < 4; dir++) {
                int nX = x + mapper[0][dir];
                int nY = y + mapper[1][dir];

                if (nX < 0 || nY < 0 || nX >= N || nY >= M) continue;

                if (cabbage[nX][nY]) {
                    cabbage[nX][nY] = false;
                    queue.add(new Point(nX, nY));
                }
            }
        }
    }

}

