import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int[] parent;
    static List<House>[] adj;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        List<Bridge> bridges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int h1 = Integer.parseInt(st.nextToken());
            int h2 = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            bridges.add(new Bridge(h1, h2, k));
        }

        Collections.sort(bridges);

        adj = new ArrayList[N + 1];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }


        for (int i = 0; i < bridges.size(); i++) {
            Bridge bridge = bridges.get(i);

            if (union(bridge) && find(s) == find(e)) {
                System.out.print(bridge.weightLimit);
                return;
            }
        }

        System.out.print(0);
    }


    private static boolean union(Bridge bridge) {
        int p1 = find(bridge.h1);
        int p2 = find(bridge.h2);

        if (p1 != p2) {
            parent[p1] = p2;
            return true;
        }

        return false;
    }

    private static int find(int h) {
        if (parent[h] == h) return h;
        else return parent[h] = find(parent[h]);
    }


    static class Bridge implements Comparable<Bridge> {
        int h1;
        int h2;
        int weightLimit;

        public Bridge(int h1, int h2, int weightLimit) {
            this.h1 = h1;
            this.h2 = h2;
            this.weightLimit = weightLimit;
        }


        @Override
        public int compareTo(Bridge o) {
            return o.weightLimit - this.weightLimit ;
        }
    }

    static class House {
        int no;
        int weightLimit;

        public House(int no, int weightLimit) {
            this.no = no;
            this.weightLimit = weightLimit;
        }
    }

}
/**
  *
 * 이벤트 장소까지 돌아가더라도, 가장 많이 들고 갈 수 있는 다리를 이용해야함.
 * 무조건 큰 위치부터 가면 안된다. 결과적으로 통과한 모든 다리 중 최솟값이 가장 커야한다.
 *
 * 해결 : 최대스패닝 트리를 구하고, bfs를 한다.
 * 변경 -> 스패닝트리를 만들고, 추가 탐색을 할 필요가 없다. s와 e가 결합되는 순간을 보면 됨.
 * 최단경로 알고리즘을 사용하는 것은 시간복잡도가 어떻지?
 */