import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int N,M;
    private static boolean[][] shine;
    private static int[][] house;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        shine = new boolean[N + 1][M + 1];
        house = new int[N + 1][M + 1];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                if (line.charAt(j) == 'X') {
                    shine[i + 1][j + 1] = true;
                }
            }
        }

        // 누적합 배열 생성
        for (int y = 1; y <= N; y++) {
            for (int x = 1; x <= M; x++) {
                house[y][x] = house[y][x - 1] + house[y - 1][x] - house[y - 1][x - 1];
                if (!shine[y][x]) house[y][x]++;
            }
        }

        StringBuilder answer = new StringBuilder();
        int min = Math.min(N, M);

        for (int k = 1; k <= min; k++) {
            int count = 0;
            int houseNeed = k * (k - 1);

            for (int y = 1; y <= N - k + 1; y++) {
                for (int x = 1; x <= M - k + 1; x++) {
                    if (!checkDiagonal(y, x, k)) continue;

                    int houseSum = house[y + k - 1][x + k - 1] - house[y - 1][x + k - 1] - house[y + k - 1][x - 1] + house[y - 1][x - 1];
                    if (houseNeed == houseSum) count++;
                }
            }

            answer
                    .append(count)
                    .append(System.lineSeparator());
        }

        System.out.print(answer);
    }

    private static boolean checkDiagonal(int y, int x, int k) {
        for (int i = 0; i < k; i++) {
            if (!shine[y + i][x + i]) return false;
        }

        return true;
    }
}
/**
 * 최대 2000*2000
 * K는 최대 2000! ->
 *
 * 500 * 500을 1500번? 25_000_000 * 15 -> 3억7천?
 * 모든 경우 구하다보면, 브루트포스하면 무조건 연산 10억회는 넘어간다.
 * 집 위치의 누적합을 미리 구해놓고, 대각선만 점검한다면? K*K에서 K개와 나머지 집 수만 검사하면 된다.
 *
 * K == 1일때는 X가 영역의 수
 */
