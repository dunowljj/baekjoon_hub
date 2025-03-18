import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int[] seq;
    private static int[][] road;
    private static Integer[][] dp;
    private static int ALL_VISITED, KS_COLLEGE, N, M;
    private static final int INF = 10_000; // 최대 1400

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        seq = new int[N + 1];

        // i번 동네의 하늘 사진은 Pi번 동네의 하늘 사진보다 늦게 찍어야 한다
        // i이전에 Pi가 선행되어야 한다.
        // [Line 2]
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            int P = Integer.parseInt(st.nextToken());
            seq[i] = P;
        }

        // 다 찍기 전에는 경상국립대로 돌아올 수 없다. -> 재방문하면 안됨
        ALL_VISITED = (1 << (N + 1)) - 1; // 1 ~ n+1
        dp = new Integer[N + 2][ALL_VISITED + 1];
        road = new int[N + 2][N + 2];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // 동일한 도로 존재시 최솟값으로 갱신
            if (road[u][v] == 0) road[u][v] = w;
            else road[u][v] = Math.min(road[u][v], w);
        }
        
        KS_COLLEGE = N + 1;
        int result = TSP(KS_COLLEGE, (1 << (KS_COLLEGE - 1)));
        
        if (result == INF) System.out.print(-1);
        else System.out.print(result);
    }

    private static int TSP(int now, int bit) {
        if (bit == ALL_VISITED) {
            if (road[now][KS_COLLEGE] == 0) return INF;
            else return road[now][KS_COLLEGE];
        }

        if (dp[now][bit] != null) {
            return dp[now][bit];
        }

        dp[now][bit] = INF;

        for (int next = 1; next <= N; next++) {
            if (road[now][next] == 0) continue;
            if ((bit & (1 << (next - 1))) != 0) continue;

            //  선행노드 비방문 체크 : 자기 자신이 아니고, 선행노드가 방문되지 않았다면 탐색 불가
            if ((seq[next] != next) && ((bit & (1 << (seq[next] - 1))) == 0)) continue;

            dp[now][bit] = Math.min(
                    TSP(next, bit | (1 << (next - 1))) + road[now][next],
                    dp[now][bit]
            );
        }

        return dp[now][bit];
    }
}
