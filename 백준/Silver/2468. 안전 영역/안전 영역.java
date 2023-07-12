import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private final static int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        // init grid, find max/min
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int[][] grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int num = Integer.parseInt(st.nextToken());
                grid[i][j] = num;
                min = Math.min(min, num);
                max = Math.max(max, num);
            }
        }

        // 강수량 별로 안전한 영역 최대 개수 구하기
        int answer = findMaxSafeAreaCount(N, min, max, grid);

        System.out.print(answer);
    }

    private static int findMaxSafeAreaCount(int N, int min, int max, int[][] grid) {
        int answer = 0;
        for (int rainfall = min - 1; rainfall <= max; rainfall++) {
            answer = Math.max(answer, countSafeArea(rainfall, grid, N));
        }
        return answer;
    }

    private static int countSafeArea(int rainfall, int[][] grid, int N) {
        boolean[][] visited = new boolean[N][N];
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visited[i][j] || grid[i][j] <= rainfall) continue;

                bfs(rainfall, grid, new Point(i,j), N, visited);
                count++;
            }
        }
        return count;
    }

    private static void bfs(int rainfall, int[][] grid, Point now, int N, boolean[][] visited) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(now);
        visited[now.x][now.y] = true;

        while (!queue.isEmpty()) {
            now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.x + mapper[0][i];
                int ny = now.y + mapper[1][i];

                if (nx < 0 || nx >= N || ny < 0 || ny >= N || visited[nx][ny] || grid[nx][ny] <= rainfall) continue;

                visited[nx][ny] = true;
                queue.offer(new Point(nx, ny));
            }
        }
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
/*
bfs로 탐색
[시간복잡도]
N 2~100

N^2(배열 순회) * 2N(bfs 최대 탐색) * 100(높이)
O(N^3) (* 높이 -> 1000만)

[공간복잡도]
O(N^2)

[과정]
0) max, min 높이 구해서 높이 범위 정하기
1) 2차원 배열 생성
2) 각 요소 방문 체크하면서 처음부터 순회. 안잠김 영역만 탐색
3) 탐색 : 각 요소에서 bfs로 안전영역인지 확인 : 안전영역 개수 카운트, 해당 영역 방문체크, 잠겼으면 탐색 x

모든 비의 양에 대해 조사를 해야한다.
높이는 1~100이다.
 */