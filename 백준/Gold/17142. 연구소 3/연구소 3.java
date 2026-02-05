import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static final int[][] mapper = {{0, 0, 1, -1}, {-1, 1, 0, 0}};
    static final int WALL = 1;
    public static final int EMPTY = 0;
    static int[][] lab;
    static int N, M, emptyCount;
    static List<Point> viruses = new ArrayList<>();
    static int minTime = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        emptyCount = 0;
        lab = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                int val = Integer.parseInt(st.nextToken());
                lab[i][j] = val;

                if (val == 0) emptyCount++;
                if (val == 2) {
                    viruses.add(new Point(i, j));
                }
            }
        }

        if (emptyCount == 0) {
            System.out.print(0);
            return;
        }

        pick(0, 0, new int[M]);
        System.out.print(minTime == Integer.MAX_VALUE ? -1 : minTime);
    }

    public static void pick(int depth, int idx, int[] picked) {
        if (depth == M) {
            minTime = Math.min(minTime, findMinTimeByBFS(picked));
            return;
        }

        for (int i = idx; i < viruses.size(); i++) {
            picked[depth] = i;
            pick(depth + 1, i + 1, picked);
        }
    }

    private static int findMinTimeByBFS(int[] picked) {
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        for (int i = 0; i < picked.length; i++) {
            Point virus = viruses.get(picked[i]);
            queue.offer(virus);
            visited[virus.y][virus.x] = true;
        }

        int remain = emptyCount;

        int second = 1;
        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i++) {

                Point now = queue.poll();
                for (int dir = 0; dir < 4; dir++) {
                    int ny = now.y + mapper[0][dir];
                    int nx = now.x + mapper[1][dir];

                    if (ny < 0 || ny >= N || nx < 0 || nx >= N) continue;
                    if (lab[ny][nx] == WALL) continue;
                    if (visited[ny][nx]) continue;
                    visited[ny][nx] = true;

                    if (lab[ny][nx] == EMPTY) remain--;
                    if (remain == 0) return second;

                    queue.offer(new Point(ny, nx));
                }
            }
            second++;
        }

        return Integer.MAX_VALUE;
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
 * M은 최대 10
 *
 * 비활성 바이러스의 수는 10이하이다.
 * 모든 경우의 수를 구해도 충분하다.
 *
 */