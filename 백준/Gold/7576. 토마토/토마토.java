import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final int RIPEN = 1;
    public static final int EMPTY = -RIPEN;
    public static final int NOT_RIPEN = 0;
    static int[][] mapper = {{0, 0, -1, 1}, {1, -1, 0, 0}};

    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int[][] box = new int[N][M];

        boolean alreadyRipen = true; // 주어진 박스에 토마토가 모두 익은 경우 체크
        Queue<Point> queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int tomatoStatus = Integer.parseInt(st.nextToken());
                box[i][j] = tomatoStatus;

                if (tomatoStatus == NOT_RIPEN) alreadyRipen = false;
                else if (tomatoStatus == RIPEN) queue.offer(new Point(i, j));
            }
        }

        if (alreadyRipen) {

        } else {
            while (true) {
                bfs(queue, box, N, M); // 하루치만 탐색하고 큐에 데이터 유지
                answer++;
                if (areAllRipen(box, N, M)) break;
                // 모두 익지 않았는데 큐가 비어있으면 모두 익지 못하는 상황이다.
                else if (queue.isEmpty()) {
                    answer = -1;
                    break;
                }
            }
        }

        System.out.print(answer);
    }

    private static void bfs(Queue<Point> queue, int[][] box, int n, int m) {
        int size = queue.size();

        for (int i = 0; i < size; i++) {

            Point now = queue.poll();
            for (int dir = 0; dir < 4; dir++) {
                int nx = now.x + mapper[0][dir];
                int ny = now.y + mapper[1][dir];

                // 이미 익었거나, 빈칸은 탐색하지 않는다. -> 익지 않은 경우를 제외하고 탐색하지 않음
                if (nx < 0 || nx >= n || ny < 0 || ny >= m
                        || box[nx][ny] != NOT_RIPEN) continue;

                box[nx][ny] = RIPEN;
                queue.offer(new Point(nx, ny));
            }
        }

    }

    private static boolean areAllRipen(int[][] box, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (box[i][j] == NOT_RIPEN) return false;
            }
        }
        return true;
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

익었는지 검사할때마다 N*M(100만) 만큼 순회
100만을 순회하면서 4개씩 탐색?
익은 토마토 위치를 기억하는게 의미가 있을까?

 */