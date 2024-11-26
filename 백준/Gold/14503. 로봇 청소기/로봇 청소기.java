import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int y = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        int[][] room = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int val = Integer.parseInt(st.nextToken());
                room[i][j] = val;
            }
        }

        // 북0, 동1, 남2, 서3
        final int[][] mapper = {{-1, 0, 1, 0}, {0, 1, 0, -1}};
        boolean[][] isClean = new boolean[N][M];

        int cleanCount = 0;
        Point now = new Point(y, x, d);

        while (true) {
            // 처음 시작과 후진했을경우 청소도 고려해준다.
            if (!isClean[now.y][now.x]) {
                isClean[now.y][now.x] = true;
                cleanCount++;
            }

            // 0 + 4 -> 4
            // 8 7 6 5 -> 0 3 2 1
            // 주변 4칸 확인
            // 반시계로 먼저 한번 회전했다고 가정하고 탐색.
            // 진행 방향 무관하게 주변에서 갈수있는 칸이 있다면 90도 회전을 먼저해야하기 떄문이다.
            boolean canClean = false;

            for (int dir = now.d + 3; dir >= now.d; dir--) {
                int ny = now.y + mapper[0][dir % 4];
                int nx = now.x + mapper[1][dir % 4];

                if (ny < 0 || ny >= N || nx < 0 || nx >= M) continue;
                if (room[ny][nx] == 1 || isClean[ny][nx]) continue;

                now = new Point(ny, nx, dir % 4);
                canClean = true;
                break;
            }

//            printClean(N, M, isClean);

            // 청소가능한 곳이 없는 경우
            if (!canClean) {

                // 바라보는 방향 후진하는 좌표
                int ny = now.y - mapper[0][now.d];
                int nx = now.x - mapper[1][now.d];

                // 후진 불가 시 작동 종료
                if (ny < 0 || ny >= N || nx < 0 || nx >= M ||
                        room[ny][nx] == 1) {
                    break;
                }

                now = new Point(ny, nx, now.d);
            }

            /**
             * 1. 청소되지 않은 빈칸이 없음 -> 주변이 모두 벽이거나 이미 청소함
             * - 바라보는 방향에서 후진 가능하다면 후진
             * - 뒤쪽 칸이 벽이라면 작동 중지
             */

            /**
             * 2. 청소되지 않은 빈칸이 있음 -> 청소 안된 빈칸이 하나이상 존재
             * - 반시계 방향으로 90도 회전
             * - 앞 빈칸이 청소 안됐으면 전진
             */

            // 청소할 곳이 있으면 반시계 방향 우선으로 찾아서 전진한다. -> 바라보는 방향부터 탐색 시작
            // 없다면 바라보는 방향 기준으로 후진한다. 후진 불가시 중지
        }

        System.out.print(cleanCount);
    }

    private static void printClean(int N, int M, boolean[][] isClean) {
        System.out.print("   ");
        for (int i = 0; i < M; i++) {
            System.out.print(i);
        }
        System.out.println();
        for (int i = 0; i < N; i++) {
            if (i == 10) System.out.print(i+" ");
            else System.out.print(i+"  ");
            for (int j = 0; j < M; j++) {
                if (isClean[i][j]) System.out.print("c");
                else System.out.print("n");
            }
            System.out.println();
        }
        System.out.println();
    }

    static class Point {
        int y;
        int x;

        int d;

        public Point(int y, int x, int d) {
            this.y = y;
            this.x = x;
            this.d = d;
        }
    }
}
