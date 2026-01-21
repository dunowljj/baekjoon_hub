import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";
    static int[][] arr;
    static int N,M, R;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        arr = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (R-- > 0) {
            rotate(0, 0, N - 1, M - 1);
        }

        StringBuilder sb = new StringBuilder();
        for (int[] row : arr) {
            for (int e : row) {
                sb.append(e).append(SPACE);
            }
            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }

    private static void rotate(int startR, int startC, int endR, int endC) {
        if (endR - startR <= 0 || endC - startC <= 0) return;

        int temp = arr[startR][startC];
        for (int col = startC; col < endC; col++) {
            arr[startR][col] = arr[startR][col + 1];
        }

        for (int row = startR; row < endR; row++) {
            arr[row][endC] = arr[row + 1][endC];
        }

        for (int col = endC; col > startC; col--) {
            arr[endR][col] = arr[endR][col - 1];
        }

        for (int row = endR; row > startR; row--) {
            arr[row][startC] = arr[row - 1][startC];
        }

        arr[startR + 1][startC] = temp;

        rotate(startR + 1, startC + 1, endR - 1, endC - 1);
    }
}
