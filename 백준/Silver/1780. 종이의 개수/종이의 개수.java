import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[][] paper;
    static int[] count = {0, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        paper = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        check(N, 0, 0);
        System.out.println(count[0]);
        System.out.println(count[1]);
        System.out.print(count[2]);
    }

    private static void check(int len, int r, int c) {
        int val = paper[r][c];

        if (len == 1) {
            count[val + 1]++;
            return;
        }

        for (int i = r; i < r + len; i++) {
            for (int j = c; j < c + len; j++) {
                if (val != paper[i][j]) {
                    int nlen = len / 3;

                    check(nlen, r, c);
                    check(nlen, r, c + nlen);
                    check(nlen, r, c + (nlen * 2));

                    check(nlen, r + nlen, c);
                    check(nlen, r + nlen, c + nlen);
                    check(nlen, r + nlen, c + (nlen * 2));

                    check(nlen, r + (nlen * 2), c);
                    check(nlen, r + (nlen * 2), c + nlen);
                    check(nlen, r + (nlen * 2), c + (nlen * 2));
                    return;
                }
            }
        }

        count[val + 1]++;
    }
}
