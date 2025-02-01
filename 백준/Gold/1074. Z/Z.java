import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int len = ((int) Math.pow(2, N));
        int count = len * len;

        int start = find(r, c, 0, len, count);
        System.out.print(start);
    }

    private static int find(int r, int c, int start, int len, int count) {
        if (len == 1) return start;

        len /= 2;
        count /= 4;

        // 왼위
        if (r < len && c < len)
            return find(r, c, start, len, count);

        // 오른위
        if (r < len && c >= len)
            return find(r, c - len, start + count, len, count);

        // 왼아래
        if (r >= len && c < len)
            return find(r - len, c, start + (count * 2), len, count);

        // 오른아래
        if (r >= len && c >= len)
            return find(r - len, c - len, start + (count * 3), len, count);

        return start;
    }
}