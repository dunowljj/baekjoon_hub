import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Point1 {
    int x;
    int y;
    int count;
    boolean canSmash;
    public Point1(int x, int y, int count, boolean canSmash) {
        this.x = x;
        this.y = y;
        this.count = count;
        this.canSmash = canSmash;
    }
}

public class Main {
    static boolean[][] isWall;
    static int N;
    static int M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        isWall = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                if (input.charAt(j) == '1') {
                    isWall[i][j] = true;
                }
            }
        }

        bw.write(bfs()+"");
        bw.flush();
        bw.close();
    }

    private static int bfs() {
        Queue<Point1> queue = new LinkedList<>();
        queue.add(new Point1(0, 0, 1, true));

        int mapper[][] = {{1, -1, 0, 0}, {0, 0, 1, -1}};
        boolean[][][] visited = new boolean[2][N][M]; // 0 벽부순적 없음, 1 있음
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            Point1 point = queue.poll();

            // 도착 시 종료
            if (point.x == N - 1 && point.y == M - 1) {
                return point.count;
            }

            // 4방향 탐색
            for (int i = 0; i < 4; i++) {
                int nx = point.x + mapper[0][i];
                int ny = point.y + mapper[1][i];
                int nc = point.count + 1;
                boolean ns = point.canSmash;
                int bit = ns == true ? 0 : 1;

                // 인덱스 벗어나는 경우
                if (nx < 0 || ny < 0 || nx >= N || ny >= M
                || (!point.canSmash && isWall[nx][ny])) {
                    continue;
                }

                if (point.canSmash && isWall[nx][ny]) {
                    ns = false;
                    bit = 1;
                }

                if (!visited[bit][nx][ny]) {
                    visited[bit][nx][ny] = true;
                    queue.add(new Point1(nx, ny, nc, ns));
                }
                
               /* // 벽인 경우
                if (isWall[nx][ny]) {
                    // 부술수 있는지, 방문 가능인지 체크
                    if (point.canSmash && !visited[0][nx][ny]) { // 여기서는 0,1무관함
                        visited[bit][nx][ny] = true;
                        queue.add(new Point1(nx, ny, nc, false));
                    }
                }
                // 벽이 아닌 경우 - 벽을 부쉈는지 여부에 따라 따로 방문체크
                else {

                    if (!visited[bit][nx][ny]) {
                        visited[bit][nx][ny] = true;
                        queue.add(new Point1(nx, ny, nc, ns));
                    }

                }*/
            }
        }
        return -1;
    }

}
/*
# 조건
시작 칸, 끝 칸 포함한 최단 경로
시작,끝칸은 항상 0
0 이동 가능, 1 이동 불가

# 시간
벽 하나를 부수는 모든 경우에 대해 탐색 -> 최악 100만 근접
100만 * 100만?.. -> 9개 10억 -> 시간 초과

# 풀이
탐색을 하면서 벽을 부수는 경우를 반영한다.

벽을 부순 경우와 벽을 부수지 않은 경우가 특정 지점에 동시에 도달한다면, 벽을 부수지 않은 경우가 우선되어야 한다.
사실상 벽을 부수지 않은 경우의 방문체크가 벽을 부수고 온 경우에 영향을 받지 않고 이루어져야한다.

벽을 부순 경우가 먼저 치고 나가서 방문체크를 해버리면 답이 틀린다.
ex)
0000
1110
1000
1111
0011
0000

 */