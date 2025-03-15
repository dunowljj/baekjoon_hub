import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static final int START = 1;

    // 최대거리 : 2000*sqrt2, 거리 합 최댓값 -> 최대거리(2000 * 1.x) * 16 -> 넉넉하게 10_000 * 16
    public static final double INF = 160_000;

    private static int N;

    private static Double[][] dp;
    private static Double[][] adj;

    private static int VISIT_ALL;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        List<Point> points  = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            points.add(new Point(x, y));
        }

        VISIT_ALL = (1 << N) - 1;
        dp = new Double[N + 1][VISIT_ALL + 1];
        adj = new Double[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            Point now = points.get(i - 1);
            for (int j = 1; j <= N; j++) {
                Point next = points.get(j - 1);

                adj[i][j] = now.distanceTo(next);
                adj[j][i] = now.distanceTo(next);
            }
        }

        System.out.println(TPS(START, 1));
    }

    private static double TPS(int now, int bit) {
        if (bit == VISIT_ALL) {
            if (adj[now][START] == 0) return INF;
            else return adj[now][START];
        }

        if (dp[now][bit] != null) {
            return dp[now][bit];
        }

        dp[now][bit] = INF;

        for (int nextNo = 1; nextNo <= N; nextNo++) {
            if (adj[now][nextNo] == null) continue;
            if ((bit & (1 << (nextNo-1))) == 1) continue;

            int nb = (bit | (1 << (nextNo-1)));

            dp[now][bit] = Math.min(dp[now][bit], TPS(nextNo, nb) + adj[now][nextNo]);
        }

        return dp[now][bit];
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double distanceTo(Point point) {
            return Math.sqrt(
                    Math.pow(point.x - this.x, 2) + Math.pow(point.y - this.y, 2)
            );
        }
    }
}
