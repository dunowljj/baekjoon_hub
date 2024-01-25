import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        long M = Long.parseLong(st.nextToken());

        long[] heights = new long[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            long height = Long.parseLong(st.nextToken());
            heights[i] = height;
        }

        // T T T T F F F
        long lo = 0L;
        long hi = 2_000_000_001L;

        while (lo + 1 < hi) {
            long mid = (lo + hi) / 2L;

            if (satisfied(mid, heights, M)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        System.out.print(lo);
    }

    private static boolean satisfied(long cutHeight, long[] heights, long needHeight) {
        long collected = 0;
        for (long height : heights) {
            collected += Math.max(0, height - cutHeight);
        }

        return needHeight <= collected;
    }

    // 누적합쓰면?
}
