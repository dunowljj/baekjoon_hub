import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


class Point3 {
    int x;
    int y;
    int k;
    int count;

    public Point3(int x, int y, int k, int count) {
        this.x = x;
        this.y = y;
        this.k = k;
        this.count = count;
    }
}


public class Main {
    static int N;
    static int M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        boolean[][] isWall = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                if (input.charAt(j) == '1') {
                    isWall[i][j] = true;
                }
            }
        }

        int answer = bfs(K, isWall);

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }

    static int bfs(int K, boolean[][] isWall) {
        Queue<Point3> queue = new LinkedList<>();
        queue.add(new Point3(0, 0, K, 1));

        int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};
        boolean[][][] visited = new boolean[K + 1][N][M];
        visited[K][0][0] = true;
        visited[K - 1][0][0] = true;

        while (!queue.isEmpty()) {
            Point3 point = queue.poll();

            if (point.x == N - 1 && point.y == M - 1) {
                return point.count;
            }

            for (int i = 0; i < 4; i++) {
                int nx = point.x + mapper[0][i];
                int ny = point.y + mapper[1][i];
                int k = point.k;

                if (nx < 0 || ny < 0 || nx >= N || ny >= M
                || (isWall[nx][ny] && k == 0)) {
                    continue;
                }


                if (!visited[k][nx][ny]) {
                    visited[k][nx][ny] = true;

                    if (isWall[nx][ny]) {
                        k--;
                    }

                    queue.add(new Point3(nx, ny, k, point.count + 1));
                }

               /* if (isWall[nx][ny] && !visited[k][nx][ny]) {
                    visited[k][nx][ny] = true;
                    queue.add(new Point3(nx, ny, k - 1));
                }

                if (!isWall[nx][ny] && !visited[k][nx][ny]) {
                    visited[k][nx][ny] = true;
                    queue.add(new Point3(nx, ny, k));
                }*/
            }


        }
        return -1;
    }

}
/*
부술수있는 벽의 개수가 여러개
더 많이 부수고 온것과 더 적게 부수고 온것 모두 방문체크를하면 배열을 10배로 만들어야한다.
부순횟수를 비교해서 더 적게 부순 경우를 하기엔 애매하다. 최단 거리가 무엇인지 알 수 없기떄문이다.

 */