import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int N;
    private static int r;
    private static int c;
    private static int start;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        int len = ((int) Math.pow(2, N));
        int count = len * len;
        start = 0;

        while (true) {

            if (len == 1) {
                break;
            }

            len /= 2; count /= 4;

            // 왼위
            if (r < len && c < len) {
                // 그대로
                continue;
            }

            // 오른위
            if (r < len && c >= len) {
                start += count;
                c -= len;
                continue;
            }

            // 왼아래
            if (r >= len && c < len) {
                start += count * 2;
                r -= len;
                continue;
            }

            // 오른아래
            if (r >= len && c >= len) {
                start += count * 3;
                r -= len;
                c -= len;
                continue;
            }
        }
        System.out.print(start);
    }
}
/**
 *
 */