import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static int N;
    private static char[][] video;

    private static final StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        video = new char[N][N];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                video[i][j] = line.charAt(j);
            }
        }

        compress(0, 0, N);
        System.out.print(answer);
    }

    private static void compress(int y, int x, int len) {
        int half = len / 2;

        if (canCompress(y, x, len)) {
            answer.append(video[y][x]);
            return;
        }

        answer.append("(");

        compress(y, x, half);
        compress(y, x + half, half);
        compress(y + half, x, half);
        compress(y + half, x + half, half);

        answer.append(")");
    }

    private static boolean canCompress(int y, int x, int len) {
        char start = video[y][x];

        for (int i = y; i < y + len; i++) {
            for (int j = x; j < x + len; j++) {
                if (video[i][j] != start) {
                    return false;
                }
            }
        }

        return true;
    }
}
