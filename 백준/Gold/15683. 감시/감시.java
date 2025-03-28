import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final int[] LEFT = {0, -1};
    private static final int[] RIGHT = {0, 1};
    private static final int[] UP = {-1, 0};
    private static final int[] DOWN = {1, 0};

    // cctv     종류              바라보는 방법
    // 1        한방향             4가지
    // 2        두방향(서로 반대)    2가지
    // 3        두방향(직각)        4가지
    // 4        세방향             4가지
    // 5        네방향             1가지

    // cctvNo는 cctv 숫자로 (입력 -1)이다.
    // int[cctvNo][a][b][c] -> a개만큼 감시하는 방법 개수 존재. b개수 만큼의 방향을 볼 수 있음. c는 y,x구분
    static final int[][][][] cctvDirs = new int[][][][]{
            new int[][][]{{LEFT}, {RIGHT}, {UP}, {DOWN}},
            new int[][][]{{LEFT, RIGHT}, {UP, DOWN}},
            new int[][][]{{LEFT, UP}, {UP, RIGHT}, {RIGHT, DOWN}, {DOWN, LEFT}},
            new int[][][]{{LEFT, UP, RIGHT}, {UP, RIGHT, DOWN}, {RIGHT, DOWN, LEFT}, {DOWN, LEFT, UP}},
            new int[][][]{{LEFT, RIGHT, UP, DOWN}}
    };
    public static final int WALL = 6;

    static int N,M;
    static int answer = Integer.MAX_VALUE;

    static int wallCount = 0;
    static int[][] office;

    static final List<Point> CCTVs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        office = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                office[i][j] = Integer.parseInt(st.nextToken());

                if (1 <= office[i][j] && office[i][j] <= 5) {
                    CCTVs.add(new Point(i, j));
                }

                if (office[i][j] == WALL) wallCount++;
            }
        }

        dfs(0, new ArrayList<>());

        System.out.print(answer);
    }

    // [cctv종류,바라보는 방향]에 대한 모든 조합을 만든다.
    private static void dfs(int depth, List<int[]> comb) {
        if (depth == CCTVs.size()) {

//            for (int i = 0; i < comb.size(); i++) {
//                System.out.print(comb.get(i)[0]);
//                System.out.print(","+comb.get(i)[1]+" ");
//            }
//            System.out.println();

            int minBlindZone = findBlindZoneWhen(comb);
            answer = Math.min(answer, minBlindZone);
            return;
        }

        Point point = CCTVs.get(depth);
        int kindOfCCTV = office[point.y][point.x] - 1;

        for (int dirIdx = 0; dirIdx < cctvDirs[kindOfCCTV].length; dirIdx++) {
            comb.add(new int[]{kindOfCCTV, dirIdx});
            dfs(depth + 1, comb);
            comb.remove(comb.size() - 1);
        }
    }

    private static int findBlindZoneWhen(List<int[]> comb) {
        int canMonitor = 0;
        boolean[][] visited = new boolean[N][M]; // 중복 카운팅 방지

        for (int i = 0; i < CCTVs.size(); i++) {
            Point point = CCTVs.get(i);

            if (!visited[point.y][point.x]) canMonitor++;
            visited[point.y][point.x] = true;

            int kind = comb.get(i)[0];
            int dir = comb.get(i)[1];

            for (int[] d : cctvDirs[kind][dir]) {
                int ny = point.y + d[0];
                int nx = point.x + d[1];

                while (0 <= ny && ny < N && 0 <= nx && nx < M) {
                    if (office[ny][nx] == WALL) break; // 벽이면 탐색 종료

                    // 칸당 1회만 카운팅
                    if (!visited[ny][nx]) {
                        visited[ny][nx] = true;
                        canMonitor++;
                    }

                    ny = ny + d[0];
                    nx = nx + d[1];
                }
            }
        }

        return (N * M) - canMonitor - wallCount;
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
 * n,m 8 -> 최대 64칸
 * cctv 최대 8개
 *
 * cctv 종류              바라보는 방법
 * 1 한방향                  4가지
 * 2 두방향(서로 반대)         2가지
 * 3 두방향(직각)             4가지
 * 4 세방향                  4가지
 * 5 네방향                  1가지
 *
 * 6은 벽
 * 회전 항상 90도 방향으로 가로 또는 세로만 바라봄
 *
 *
 * 64칸에 만약 4가지 방법을 가진 cctv가 8대라면, 64 * 4^8개   -> 64 * 2^16
 *
 * 각 cctv에 대해 어떻게 해야할지?
 *
 * 예제를 보면, cctv가 있는 위치, 벽이 있는 위치는 사각지대로 치지 않는다.
 * 예제 4를 보면 cctv는 다른 cctv를 통과 가능하다.
 */
