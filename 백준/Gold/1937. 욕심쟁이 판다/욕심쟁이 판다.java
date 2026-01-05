import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int[][] forest, move;
    static int n;
    static int maxMove = 0;
    static int[][] mapper = {{0, 0, 1, -1}, {-1, 1, 0, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        forest = new int[n][n];
        move = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                forest[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (move[i][j] == 0) dfs(i, j);
            }
        }

        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(max, move[i][j]);
            }
        }

        System.out.print(max);
    }

    private static int dfs(int y, int x) {
        if (move[y][x] != 0) return move[y][x];

        int max = 1;
        for (int i = 0; i < 4; i++) {
            int ny = y + mapper[0][i];
            int nx = x + mapper[1][i];

            if (ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
            if (forest[y][x] <= forest[ny][nx]) continue;

            max = Math.max(max, dfs(ny, nx) + 1);
        }

        return move[y][x] = max;
    }
}