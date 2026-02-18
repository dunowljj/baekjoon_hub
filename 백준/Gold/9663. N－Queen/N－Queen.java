import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static final int NON = -100;
    static int count = 0;
    static int N;

    static boolean[] col;
    static boolean[] dia1;
    static boolean[] dia2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        col = new boolean[N];
        dia1 = new boolean[2 * N];
        dia2 = new boolean[2 * N];

        find(0);
        System.out.print(count);
    }

    private static void find(int r) {
        if (r == N) {
            count++;
            return;
        }

        for (int c = 0; c < N; c++) {
            if (!col[c] && !dia1[r + c] && !dia2[r - c + N - 1]) {

                col[c] = true;
                dia1[r + c] = true;
                dia2[r - c + N - 1] = true;

                find(r + 1);

                col[c] = false;
                dia1[r + c] = false;
                dia2[r - c + N - 1] = false;
            }
        }
    }
}