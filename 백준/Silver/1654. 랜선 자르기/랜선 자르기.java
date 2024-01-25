import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int K = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int[] lens = new int[K];
        long max = 0;
        for (int i = 0; i < K; i++) {
            int len = Integer.parseInt(br.readLine());
            max = Math.max(max, len);
            lens[i] = len;
        }

        // T T T T F F F
        long lo = 0;
        long hi = max + 1;
        while (lo + 1 < hi) {
            long mid = (lo + hi) / 2;

            if (canMake(mid, lens, N)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        System.out.print(lo);
    }

    private static boolean canMake(long cutSize, int[] lens, int needCount) {
        long count = 0;
        for (int len : lens) {
            count += len / cutSize;
        }

        return count >= needCount;
    }
}
