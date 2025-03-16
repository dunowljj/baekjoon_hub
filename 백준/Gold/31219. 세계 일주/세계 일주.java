import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static int VISIT_ALL;
    public static double INF = Math.pow(10,8);

    // 최대 국가간 거리 = sqrt2 * 10^6, 최대 국가간 거리 * 10 -> MAX값
    private static double[][] dp, adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            points.add(new Point(x, y));
        }

        adj = new double[n][n];
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                double dist = points.get(i).distanceTo(points.get(j));
                adj[i][j] = dist;
                adj[j][i] = dist;
            }
        }

        VISIT_ALL = (1 << n) - 1;
        dp = new double[n][VISIT_ALL + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.print(TPS(0, 1));
    }

    private static double TPS(int now, int bit) {
        if (bit == VISIT_ALL) {
            if (adj[now][0] == 0) return INF;
            else return adj[now][0];
        }

        if (dp[now][bit] != -1) {
            return dp[now][bit];
        }

        dp[now][bit] = INF;
        for (int next = 0; next < adj.length; next++) {
            if (adj[now][next] == 0) continue;
            if ((bit & (1 << next)) == 1) continue;

            dp[now][bit] = Math.min(dp[now][bit], TPS(next, (bit | (1 << next))) + adj[now][next]);
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

        public double distanceTo(Point target) {
            return Math.sqrt(
                    Math.pow(this.x - target.x, 2) + Math.pow(this.y - target.y, 2)
            );
        }
    }
}
