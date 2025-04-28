import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static final int[][] mapper = {{0, 1}, {1, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        String[][] map = new String[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                map[i][j] = st.nextToken();
            }
        }

        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(0, 0, Integer.parseInt(map[0][0])));

        int depth = 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Point now = queue.poll();

                if (now.y == N - 1 && now.x == N - 1) {
                    max = Math.max(max, now.result);
                    min = Math.min(min, now.result);
                    continue;
                }

                for (int dir = 0; dir < 2; dir++) {

                    int ny = now.y + mapper[0][dir];
                    int nx = now.x + mapper[1][dir];

                    if (ny < 0 || ny >= N || nx < 0 || nx >= N) continue;

                    // 다음 칸이 숫자인 경우
                    if (depth % 2 == 1) {
                        int val = Integer.parseInt(map[ny][nx]);
                        String operator = map[now.y][now.x];

                        int newResult = operate(now.result, operator, val);
                        queue.offer(new Point(ny, nx, newResult));

                    // 다음 칸이 연산자인 경우 -> 다음숫자까지 연산 보류
                    } else {
                        queue.offer(new Point(ny, nx, now.result));
                    }
                }
            }

            depth++;
        }

        System.out.print(max + " " + min);
    }

    private static int operate(int result, String operator, int val) {
        if (operator.equals("+")) {
            return result + val;
        }

        if (operator.equals("-")) {
            return result - val;
        }

        if (operator.equals("*")) {
            return result * val;
        }

        throw new RuntimeException("존재하지 않는 연산자");
    }

    static class Point {
        int y;
        int x;
        int result;

        public Point(int y, int x, int result) {
            this.y = y;
            this.x = x;
            this.result = result;
        }
    }
}
