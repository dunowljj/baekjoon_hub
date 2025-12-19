import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static List<Integer>[] relationShip;
    static List<Friends> groups = new ArrayList<>();
    static int[] candyCounts;
    static boolean[] visited;

    static int sum = 0, numOfChildren = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        relationShip = new ArrayList[N + 1];
        for (int i = 0; i < relationShip.length; i++) {
            relationShip[i] = new ArrayList<>();
        }
        visited = new boolean[N + 1];

        st = new StringTokenizer(br.readLine());

        candyCounts = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            candyCounts[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            relationShip[a].add(b);
            relationShip[b].add(a);
        }

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                sum = 0;
                numOfChildren = 0;
                findRelation(i);

                groups.add(new Friends(numOfChildren, sum));
            }
        }

        Integer[] dp = new Integer[K];
        dp[0] = 0;
        for (Friends group : groups) {
            List<int[]> temp = new ArrayList<>();
            for (int i = 0; i < K; i++) {
                if (dp[i] != null && i + group.numOfChildren < K) {
                    temp.add(new int[]{i + group.numOfChildren, dp[i] + group.totalCandy});
                }
            }

            for (int[] ints : temp) {
                if (dp[ints[0]] == null) dp[ints[0]] = ints[1];
                else dp[ints[0]] = Math.max(dp[ints[0]], ints[1]);
            }
        }

        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] == null) continue;

            max = Math.max(dp[i], max);
        }

        System.out.print(max);
    }

    private static void findRelation(int now) {
        numOfChildren++;
        sum += candyCounts[now];

        for (int friend : relationShip[now]) {
            if (visited[friend]) continue;
            visited[friend] = true;

            findRelation(friend);
        }
    }


    static class Friends {
        int numOfChildren;
        int totalCandy;

        public Friends(int numOfChildren, int totalCandy) {
            this.numOfChildren = numOfChildren;
            this.totalCandy = totalCandy;
        }
    }
}
/**
 * 친구들을 같은 숫자만큼 모두 빼앗아야함 -> 가장 적은 친구의 양으로 빼앗아야 함
 * K이상 안됨
 *
 * 1) 각 그룹의 모든 사탕 수와, 인원 수 알아내기
 * 2)
 *
 *
 * 인원당 사탕 수가 많은 그룹을 고르는게 이득이지만,
 * 그렇지 않은 그룹이 최대인 경우도 있다.
 * ex)
 * K = 6
 * 24/5
 * 10/2
 * 10/2
 * 10/2
 * --> 24/5만 고르는게 최선
 *
 * 그룹핑을 하고, dp를 해야할듯
 *
 * dp[K]
 */