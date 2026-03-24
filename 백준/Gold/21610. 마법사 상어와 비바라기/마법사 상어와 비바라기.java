import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static int[][] grid;
    private static int N, M;
    private final static int[][] direction = {
            {0, -1},
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, 1},
            {1, 1}, {1, 0}, {1, -1}
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 인덱스 0~N-1 사용
        List<Point> clouds = new ArrayList<>(
                List.of(new Point(N - 2, 0), new Point(N - 2, 1),
                        new Point(N - 1, 0), new Point(N - 1, 1)
                ));



        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int dVal = Integer.parseInt(st.nextToken());
            int step = Integer.parseInt(st.nextToken());

            // 이동 후 물 1 증가 (좌표 넘기 가능)
            int[] dir = direction[dVal - 1];
            for (int j = 0; j < clouds.size(); j++) {
                Point cloud = clouds.get(j);
                cloud.y += (N * 50) + (dir[0] * step);
                cloud.x += (N * 50) + (dir[1] * step);
                // 음수값 인덱스 관리 필요

                cloud.y %= N;
                cloud.x %= N;

                grid[cloud.y][cloud.x]++;
            }

            // 대각선 검사 (좌표 넘기 불가)

            // 증가값 따로 저장
            int[][] inc = new int[N][N];
            for (int j = 0; j < clouds.size(); j++) {
                Point cloud = clouds.get(j);
                inc[cloud.y][cloud.x] += countDiagonal(cloud);
            }

            // 증가값 반영, 사용했던 위치 체크
            boolean[][] used = new boolean[N][N];
            for (int j = 0; j < clouds.size(); j++) {
                Point cloud = clouds.get(j);
                grid[cloud.y][cloud.x] += inc[cloud.y][cloud.x];
                used[cloud.y][cloud.x] = true;
            }

            clouds.clear();

            // 전체 검사해서 2 이상이면 구름 생성 후 감소
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (grid[r][c] >= 2 && !used[r][c]) {
                        grid[r][c] -= 2;
                        clouds.add(new Point(r, c));
                    }
                }
            }
        }

        int sum = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                sum += grid[r][c];
            }
        }

        System.out.print(sum);
    }

    private static int countDiagonal(Point cloud) {
        int r = cloud.y;
        int c = cloud.x;

        int count = 0;

        if (r - 1 >= 0) {
            if (c - 1 >= 0 && grid[r - 1][c - 1] > 0) count++;
            if (c + 1 < N && grid[r - 1][c + 1] > 0) count++;
        }

        if (r + 1 < N) {
            if (c - 1 >= 0 && grid[r + 1][c - 1] > 0) count++;
            if (c + 1 < N && grid[r + 1][c + 1] > 0) count++;
        }

        return count;
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
 * 이동 -> 2*2 물 증가 + 1 -> 대각선 검사 후 물있으면 증가 -> 2이상 인 모든 칸에 구름, -2씩 하기, 3에서 사라진 칸 제외.
 *
 *
 * 칸마다 거리가 1칸인 바구니 수 미리 구해놓기
 * 물복사에는 경계 넘어 칸은 대각선 거리 1이 아니다.
 *
 * 2500개 -> 100
 */