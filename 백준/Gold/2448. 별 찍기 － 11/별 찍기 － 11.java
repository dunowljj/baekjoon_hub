import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static boolean[][] isStar;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int height = N;
        int wide = 2 * N - 1;
        isStar = new boolean[height][wide];

        // (2 * N - 1) / 2  == N - 1
        checkStars(N, 0, N - 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < isStar.length; i++) {
            for (int j = 0; j < isStar[i].length; j++) {
                if (isStar[i][j]) sb.append("*");
                else sb.append(" ");
            }

            sb.append(System.lineSeparator());
        }

        System.out.print(sb);
    }

    private static void checkStars(int N, int h, int w) {
        if (N == 3) {
            isStar[h][w] = true;

            isStar[h + 1][w + 1] = true;
            isStar[h + 1][w - 1] = true;

            isStar[h + 2][w - 2] = true;
            isStar[h + 2][w - 1] = true;
            isStar[h + 2][w] = true;
            isStar[h + 2][w + 1] = true;
            isStar[h + 2][w + 2] = true;
            return;
        }

        checkStars(N / 2, h + 0, w);
        checkStars(N / 2, h + N / 2, w - ((N - 1) / 2) - 1);
        checkStars(N / 2, h + N / 2, w + ((N - 1) / 2) + 1);
    }
}