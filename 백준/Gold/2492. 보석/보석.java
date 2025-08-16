import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        List<Point> stones = new ArrayList<>();
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());

            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            stones.add(new Point(A, B));
        }

        Set<Integer> xs = new HashSet<>();
        Set<Integer> ys = new HashSet<>();

        for (Point p : stones) {
            xs.add(clamp(p.x,     0, N - K));
            xs.add(clamp(p.x - K, 0, N - K));
            ys.add(clamp(p.y,     K, M));
            ys.add(clamp(p.y + K, K, M));
        }

        int answer = 0;
        int maxY = -1;
        int maxX = -1;
        for (Integer x : xs) {
            for (Integer y : ys) {
                int count = 0;

                for (Point p : stones) {
                    if (
                            (x <= p.x && p.x <= x + K) &&
                            (y - K <= p.y && p.y <= y)
                    ) count++;
                }

                if (count > answer) {
                    answer = count;
                    maxX = x;
                    maxY = y;
                }
            }
        }

        System.out.println(maxX + " "+maxY);
        System.out.print(answer);
    }

    private static int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(v, hi));
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

/**
 */