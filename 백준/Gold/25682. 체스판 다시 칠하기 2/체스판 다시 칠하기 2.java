import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int BLACK = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 0행, 0열은 구간합을 사용할때 편의를 위한 공집합
        // 색깔 체크는 1~N,M까지에 이뤄진다.
        int[][] board = new int[N + 1][M + 1];
        int[][] black = new int[N + 1][M + 1]; // 시작이 black인 board
        int[][] white = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            String row = br.readLine();

            for (int j = 1; j <= M; j++) {
                if (row.charAt(j - 1) == 'B') {
                    board[i][j] = BLACK;
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                // (첫칸부터) 홀수칸
                if (i % 2 == 1) {

                    if (j % 2 == 1) {
                        // 홀수칸이 black이라면, white가 시작인 체스판에는 색을 칠해야하는 부분이므로 체크한다!
                        if (board[i][j] == BLACK) white[i][j] = 1;
                        else black[i][j] = 1;
                    }

                    if (j % 2 == 0) {
                        if (board[i][j] == BLACK) black[i][j] = 1;
                        else white[i][j] = 1;
                    }
                }

                // (둘째칸부터) 짝수칸
                if (i % 2 == 0) {
                    if (j % 2 == 1) {
                        if (board[i][j] == BLACK) black[i][j] = 1;
                        else white[i][j] = 1;
                    }

                    if (j % 2 == 0) {
                        if (board[i][j] == BLACK) white[i][j] = 1;
                        else black[i][j] = 1;
                    }
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                black[i][j] += black[i - 1][j] + black[i][j - 1] - black[i - 1][j - 1];
                white[i][j] += white[i - 1][j] + white[i][j - 1] - white[i - 1][j - 1];
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int i = K; i <= N; i++) {
            for (int j = K; j <= M; j++) {
                int blackSum = black[i][j] - black[i - K][j] - black[i][j - K] + black[i - K][j - K];
                int whiteSum = white[i][j] - white[i - K][j] - white[i][j - K] + white[i - K][j - K];
                answer = Math.min(answer, Math.min(blackSum, whiteSum));
            }
        }

        System.out.print(answer);
    }
}
/**
 * M*N 보드
 *
 * 맨 왼쪽 위가 검은색인 경우, 흰색인 경우에 대해 일치하는지에 대한 배열 만들기
 * 일치하면 1, 아니면 0
 * K 크기의 구간합?
 *
 * (N - K + 1)*(M - K + 1)개의 K*K 사각형을 검사해야함.
 * 5*5, 3*3이라면 9개의 3*3을 검사해야.
 * 2000*2000, 100*100이라면? -> 300만개가 넘는 100*100을 탐색해야한다.
 *
 * 구간합을 구해서 각 사각형을 4번만 검사하면 되도록 하자.
 * 최대 개수 -> 2000*2000 2*2 400만개의 4칸 탐색!
 */