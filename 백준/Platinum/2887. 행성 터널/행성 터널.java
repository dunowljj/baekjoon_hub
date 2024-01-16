import com.sun.management.GarbageCollectionNotificationInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.GarbageCollectorMXBean;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        List<Planet> planets = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            planets.add(new Planet(i, x, y, z));
        }

        int[] rank = new int[N];
        int[] parent = new int[N];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        long minCost = 0;

        PriorityQueue<Tunnel> tunnels = new PriorityQueue<>(comparingInt(Tunnel::getDistance));

        Collections.sort(planets, comparingInt(p -> p.x));
        for (int i = 0; i < N - 1; i++) {
            Planet start = planets.get(i);
            Planet end = planets.get(i + 1);

            tunnels.offer(new Tunnel(start, end));
        }

        Collections.sort(planets, comparingInt(p -> p.y));
        for (int i = 0; i < N - 1; i++) {
            Planet start = planets.get(i);
            Planet end = planets.get(i + 1);

            tunnels.offer(new Tunnel(start, end));
        }

        Collections.sort(planets, comparingInt(p -> p.z));
        for (int i = 0; i < N - 1; i++) {
            Planet start = planets.get(i);
            Planet end = planets.get(i + 1);

            tunnels.offer(new Tunnel(start, end));
        }

        while (!tunnels.isEmpty()) {
            Tunnel poll = tunnels.poll();

            if (find(parent, poll.start) == find(parent, poll.end)) continue;

            union(parent, rank, poll.start, poll.end);
            minCost += poll.distance;
        }

        System.out.print(minCost);
    }

    private static int find(int[] parent, int no) {
        if (parent[no] == no) return no;
        return parent[no] = find(parent, parent[no]);
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
                rank[p1]++;
                parent[p2] = p1;
            }
        }
    }

    static class Planet {
        int no;
        int x;
        int y;
        int z;

        public Planet(int no, int x, int y, int z) {
            this.no = no;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int distanceTo(Planet planet) {
            int minX = Math.abs(this.x - planet.x);
            int minY = Math.abs(this.y - planet.y);
            int minZ = Math.abs(this.z - planet.z);

            return Math.min(minX, Math.min(minY, minZ));
        }
    }

    static class Tunnel {
        int start;
        int end;
        int distance;

        public Tunnel(Planet start, Planet end) {
            this.start = start.no;
            this.end = end.no;
            this.distance = start.distanceTo(end);
        }

        public int getDistance() {
            return distance;
        }
    }
}
