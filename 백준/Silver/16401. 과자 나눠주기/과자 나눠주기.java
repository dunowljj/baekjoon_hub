import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int M = Integer.parseInt(st.nextToken());
        int cookieCount = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] lens = new int[cookieCount];
        int max = 0;
        for (int i = 0; i < cookieCount; i++) {
            int len = Integer.parseInt(st.nextToken());
            lens[i] = len;
            max = Math.max(max, len);
        }

        // T T T T F F F
        // T T T m
        // 0 F F F
        long lo = 1;
        long hi = max + 1;

        while (lo + 1 < hi) {
            long mid = (lo + hi) / 2;

            if (M <= getSplitCount(mid, lens)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        int splitCount = getSplitCount(lo, lens);
        System.out.print(lo * (splitCount / M));
    }

    private static int getSplitCount(long cutLen, int[] lens) {
        int count = 0;
        for (int len : lens) {
            count += len / cutLen;
        }

        return count;
    }
}
