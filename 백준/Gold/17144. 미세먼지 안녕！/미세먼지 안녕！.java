import java.io.BufferedReader;import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int CLEANER = -1;
    static int R,C,T;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        int topR = -1;
        int downR = -1;
        int[][] room = new int[R][C];
        for (int r = 0; r < R; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < C; c++) {
                int amount = Integer.parseInt(st.nextToken());

                if (amount == CLEANER) {
                    if (topR == -1) topR = r;
                    else downR = r;
                }

                room[r][c] = amount;
            }
        }

//        System.out.println("topR = " + topR);
//        System.out.println("downR = " + downR);

        while (T-- > 0) {
            spread(room);
//            printStatus(room);
            cleanerWork(room, topR, downR);
//            printStatus(room);
        }

        int sum = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (room[r][c] <= 0) continue;
                sum += room[r][c];
            }
        }

        System.out.print(sum);
    }

    private static void printStatus(int[][] room) {
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                System.out.print(room[r][c]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static final int[][] mapper = {{0, 0, -1, 1}, {1, -1, 0, 0}};

    private static void spread(int[][] room) {
        int[][] temp = new int[R][C];
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                // 먼지가 없거나 공기청정기라면 무시
                if (room[r][c] <= 0) continue;

                int spreadAmount = room[r][c] / 5;
                for (int dir = 0; dir < 4; dir++) {
                    int nr = r + mapper[0][dir];
                    int nc = c + mapper[1][dir];

                    if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                    if (room[nr][nc] == CLEANER) continue;

                    temp[nr][nc] += spreadAmount;
                    room[r][c] -= spreadAmount;
                }
            }
        }

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                room[r][c] += temp[r][c];
            }
        }
    }
    private static void cleanerWork(int[][] room, int topR, int downR) {
        // 1. 상단 반시계 역순으로 갱신

        // 맨 왼쪽열의 위 -> 아래로 한칸씩 밀기
        for (int r = topR - 1; r > 0; r--) {
            room[r][0] = room[r - 1][0];
        }

        // 맨 위 행의, 왼쪽 <- 오른쪽으로 한칸씩 밀기
        for (int c = 0; c < C - 1; c++) {
            room[0][c] = room[0][c + 1];
        }

        // 맨 오른쪽열의, 아래 -> 위로 한칸씩 밀기
        for (int r = 0; r < topR; r++) {
            room[r][C - 1] = room[r + 1][C - 1];
        }

        // top과 같은 행의, 왼쪽 -> 오른쪽으로 한칸씩 밀기
        for (int c = C - 1; c > 1; c--) {
            room[topR][c] = room[topR][c - 1];
        }
        room[topR][1] = 0;

        // 2. 하단 시계방향을 역순으로 갱신
        // 맨 왼쪽열의, 아래 -> 위 한칸 밀기
        for (int r = downR + 1; r < R - 1; r++) {
            room[r][0] = room[r + 1][0];
        }

        // 맨 아래행, 왼쪽 <- 오른쪽 한칸 밀기
        for (int c = 0; c < C - 1; c++) {
            room[R - 1][c] = room[R - 1][c + 1];
        }

        // 맨 오른열, 위쪽 -> 아래쪽 한칸 밀기
        for (int r = R - 1; r > downR; r--) {
            room[r][C - 1] = room[r - 1][C - 1];
        }

        // down행 왼쪽 -> 오른쪽 한칸 밀기 : 오른쪽부터 채우면서 한칸밀기
        for (int c = C - 1; c > 1; c--) {
            room[downR][c] = room[downR][c - 1];
        }
        room[downR][1] = 0;
    }
}
/**
 * 1. 미세먼지 있는 모든 칸 확산
 * - 인접 4방향
 * - 인접한 곳에 공기청정기 있거나, 칸이 없으면 확산 x
 * - 확산량 Arc/5
 *
 * 2. 공기청정기 동작
 * - 바람이 나옴
 * - 위-반시계, 아래-시계
 * - 먼지가 한칸씩 밀려남
 * - 공기청정기에 들어가면 미세먼지 제거
 *
 * 공기청정기는 항상 1열에 있으며, 맨 위나 맨 아래에 붙어있는 경우는 없다.
 *
 *
 * 2500개 먼지가 있을때, 1000초 -> 2_500_000
 * 공기청정기 순환 -> 한쪽에 200 미만
 */