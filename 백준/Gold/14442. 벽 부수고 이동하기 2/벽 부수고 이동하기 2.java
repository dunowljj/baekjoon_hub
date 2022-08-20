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
        int[][] visited = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!isWall[i][j]) {
                    visited[i][j] = -1;
                }
            }
        }
        //방문여부를 남은 부술수있는 벽의 개수로 갱신
        visited[0][0] = K;

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


                // 현재 부술 수 있는 벽의 개수가 더 큰 경우만
                if (visited[nx][ny] < k) {
                    visited[nx][ny] = k;

                    if (isWall[nx][ny]) {
                        k--;
                    }
                    queue.add(new Point3(nx, ny, k, point.count + 1));
                }

            }

        }
        return -1;
    }

}
/*
부술수있는 벽의 개수가 여러개

bfs를 사용하면 특정 위치(노드)에 도착하는 시점은 같다. 즉, 벽을 부순 횟수를 비교하여 효율을 높일 수 있다.
같은 위치에 도달했을때 걸린 횟수는 같으므로, 벽을 부순 횟수가 적은 것만 선택하면 되기 때문이다.

벽인 경우를 확인하기 위한 boolean[][] isWall 배열 + 방문배열 int[][] visited
isWall로 벽 여부를 확인하고, 방문배열에 부순 벽을 저장하면서 진행
방문배열에 부순 횟수 0과, 방문하지 않은곳 0을 구분하기 위해 벽이 없는 곳을 -1 처리
 */