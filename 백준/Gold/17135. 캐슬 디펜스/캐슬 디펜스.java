import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int N,M,D;
    private static int[][] grid;

    private static int maxKill = 0;
    private static int totalEnemy = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        grid = new int[N + 1][M + 1]; // 편의를 위해 궁수의 처음 위치까지 확
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int status = Integer.parseInt(st.nextToken());
                grid[i][j] = status;
                if (status == 1) totalEnemy++;
            }
        }

        arrangeArcher(0, 0, new int[3]);

        System.out.print(maxKill);
    }

    private static void arrangeArcher(int depth, int idx, int[] arrangement) {
        if (depth == 3) {
            simulate(arrangement);
            return;
        }

        for (int i = idx; i < M; i++) {
            arrangement[depth] = i;
            arrangeArcher(depth + 1, i + 1, arrangement);
        }
    }

    private static void simulate(int[] arrangement) {
        int y = N;
        int enemyCount = totalEnemy;

        int[][] copy = new int[N + 1][M + 1];
        for (int i = 0; i <= N; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, M + 1);
        }

        int killCount = 0;
        for (int step = 0; step < N; step++) {

            if (enemyCount == killCount) {
                maxKill = killCount;
                return;
            }

            Set<Point> kill = new HashSet<>();
            // 성의 위치가 한칸씩 위로 올라간다 가정해서, 적이 전진하는 것을 구현한다.
            // 이미 성을 지나간 적은 겨냥할 수 없다. 성을 한칸씩 위로 올리면서 위치보다 위만 탐색한다.
            for (int j = 0; j < arrangement.length; j++) {
                int x = arrangement[j];
                Point target = findNearest(y, x, step, copy);

                if (target != null) {
                    kill.add(target);
                }
            }

            killCount += kill.size();
            for (Point p : kill) {
//                System.out.println("killed; y:"+p.y+",x:"+p.x);
                copy[p.y][p.x] = 0;
            }
        }

        maxKill = Math.max(maxKill, killCount);
    }

    // 왼,위,오 방향 탐색
    private static final int[][] mapper = {{0, -1, 0}, {-1, 0, 1}};

    // 첫 요소는 궁수위치라 방문체크 불가
    private static Point findNearest(int y, int x, int step, int[][] copy) {
        y = y - step;
        boolean[][] visited = new boolean[N + 1][M + 1];
        visited[y][x] = true;
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(y, x));

        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            if (depth > D) break;

            for (int i = 0; i < size; i++) {
                Point now = queue.poll();

                if (copy[now.y][now.x] == 1) {
                    return now;
                }

                for (int j = 0; j < 3; j++) {
                    int ny = now.y + mapper[0][j];
                    int nx = now.x + mapper[1][j];

                    if (ny < 0 || ny >= N - step || nx < 0 || nx >= M) continue;
                    if (visited[ny][nx]) continue;
                    visited[ny][nx] = true;

                    queue.offer(new Point(ny, nx));
                }
            }
            depth++;
        }

        return null;
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public boolean equals(Object o) {
            Point point = (Point) o;

            if (y != point.y) return false;
            return x == point.x;
        }

        @Override
        public int hashCode() {
            int result = y;
            result = 31 * result + x;
            return result;
        }
    }
}
/**
 * 맨 아래행부터 올라오면서, 배치한 위치에서 적을 쏜다.
 * 쏘는 적을 탐색하는 것은 현재행부터 위에 있는 행을 확인
 *7
 * N,M은 3~15-> 최대 225칸
 * 격자칸 바로아래 한 행에만 성이 있다. 즉, 궁수 3명을 배치하는 경우의 수 15C3(455)이다.
 *
 * 455(배치조합) * D^2(대상탐색) * 3(궁수) * 15(열 개수,전진마다 탐색)
 *
 * D 1 -> 1칸
 * D 2 -> 3~4칸 (+3)
 * D 3 -> 6~9칸 (+5)
 * D 4 -> 10~16칸 (+7)
 * D 10 ->  ~100칸
 * 가까운 적이 여럿이면, 가장 왼쪽 공략
 */
