import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int K = Integer.parseInt(st.nextToken()); // 1 ~ 10_000
        int N = Integer.parseInt(st.nextToken()); // 1 ~ 1_000_000

        List<Integer> lines = new ArrayList<>();
        long end = 0;
        for (int i = 0; i < K; i++) {
            int line = Integer.parseInt(br.readLine());
            lines.add(line);
            if (line > end) end = line;
        }

        long start = 0L;
        long mid = 0L;
        end++;

        while (start < end) {
            mid = (start + end) / 2;
            
            if (isCuttable(mid, lines, N)) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }

        System.out.println(end - 1L);
    }

    private static boolean isCuttable(long size, List<Integer> lines, int need) {
        int pieces = 0;
        for (Integer line : lines) {
            pieces += line / size;
        }

        return pieces >= need;
    }
}
