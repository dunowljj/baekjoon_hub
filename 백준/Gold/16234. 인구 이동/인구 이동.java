import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final int[][] mapper = {{0,0,-1,1},{1,-1,0,0}};
    private static int N, L, R;

    private static int[][] population;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        population = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                population[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int moveCount = 0;
        while (move()) {
            moveCount++;
        }

        System.out.print(moveCount);
    }

    private static boolean move() {
        boolean[][] visited = new boolean[N][N];
        boolean moved = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    moved |= bfs(new Point(i, j), visited);
                }
            }
        }

        return moved;
    }

    // 현위치부터 국경이 열리는 곳만 방문체크 하고, 탐색을 이어나간다.
    private static boolean bfs(Point start, boolean[][] visited) {
        boolean moved = false;
        List<Point> union = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();

        union.add(start);
        queue.offer(start);
        visited[start.y][start.x] = true;

        int pSum = population[start.y][start.x];
        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];

                if (ny < 0 || ny >= N || nx < 0 || nx >= N) continue;
                if (visited[ny][nx]) continue;

                int pDiff = Math.abs(population[now.y][now.x] - population[ny][nx]);

                // 국경 오픈
                if (L <= pDiff && pDiff <= R) {
                    visited[ny][nx] = true;
                    moved = true;
                    Point next = new Point(ny, nx);
                    union.add(next);
                    queue.offer(next);

                    pSum += population[ny][nx];
                }
            }
        }

        movePeople(union, (pSum / union.size()));

        return moved;
    }

    private static void movePeople(List<Point> union, int pPerCountry) {
        for (Point p : union) {
            population[p.y][p.x] = pPerCountry;
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
 * 소수점은 버린다.
 * 인접(국경선 공유) 시 합해서 인구를 반씩 나눠가짐
 *
 * 1) BFS로 국경이 열리는 곳만 탐색해서 한번에 찾기. 국경 열었던 곳은 방문체크해서 다시 방문 안하도록 하기
 * 2) 동시에 sum과 국가 위치를 저장하고, 1) 후에 인구 나눠주기
 */