import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int connectedCount = 0;

    static int[] parent;
    static int[] cost;

    static boolean[] connectWithRight; // 오른쪽과 연결

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        long K = Long.parseLong(st.nextToken());

        parent = new int[N + 1];
        for (int i = 1; i < parent.length; i++) {
            parent[i] = i;
        }

        cost = new int[N + 1];

        connectWithRight = new boolean[N + 1];
        Arrays.fill(connectWithRight, true);

        // 와우도와 연결 끊어놓기
        connectWithRight[0] = false;

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }

        // i,j 이웃한 경우만 주어짐
        for (int l = 0; l < M; l++) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken());
            int j = Integer.parseInt(st.nextToken());

            int big = Math.max(i, j);
            int small = Math.min(i, j);

            if (big == N && small == 1) {
                connectWithRight[big] = false;
            } else {
                connectWithRight[small] = false;
            }
        }

        // 공사 구간이 한개이면, 각 강의동은 N-1개 연결이 있고, 모두 연결되어 있다.
        if (M <= 1) {
            System.out.print("YES");
            return;
        }

        // 공사 구간이 한개가 넘는다면, 와우도를 사용해야하고, 총 N개의 연결이 필요하다.

        // 공사안된 부분만 미리 연결
        for (int i = 1; i <= N; i++) {
            if (connectWithRight[i]) {
                if (i == N) {
                    if (union(N, 1)) connectedCount++;
                } else {
                    if (union(i, i + 1)) connectedCount++;
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            int pi = find(i);
            cost[pi] = Math.min(cost[pi], cost[i]);
        }

        long total = 0;
        for (int i = 0; i <= N; i++) {
            if (find(i) == i) {
                total += cost[i];
            }
        }

        if (K < total) System.out.print("NO");
        else System.out.print("YES");
    }

    private static boolean union(int i, int j) {
        int pi = find(i);
        int pj = find(j);

        if (pi != pj) {
            parent[pi] = pj;
            return true;
        }

        return false;
    }

    private static int find(int i) {
        if (parent[i] == i) return i;
        else return parent[i] = find(parent[i]);
    }
}

/**
 * 풀이
 * 1) 크루스칼 기반 와우도를 0번으로 두고 mst 만들기
 * 2) 공사중이 아닌 구간은 이미 연결됨 처리를 해야한다.
 * - 공사중이 아닌 구간을 어떻게 쉽게 체크할까?
 * - right boolean 배열을 만들어서 연결여부를 체크.
 * - 해당 배열에 기반해 parent 배열 갱신하여 미리 연결해두기
 * 3) 와우도에서 다른 강의동까지 간선을 크루스칼로 마저 연결 시도
 */