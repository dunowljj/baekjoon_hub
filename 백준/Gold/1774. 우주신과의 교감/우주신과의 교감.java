import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import static java.util.Comparator.comparingDouble;

public class Main {

    public static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Coordinate[] coordinates = new Coordinate[N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int X = Integer.parseInt(st.nextToken());
            int Y = Integer.parseInt(st.nextToken());

            coordinates[i] = new Coordinate(i, X, Y);
        }

        int[] parent = new int[N + 1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            boolean executed = union(parent, a, b);
            if (executed) {
                count++;
            }
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>(comparingDouble(Edge::getDistance));
        for (int i = 1; i < coordinates.length; i++) {
            for (int j = i + 1; j < coordinates.length; j++) {
                Edge e = new Edge(coordinates[i], coordinates[j]);
                pq.offer(e);
            }
        }

        double answer = 0;
        if (count == N - 1) {
            double result = Math.round(answer * 100.0) / 100.0;
            System.out.print(String.format("%.2f", result));
            return;
        }

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            boolean executed = union(parent, edge.c1.no, edge.c2.no);

            if (executed) {
                answer += edge.distance;
                count++;
                if (count == N - 1) break;
            }
        }

        double result = Math.round(answer * 100.0) / 100.0;
        System.out.print(String.format("%.2f", result));
    }

    private static boolean union(int[] parent, int a, int b) {
        int pa = find(parent, a);
        int pb = find(parent, b);

        if (pa != pb) {
            parent[pa] = pb;
            return true;
        }

        return false;
    }

    private static int find(int[] parent, int a) {
        if (parent[a] == a) return a;
        else return parent[a] = find(parent, parent[a]);
    }

    static class Coordinate {
        int no;

        double x;
        double y;

        public Coordinate(int no, double x, double y) {
            this.no = no;
            this.x = x;
            this.y = y;
        }

        public double distanceTo(Coordinate dest) {
            return Math.sqrt(
                    Math.pow((this.x - dest.x), 2) + Math.pow((this.y - dest.y), 2)
            );
        }
    }

    static class Edge {
        Coordinate c1;
        Coordinate c2;
        double distance;

        public Edge(Coordinate c1, Coordinate c2) {
            this.c1 = c1;
            this.c2 = c2;
            this.distance = c1.distanceTo(c2);
        }

        public double getDistance() {
            return distance;
        }
    }
}

/**
 * 만들어야 할 최소의 통로 길이
 *
 * - 기본 정보를 토대로 union-find로 집합 만들기.
 * - 약 100만개의 모든 간선에 대해 mst 알고리즘 시행. 사이클을 방지해야 함
 * - 싸이클 방지를 하면 알아서 중복 간선을 제거해준다.
 */