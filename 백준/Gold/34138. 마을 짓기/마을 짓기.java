import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int N,M;
    private static boolean[][] shine;
    private static int[][] diagonal;
    private static int[][] house;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        shine = new boolean[N + 1][M + 1];
        house = new int[N + 1][M + 1];
        diagonal = new int[N + 1][M + 1];
        for (int y = 0; y < N; y++) {
            String line = br.readLine();

            for (int x = 0; x < M; x++) {
                if (line.charAt(x) == 'X') {
                    shine[y + 1][x + 1] = true;
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

        // 연속 대각선 누적
        for (int y = 1; y <= N; y++) {
            for (int x = 1; x <= M; x++) {
                if (shine[y][x]) {
                    diagonal[y][x] = diagonal[y - 1][x - 1] + 1;
                }
            }
        }

//        for (int y = 1; y <= N; y++) {
//            for (int x = 1; x <= M; x++) {
//                System.out.print(diagonalDp[y][x]+ " ");
//            }
//            System.out.println();
//        }

        StringBuilder answer = new StringBuilder();
        int min = Math.min(N, M);

        for (int k = 1; k <= min; k++) {
            int count = 0;
            int houseNeed = k * k - k;

            for (int y = 1; y <= N - k + 1; y++) {
                for (int x = 1; x <= M - k + 1; x++) {
                    if (diagonal[y + k - 1][x + k - 1] < k) continue;

                    int houseSum = house[y + k - 1][x + k - 1] - house[y - 1][x + k - 1] - house[y + k - 1][x - 1] + house[y - 1][x - 1];
                    if (houseNeed == houseSum) count++;
                }
            }

            answer
                    .append(count)
                    .append(System.lineSeparator());
        }

        System.out.print(answer.toString().trim());
    }
}
/**
 * 최대 2000*2000
 * K는 최대 2000!
 *
 * K == 1일때는 X가 영역의 수
 *
 * 집 수는 K만으로도 추측 가능. 누적합으로 집의 수를 미리 세고 비교한다.
 * 연속 대각선 수는 미리 구해놓는다. 매번 k개만큼 검사하여 대각선을 체크하면 시간복잡도 너무 높아진다.
 */
