import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static final int ONE = 1;
    private static final int ZERO = 0;
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {

        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            grade();
        }

        System.out.print(sb);
    }

    private static void grade() throws IOException {
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] notebook1 = new int[N];
        for (int i = 0; i < N; i++) {
            notebook1[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(notebook1);

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < M; i++) {
            int num = Integer.parseInt(st.nextToken());

            if (isExist(notebook1, num)) sb.append(ONE).append(System.lineSeparator());
            else sb.append(ZERO).append(System.lineSeparator());
        }
    }

    private static boolean isExist(int[] notebook1, int num) {
        int lo = 0;
        int hi = notebook1.length - 1;

        // F F T T T T
        // T T T
        // F F F
        while (lo < hi) {
            int mid = (lo + hi) / 2;

            if (notebook1[mid] >= num) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        if (lo == notebook1.length) return false;
        return notebook1[lo] == num;
    }
}
