/**
 * kruskal
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());

            int houseCount = Integer.parseInt(st.nextToken());
            int roadCount = Integer.parseInt(st.nextToken());

            if (houseCount == 0 && roadCount == 0) break;

            int[] parent = new int[houseCount + 1];
            int[] rank = new int[houseCount + 1];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = i;
            }

            PriorityQueue<Road> pq = new PriorityQueue<>(comparingInt(Road::getLen));
            int lenTotal = 0;
            for (int i = 0; i < roadCount; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());

                lenTotal += z;
                pq.offer(new Road(x, y, z));
            }

            int lenSum = 0;
            while (!pq.isEmpty()) {
                Road now = pq.poll();

                if (houseCount == 0) break;

                if (find(parent, now.start) != find(parent, now.end)) {
                    lenSum += now.len;
                    union(parent, rank, now.start, now.end);
                    houseCount--;
                }
            }

            System.out.println(lenTotal - lenSum);
        }
    }

    private static void union(int[] parent, int[] rank, int start, int end) {
        int p1 = find(parent, start);
        int p2 = find(parent, end);

        if (p1 != p2) {
            if (rank[p1] > rank[p2]) {
                parent[p2] = p1;
            } else if (rank[p1] < rank[p2]) {
                parent[p1] = p2;
            } else {
                rank[p1] ++;
                parent[p2] = p1;
            }
        }
    }

    private static int find(int[] parent, int x) {
        if (parent[x] == x) return x;
        else return parent[x] = find(parent, parent[x]);
    }

    static class Road {
        int start;
        int end;
        int len;

        public Road(int start, int end, int len) {
            this.start = start;
            this.end = end;
            this.len = len;
        }

        public int getLen() {
            return len;
        }
    }
}

/**
 * prim
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());

            int houseCount = Integer.parseInt(st.nextToken());
            int roadCount = Integer.parseInt(st.nextToken());

            if (houseCount == 0 && roadCount == 0) break;

            List<Road>[] city = new ArrayList[houseCount + 1];
            for (int i = 0; i < city.length; i++) {
                city[i] = new ArrayList<>();
            }

            int totalPrice = 0;
            for (int i = 0; i < roadCount; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());

                city[x].add(new Road(y, z));
                city[y].add(new Road(x, z));
                totalPrice += z;
            }

            boolean[] visited = new boolean[houseCount + 1];
            PriorityQueue<Road> pq = new PriorityQueue<>(comparingInt(Road::getLen));
            pq.offer(new Road(1, 0));

            int lenSum = 0;
            while (!pq.isEmpty()) {
                Road now = pq.poll();
                if (visited[now.end]) continue;

                visited[now.end] = true;
                lenSum += now.len;

                for (Road next : city[now.end]) {
                    if (!visited[next.end]) {
                        pq.offer(next);
                    }
                }
            }

            System.out.println(totalPrice - lenSum);
        }
    }

    static class Road {
        int end;
        int len;

        public Road(int end, int len) {
            this.end = end;
            this.len = len;
        }

        public int getLen() {
            return len;
        }
    }
}

/**
 * 도시상의 모든 길의 거리 합은 2^31미터보다 작다.
 */
