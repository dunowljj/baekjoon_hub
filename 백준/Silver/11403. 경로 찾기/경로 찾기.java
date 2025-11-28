import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";
    public static final int ONE = 1;
    public static final int ZERO = 0;
    private static int N;
    private static int[][] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        adj = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                adj[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (adj[i][k] == 0 || adj[k][j] == 0) continue;

                    if (adj[i][j] == 0) adj[i][j] = adj[i][k] + adj[k][j];
                    else adj[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (adj[i][j] > 0) sb.append(ONE);
                else sb.append(ZERO);

                sb.append(SPACE);
            }
            sb.append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }
}
