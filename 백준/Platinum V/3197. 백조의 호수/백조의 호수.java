import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class Main {

    private static final int[][] mapper = {{0, 0, -1, 1}, {1, -1, 0, 0}};
    private static final char ICE = 'X';
    private static final char SWAN = 'L';
    private static final char WATER = '.';

    private static Queue<Point> nextSearchQueue = new LinkedList<>();
    private static Queue<Point> meltQueue = new LinkedList<>();
    private static boolean[][] visited;
    private static boolean[][] meltVisited;
    private static int R;
    private static int C;
    private static int iceCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        List<Point> swanPoints = new ArrayList<>();
        char[][] lake = new char[R][C];
        for (int r = 0; r < R; r++) {
            String row = br.readLine();
            for (int c = 0; c < C; c++) {
                char now = row.charAt(c);

                if (now == ICE) {
                    iceCount++;
                }

                else if (now == SWAN) {
                    swanPoints.add(new Point(r, c));
                    meltQueue.offer(new Point(r, c));
                }

                else if (now == WATER) {
                    meltQueue.offer(new Point(r, c));
                }

                lake[r][c] = now;
            }
        }

        Point start = swanPoints.get(0);
        Point end = swanPoints.get(1);


        // 다음 탐색 큐 말고 다른 부분에 녹는것도 생각해야한다. 전체 녹개될 빙하 체크
        meltVisited = new boolean[R][C];

        int day = 0;
        nextSearchQueue.offer(start);

        visited = new boolean[R][C];
        visited[start.r][start.c] = true;

        // 다음 탐색에 쓰일 큐가 탐색 큐 위치에 인수로 주어지고, 그 다음 탐색은 새 큐를 선언한다.
        while (!canMeet(lake, end) && iceCount > 0) {
            melt(lake);
            day++;
        }

        System.out.print(day);
    }

    private static boolean canMeet(char[][] lake, Point end) {
        Queue<Point> searchQueue = new LinkedList<>(nextSearchQueue);
        nextSearchQueue = new LinkedList<>();

        while (!searchQueue.isEmpty()) {
            Point now = searchQueue.poll();

            if (now.equals(end)) {
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nr = now.r + mapper[0][i];
                int nc = now.c + mapper[1][i];

                if (nr >= R || nr < 0 || nc >= C || nc < 0
                        || visited[nr][nc]
                ) continue;

                visited[nr][nc] = true;

                // ICE면 다음 탐색 시작점이 된다.
                if (lake[nr][nc] == ICE) {
                    nextSearchQueue.offer(new Point(nr, nc));
                }

                // SWAN 이거나 WATER 일때
                else {
                    searchQueue.offer(new Point(nr, nc));
                }
            }
        }
        return false;
    }

    private static void melt(char[][] lake) {
        // 빙판 개수 갱신
        int size = meltQueue.size();

        // 현재 주어진 요소 개수만큼만 탐색 및 녹이기
        for (int i = 0; i < size; i++) {
            Point alreadyMelt = meltQueue.poll();

            for (int dir = 0; dir < 4; dir++) {
                int nr = alreadyMelt.r + mapper[0][dir];
                int nc = alreadyMelt.c + mapper[1][dir];

                if (nr >= R || nr < 0 || nc >= C || nc < 0
                        || lake[nr][nc] != ICE) continue;

                lake[nr][nc] = WATER;
                iceCount--;
                meltQueue.offer(new Point(nr, nc));
            }
        }
    }

    static class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;

            Point point = (Point) o;
            if (r != point.r) return false;
            return c == point.c;
        }

        @Override
        public int hashCode() {
            int result = r;
            result = 31 * result + c;
            return result;
        }
    }
}