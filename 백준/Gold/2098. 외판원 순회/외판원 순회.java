import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static final int INF = 16_000_001;
    public static final int START_CITY = 0;
    public static final int NO_WAY = 0;
    public static int ALL_VISITED;
    private static int[][] adj, dp;
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        ALL_VISITED = (1 << N) - 1;
        adj = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                adj[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[N][ALL_VISITED + 1]; // 4개라면 10000길이로 만들어서 4칸을 만들어야함.
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.print(tour(0, (1 << 0)));
    }

    private static int tour(int now, int bit) {
        if (bit == ALL_VISITED) {
            if (adj[now][START_CITY] == NO_WAY) return INF;
            else return adj[now][START_CITY];
        }

        if (dp[now][bit] != -1) {
            return dp[now][bit];
        }

        dp[now][bit] = INF;
        for (int next = 0; next < N; next++) {
            int toNext = adj[now][next];

            if (toNext == NO_WAY) continue;
            if (((bit & (1 << next)) == 0)) {
//                System.out.println(now+"->"+next+":dist"+dist+", toNext"+ toNext);

                dp[now][bit] = Math.min((tour(next, bit | (1 << next)) + toNext), dp[now][bit]);
            }
        }

        return dp[now][bit];
    }
}
/**
 * 단, 한 번 갔던 도시로는 다시 갈 수 없다. 라는 조건이 존재함을 유의하자.
 *
 * 다익스트라와 다르다. 최단경로가 아닌 모든 경로를 거쳤을때 최소거리이며, 여러번 방문도 불가하다.
 * 간선의 개수가 각 N-1개씩 모두 있다면, N!정도의 전체 탐색을 해야한다.
 */