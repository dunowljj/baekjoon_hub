import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static final int NORMAL = 0;
    private static final int RIPEN = 1;
    private static final int EMPTY = -1;

    private static final int[][] mapper = {
            {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1},
            {1, 0, 0}, {-1, 0, 0}
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());

        int[][][] box = new int[H][N][M];
        int notRipenCount = 0;
        Queue<Point> ripenQueue = new LinkedList<>();
        for (int h = 0; h < H; h++) {
            for (int n = 0; n < N; n++) {
                st = new StringTokenizer(br.readLine());
                for (int m = 0; m < M; m++) {
                    int status = Integer.parseInt(st.nextToken());

                    if (status == RIPEN) ripenQueue.offer(new Point(h, n, m));
                    if (status == NORMAL) notRipenCount++;

                    box[h][n][m] = status;
                }
            }
        }



        int beforeCount = -1;
        int day = 0;
        while (!ripenQueue.isEmpty()) {
            int size = ripenQueue.size();

//            printSnapshot(M, N, H, box, day);

            if (notRipenCount == 0) break; // 다 익었다.
            if (notRipenCount == beforeCount) break; // 시간이 지나도 더 익지 않는다.

            beforeCount = notRipenCount;

            for (int i = 0; i < size; i++) {
                Point now = ripenQueue.poll();

                for (int dir = 0; dir < mapper.length; dir++) {
                    int nh = now.h + mapper[dir][0];
                    int ny = now.y + mapper[dir][1];
                    int nx = now.x + mapper[dir][2];

                    if (nh < 0 || nh >= H || ny < 0 || ny >= N || nx < 0 || nx >= M) continue;

                    if (box[nh][ny][nx] == NORMAL) {
                        box[nh][ny][nx] = RIPEN;
                        ripenQueue.offer(new Point(nh, ny, nx));
                        notRipenCount--;
                    }
                }
            }
            day++;
        }

        if (notRipenCount == 0) {
            System.out.print(day);
        } else {
            System.out.println(-1);
        }
    }

    private static void printSnapshot(int M, int N, int H, int[][][] box, int day) {
        System.out.println("----day:"+day+"-----");
        for (int h = 0; h < H; h++) {
            for (int n = 0; n < N; n++) {
                for (int m = 0; m < M; m++) {
                    System.out.print(box[h][n][m] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    static class Point {
        int h;
        int y;

        int x;

        public Point(int h, int y, int x) {
            this.h = h;
            this.y = y;
            this.x = x;
        }
    }
}
/**
 * 6방향 익음
 *
 *
 * 상자 공간 최대 100만
 *
 * 탐색마다 100만 검사를 해야하는지?
 * -> 토마토 개수를 미리 세자
 * -> 익을 수 없는 경우는 어떻게하나? -> depth마다 탐색해서 개수가 변하지 않는다면 종료
 */
