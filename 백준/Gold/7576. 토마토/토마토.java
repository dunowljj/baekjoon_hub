import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final int RIPE = 1;
    public static final int EMPTY = -1;
    public static final int UNRIPE = 0;
    static int[][] mapper = {{0, 0, -1, 1}, {1, -1, 0, 0}};
    static int day = 0;
    static int unripeCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int[][] box = new int[N][M];

        Queue<Point> queue = new LinkedList<>(); // 익은 토마토 미리 넣는 큐

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int tomatoStatus = Integer.parseInt(st.nextToken());
                box[i][j] = tomatoStatus;

                if (tomatoStatus == UNRIPE) {
                    unripeCount++;
                } else if (tomatoStatus == RIPE) {
                    queue.offer(new Point(i, j));
                }
            }
        }

        while (true) {
            // 이미 다 익었으면 0인 day값 출력
            if (areAllUnripe(unripeCount)) break;

            // 모두 익지 않았는데 큐가 비어있으면 모두 익지 못하는 상황이다.
            else if (queue.isEmpty()) {
                day = -1;
                break;
            }

            passOneDay(queue, box, N, M); // 하루치만 탐색하고 큐에 데이터 유지
            day++;
        }

        System.out.print(day);
    }

    private static boolean areAllUnripe(int unripeCount) {
        return unripeCount == 0;
    }

    private static void passOneDay(Queue<Point> queue, int[][] box, int n, int m) {
        int size = queue.size();

        for (int i = 0; i < size; i++) {

            Point now = queue.poll();
            for (int dir = 0; dir < 4; dir++) {
                int nx = now.x + mapper[0][dir];
                int ny = now.y + mapper[1][dir];

                // 이미 익었거나, 빈칸은 탐색하지 않는다. -> "익지 않은 경우(UNRIPE)"를 제외하고 탐색하지 않음
                if (nx < 0 || nx >= n || ny < 0 || ny >= m
                        || box[nx][ny] != UNRIPE) continue;

                box[nx][ny] = RIPE;
                unripeCount--;
                queue.offer(new Point(nx, ny));
            }
        }

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
/*
1 : 익은 토마토
0 : 익지 않은 토마토
-1 : 빈칸

문제 : 며칠이 지나야 모두 익는가?

[시간]
(익은 토마토의 위치의 수 * 4)를 최대 N+M번 탐색 -> 익은 토마토의 위치에 따라 연산 수가 상이하다.

[공간]
N*M
 */