import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int[] answer = new int[2];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[][] paper = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }

//        System.out.println(isColorUnified(0, 0, 2, paper));
//        System.out.println(isColorUnified(0, 2, 2, paper));
//        System.out.println(isColorUnified(4, 0, 4, paper));

        countSplit(0, 0, N, paper);

        System.out.println(answer[0]);
        System.out.print(answer[1]);
    }

    private static void countSplit(int startY, int startX, int N, int[][] paper) {
        if (N == 1) {
            int color = paper[startY][startX];
            answer[color]++;
            return;
        }

        if (isColorUnified(startY, startX, N, paper)) {
            int color = paper[startY][startX];
            answer[color]++;
            return;
        }

        countSplit(startY, startX, N/2, paper);
        countSplit(startY + N/2, startX, N/2, paper);
        countSplit(startY, startX + N/2, N/2, paper);
        countSplit(startY + N/2, startX + N/2, N/2, paper);
    }

    private static boolean isColorUnified(int startY, int startX, int len, int[][] paper) {
        int color = paper[startY][startX];

        for (int i = startY; i < startY + len; i++) {
            for (int j = startX; j < startX + len; j++) {
                if (color != paper[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}

/**
 * 전체 크기 (2^7)^2 -> 2^14
 *
 * 2^14개를 2^7번 탐색 -> 2^21
 *
 *
 * - 각 지점의 왼쪽 맨 위 기준으로 위치 찾기
 */