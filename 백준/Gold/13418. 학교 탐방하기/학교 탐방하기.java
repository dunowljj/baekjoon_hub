import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N,M;
    static int best = 0, worst = 0, connected = 0;
    static int[] parent;
    static List<Road> roads;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        roads = new ArrayList<>();

        for (int i = 0; i < M + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            roads.add(new Road(A, B, C == 0));
        }

        // init
        connected = 0;
        parent = new int[N + 1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        // 내리막 우선 정렬
        Collections.sort(roads, (r1, r2) -> Boolean.compare(r1.isUphill, r2.isUphill));

//        for (Road road : roads) {
//            System.out.println("road.start = " + road.start);
//            System.out.println("road.end = " + road.end);
//            System.out.println(road.isUphill);
//            System.out.println();
//        }

        for (int i = 0; i < roads.size(); i++) {
            Road road = roads.get(i);

            if (union(road)) {
                connected++;
                if (road.isUphill) best++;
            }
            if (connected == N) break;
        }

        // init
        connected = 0;
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int i = roads.size() - 1; i >= 0; i--) {
            Road road = roads.get(i);

            if (union(road)) {
                connected++;
                if (road.isUphill) worst++;
            }
            if (connected == N) break;
        }

        System.out.print((int) (Math.pow(worst, 2) - Math.pow(best, 2)));
    }

    public static int find(int no) {
        if (parent[no] == no) return no;
        else return parent[no] = find(parent[no]);
    }

    public static boolean union(Road road) {
        int ps = find(road.start);
        int pe = find(road.end);

        if (ps != pe) {
            parent[ps] = pe;
            return true;
        }

        return false;
    }

    static class Road implements Comparable<Road> {
        int start;
        int end;
        boolean isUphill;

        public Road(int start, int end, boolean isUphill) {
            this.start = start;
            this.end = end;
            this.isUphill = isUphill;
        }

        @Override
        public int compareTo(Road road) {
            if (this.isUphill && !road.isUphill) return 1;
            else return 0;
        }
    }
}
/**
 * 내리막 우선, 오르막 우선을 정한다.
 *
 * 내리막길을 먼저 탔다면, 다시 올라올때 오르막길로 고려하지 않음.
 * 즉, 그냥 mst문제
 */