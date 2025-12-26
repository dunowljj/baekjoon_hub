import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N,M, K;
    static int linked = 0;
    static int[] parent, size;
    static Set<Integer> plants;
    static List<Cable> cables = new ArrayList();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        size = new int[N + 1];

        for (int i = 0; i < N + 1; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        st = new StringTokenizer(br.readLine());
        plants = new HashSet<>();
        for (int i = 0; i < K; i++) {
            plants.add(Integer.parseInt(st.nextToken()));
        }

        linked += K; // 발전소가 있는 도시는 홀로 있어도 상관 없음

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            cables.add(new Cable(u, v, w));
        }

        Collections.sort(cables);
        int totalCost = 0;
        for (Cable cable : cables) {
            int u = cable.start;
            int v = cable.end;

            if (union(u, v)) {
                totalCost += cable.cost;
            }

            if (linked == N) break;
        }

        System.out.print(totalCost);
    }

    private static boolean union(int u, int v) {
        int pu = find(u);
        int pv = find(v);

        // 같은 그룹
        if (pu == pv) return false;

        // 양쪽 모두 이미 발전소와 연결됨
        if (plants.contains(pu) && plants.contains(pv)) return false;

        // 한쪽만 발전소와 연결된 경우, 발전소 위치를 부모로 하여 연결
         if (plants.contains(pu)) {
            parent[pv] = pu;
            linked += size[pv];

        } else if (plants.contains(pv)) {
             parent[pu] = pv;
             linked += size[pu];

        // 발전소인 부분 없음
        } else {
            parent[pv] = pu;
            size[pu] += size[pv];
        }

        return true;
    }

    private static int find(int no) {
        if (parent[no] == no) return no;
        else return parent[no] = find(parent[no]);
    }

    static class Cable implements Comparable<Cable> {
        int start;
        int end;
        int cost;

        public Cable(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Cable c) {
            return this.cost - c.cost;
        }
    }
}
/**
 * 이미 발전소와 연결된 경우, 연결 처리하기
 *
 * 크루스칼을 하고, 루트에 발전소 번호를 기록하는건 어떨까?
 *
 * 발전소가 여러개라 최소비용 간선 연결을 하다가 더 좋은 연결을 포기하는 경우가 있나?
 */