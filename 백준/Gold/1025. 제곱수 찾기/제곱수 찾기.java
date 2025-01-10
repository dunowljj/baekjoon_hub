import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int X_MAX_DIFF;
    private static int Y_MAX_DIFF;
    private static final int MIN_DIFF = 0;

    private static int answer = -1; // 완전 제곱수 없는 경우 고려
    private static int N;
    private static int M;

    private static int[][] grid;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Y_MAX_DIFF = N - 1;
        X_MAX_DIFF = M - 1;

        grid = new int[N][M];
        for (int y = 0; y < N; y++) {
            String row = br.readLine();
            for (int x = 0; x < M; x++) {
                grid[y][x] = row.charAt(x) - '0';
            }
        }

        findDiffComb(MIN_DIFF, MIN_DIFF);

        System.out.print(answer);
    }

    private static void findDiffComb(int y_diff, int x_diff) {
        calculate(y_diff, x_diff);


        if (y_diff == Y_MAX_DIFF && x_diff == X_MAX_DIFF) {
            return;
        }

        if (x_diff != X_MAX_DIFF) {
            findDiffComb(y_diff, x_diff + 1);
        } else {
            findDiffComb(y_diff + 1, MIN_DIFF);
        }
    }

    private static void calculate(int y_diff, int x_diff) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                calculate("" + grid[y][x], y, x, y_diff, x_diff);
                calculate("" + grid[y][x], y, x, -y_diff, x_diff);
                calculate("" + grid[y][x], y, x, y_diff, -x_diff);
                calculate("" + grid[y][x], y, x, -y_diff, -x_diff);
            }
        }
    }

    private static void calculate(String result, int y, int x, int y_diff, int x_diff) {
        checkIsCompletePow(result);

        if (y_diff == 0 && x_diff == 0) return; // 등차가 0이면 제자리에 머물기때문에 체크만 하고 종

        int ny = y + y_diff;
        int nx = x + x_diff;

        if (0 <= ny && ny < N && 0 <= nx && nx < M) {
            calculate(result + grid[ny][nx], ny, nx, y_diff, x_diff);
        }
    }

    private static void checkIsCompletePow(String result) {
        int num = Integer.parseInt(result);

        double sqrt = Math.sqrt(num);

        if (sqrt == (int) sqrt) {
            answer = Math.max(answer, num);
        }
    }
}
/**
 * 여러개 수를 선택하기. 단,
 * - 행,열이 등차수열을 각각 이뤄야한다.
 * - 결과가 완전 제곱수여야 한다.
 *
 * N,M은 최대 1~9
 *
 * N의 공차가 0~8인경우 * M의 공차가 0~8인경우 -> 81가지 - 1가지(0,0)
 * 선택하는 칸의 개수에 따른 여러 조합 존재. ex) 공차가 1이라면, 1칸, 3칸,5칸,7칸까지 선택 가능
 * 어짜피 양수만 존재하기때문에 최대한 많은 수를 선택해야한다고 오해했지만 완전제곱수인 경우를 골라야하니, 모든 경우를 선택해야한다.
 *
 *
 * 주의!
 * - 예시를 보니 등차가 0인 경우도 포함
 * - 역순 탐색도 가능
 *
 *
 * 1) 공차의 크기에 따른 모든 조합 생성
 * 2) 각 조합에 대한 모든 선택 경우 조합
 * 3) 모든 선택 경우 결과로 가장 큰 완전제곱수 값 갱신
 *
 * 0도 완전제곱수인데, 만들수 없는 경우는 무엇인가? -> 0이 없고, 어떤 조합으로도 완전제곱수가 안됨
 * -> -1을 정답의 초기값으로 해야함
 * 1개 이상 선택이므로 1개를 선택하는 경우도 완전제곱수이다.
 */