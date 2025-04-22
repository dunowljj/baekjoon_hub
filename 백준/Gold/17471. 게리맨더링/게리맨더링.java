import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static int N;
    private static List<Integer>[] adj;

    private static int aAdjCount = 0;
    private static int bAdjCount = 0;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        // 인구수 초기화
        int[] population = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            population[i] = Integer.parseInt(st.nextToken());
        }

        // 인접리스트 초기화
        adj = new ArrayList[N + 1];
        for (int i = 1; i < adj.length; i++) adj[i] = new ArrayList<>();

        for (int no = 1; no <= N; no++) {
            st = new StringTokenizer(br.readLine());
            int adjCount = Integer.parseInt(st.nextToken());

            for (int i = 0; i < adjCount; i++) {
                int adjNo = Integer.parseInt(st.nextToken());
                adj[no].add(adjNo);
            }
        }

        // 모든 경우의 조합에 대해 가능 여부 검증, 0과 모두 방문은 제외
        int minDiff = Integer.MAX_VALUE;
        for (int bit = 1; bit < ((1 << N) - 1); bit++) {
            if (isPossible(bit)) {
                minDiff = Math.min(minDiff, calcDiff(bit, population));
            }
        }

        if (minDiff == Integer.MAX_VALUE) System.out.print(-1);
        else System.out.print(minDiff);
    }

    private static int calcDiff(int bit, int[] population) {
        int aPop = 0;
        int bPop = 0;
        for (int no = 1; no < population.length; no++) {
            if ((bit & (1 << (no - 1))) == 0) aPop += population[no];
            else bPop += population[no];
        }

        return Math.abs(aPop - bPop);
    }

    private static boolean isPossible(int bit) {
        int aAreaCount = 0;
        int bAreaCount = 0;

        int aNo = 0;
        int bNo = 0;
        for (int no = 1; no <= N; no++) {
            if ((bit & (1 << (no - 1))) == 0) {
                aNo = no;
                aAreaCount++;
            }
            else {
                bNo = no;
                bAreaCount++;
            }
        }

        boolean[] aVisited = new boolean[N + 1];
        boolean[] bVisited = new boolean[N + 1];
        aAdjCount = 1;
        bAdjCount = 1;
        countAdj(aNo, true, bit, aVisited);
        countAdj(bNo, false, bit, bVisited);

        if (aAdjCount != aAreaCount) return false;
        if (bAdjCount != bAreaCount) return false;

        return true;
    }

    private static void countAdj(int no, boolean isA, int bit, boolean[] visited) {
        visited[no] = true;

        if (isA) {
            for (int next : adj[no]) {
                if ((bit & (1 << (next - 1))) == 0 && !visited[next]) {
                    visited[next] = true;
                    aAdjCount++;
                    countAdj(next, isA, bit, visited);
                }
            }

        } else {
            for (int next : adj[no]) {
                if ((bit & (1 << (next - 1))) != 0 && !visited[next]) {
                    visited[next] = true;
                    bAdjCount++;
                    countAdj(next, isA, bit, visited);
                }
            }
        }
    }
}
/**
 * 두 선거구로 나눔
 * 각 구역은 두 선거구 중 하나에 포함되어야 함.
 *
 * 선거구는 적어도 하나 구역 포함. 한 선거구에 포함되어 있는 구역은 모두 연결되어야함.
 *
 * 인구 차이를 최소화하면서 선거구를 두개로 나눠야한다.
 *
 * 선거구 2~10개
 *
 *
 * ### 풀이
 * 비트로 나타내면 2^10가지 선거구 나눔 방식이 존재.
 * 1024개의 모든 조합을 구한다음, 불가능한 경우를 모두 제외하기.
 *
 *
 */