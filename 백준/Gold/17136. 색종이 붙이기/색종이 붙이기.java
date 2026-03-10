import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[] remain = {0, 5, 5, 5, 5, 5};
    static int[][] paper = new int[10][10];
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;



        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 10; j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0);

        if (min == Integer.MAX_VALUE) System.out.print(-1);
        else System.out.print(min);
    }

    private static void dfs(int depth) {
        Point find = findFirstOne();
        if (find == null) {
            min = Math.min(min, depth);
            return;
        }

        for (int size = 5; size > 0; size--) {
            if (attachable(find, size) && remain[size] > 0) {
                write(find, size, 0);
                remain[size]--;

                dfs(depth + 1);
                write(find, size, 1);
                remain[size]++;
            }
        }
    }

    private static boolean attachable(Point find, int size) {
        if (find.y + size > 10 || find.x + size > 10) return false;

        for (int i = find.y; i < find.y + size; i++) {
            for (int j = find.x; j < find.x + size; j++) {
                if (paper[i][j] == 0) return false;
            }
        }

        return true;
    }

    private static void write(Point find, int size, int val) {
        for (int i = find.y; i < find.y + size; i++) {
            for (int j = find.x; j < find.x + size; j++) {
               paper[i][j] = val;
            }
        }
    }

    private static Point findFirstOne() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (paper[i][j] == 1) {
                    return new Point(i, j);
                }
            }
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
    }
}

/**
 *
 */