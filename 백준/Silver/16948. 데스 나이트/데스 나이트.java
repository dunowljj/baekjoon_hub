import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.StringTokenizer;

class Point {
    int x;
    int y;
    int moveCount;

    public Point(int x, int y, int moveCount) {
        this.x = x;
        this.y = y;
        this.moveCount = moveCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        boolean[][] visited = new boolean[N][N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        int startX = Integer.parseInt(st.nextToken());
        int startY = Integer.parseInt(st.nextToken());
        Point start = new Point(startX, startY, 0);

        int destX = Integer.parseInt(st.nextToken());
        int destY = Integer.parseInt(st.nextToken());
        Point dest = new Point(destX, destY, 0);


        int answer = bfs(N, visited, start, dest);
        bw.write(answer+"");
        bw.flush();
        bw.close();
    }
    static int bfs(int N, boolean[][] visited, Point start, Point dest) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);

        int[][] mapper = {{-2,-2,0,0,+2,+2},{-1,+1,-2,+2,-1,+1}};

        while (!queue.isEmpty()) {
            start = queue.poll();
            if (start.equals(dest)) {
                return start.moveCount;
            }

            for (int i = 0; i < 6; i++) {
                int nx = start.x + mapper[0][i];
                int ny = start.y + mapper[1][i];
                int move = start.moveCount;

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
                    continue;
                }

                if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new Point(nx, ny, move + 1));
                }
            }
        }
        return -1;
    }
}
/*
체스판 크기 5~100 -> 방문여부 비트마스크 사용 불가?

데스나이트는 나의트와 다르다. 이동경로의 모양을 그려보면, 갈 수 있는 공간이 한정적이다

bfs탐색
 */