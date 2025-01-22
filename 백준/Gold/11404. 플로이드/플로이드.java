import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        long[][] minDist = new long[n + 1][n + 1];
        for (int i = 0; i < minDist.length; i++) {
            Arrays.fill(minDist[i], Integer.MAX_VALUE);
            minDist[i][i] = 0; // 자기 자신
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 노선 여러개인 경우 최소 길이로 갱신
            minDist[a][b] = Math.min(minDist[a][b], c);
        }

        for (int mid = 1; mid <= n; mid++) {
            for (int start = 1; start <= n; start++) {
                for (int end = 1; end <= n; end++) {
                    if (minDist[start][end] > minDist[start][mid] + minDist[mid][end]) {
                        minDist[start][end] = minDist[start][mid] + minDist[mid][end];
                    }
                }
            }
        }

        // Integer의 최댓값 == 갱신되지 않음 -> 갈 수 없는 곳
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (minDist[i][j] == Integer.MAX_VALUE) sb.append(0).append(SPACE);
                else sb.append(minDist[i][j]).append(SPACE);
            }
            sb.append(System.lineSeparator());
        }

        System.out.print(sb);
    }
}