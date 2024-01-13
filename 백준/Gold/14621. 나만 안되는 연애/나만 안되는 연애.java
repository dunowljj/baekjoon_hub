import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Set<Integer> connected = new HashSet<>();

        int schoolCount = Integer.parseInt(st.nextToken());
        int roadCount = Integer.parseInt(st.nextToken());

        String[] schoolTypes = br.readLine().split(" ");

//        List<Integer>[] adjList = new ArrayList[schoolCount + 1];
//        for (int i = 0; i < adjList.length; i++) {
//            adjList[i] = new ArrayList<>();
//        }

        int[] parent = new int[schoolCount + 1];
        for (int i = 0; i <= schoolCount; i++) {
            parent[i] = i;
        }

        // 모든 간선 초기화
        PriorityQueue<Edge> pq = new PriorityQueue<>(comparingInt(Edge::getDistance));
        for (int i = 0; i < roadCount; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            // 같은 계열 학교면 연결하지 않는다.
            if (schoolTypes[u - 1].equals(schoolTypes[v - 1])) continue;
            
            pq.offer(new Edge(u, v, d));
        }

        int total = 0;
        while (!pq.isEmpty()) {
            Edge now = pq.poll();

            if (AlreadyConnected(parent, now)) continue;

            connected.add(now.start);
            connected.add(now.end);

            union(parent, now.start, now.end);
            total += now.distance;
        }

        if (connected.size() != schoolCount) {
            System.out.print("-1");
        } else  {
            System.out.print(total);
        }
    }

    private static boolean AlreadyConnected(int[] parent, Edge now) {
        return findParent(parent, now.start) == findParent(parent, now.end);
    }

    public static int findParent(int[] parent, int no) {
        if (parent[no] == no) return no;
        else return findParent(parent, parent[no]);
    }

    public static void union(int[] parent, int no1, int no2) {
        int p1 = findParent(parent, no1);
        int p2 = findParent(parent, no2);

        if (p1 < p2) {
            parent[p2] = p1;
        } else {
            parent[p1] = p2;
        }
    }

    static class Edge {
        int start;
        int end;
        int distance;

        public Edge(int start, int end, int distance) {
            this.start = start;
            this.end = end;
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }
    }
}
