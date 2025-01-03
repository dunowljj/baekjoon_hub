import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    int[] dx = {-1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[][] max = new int[N][3];
        int[][] min = new int[N][3];

        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());

            for (int x = 0; x < 3; x++) {
                int n = Integer.parseInt(st.nextToken());

                max[y][x] = n;
                min[y][x] = n;
            }
        }

        for (int y = 1; y < N; y++) {
            max[y][0] += Math.max(max[y - 1][0], max[y - 1][1]);
            max[y][1] += Math.max(max[y - 1][0], Math.max(max[y - 1][1], max[y - 1][2]));
            max[y][2] += Math.max(max[y - 1][1], max[y - 1][2]);

            min[y][0] += Math.min(min[y - 1][0], min[y - 1][1]);
            min[y][1] += Math.min(min[y - 1][0], Math.min(min[y - 1][1], min[y - 1][2]));
            min[y][2] += Math.min(min[y - 1][1], min[y - 1][2]);
        }

        int maxScore = Math.max(max[N - 1][0], Math.max(max[N - 1][1], max[N - 1][2]));
        int minScore = Math.min(min[N - 1][0], Math.min(min[N - 1][1], min[N - 1][2]));

        System.out.print(maxScore + " "+minScore);
    }
}