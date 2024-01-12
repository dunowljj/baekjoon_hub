import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        double answer = 0.0;

        // 부모 배열 0~n-1로 초기화
        int[] parent = new int[n];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        // 노드들 초기화
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());

            nodes.add(new Node(i, x, y));
        }

        // 간선을 가진 우선순위큐 만들기. 거리가 짧은 순으로 정렬
        PriorityQueue<Edge> edges = new PriorityQueue<>(comparingDouble(Edge::getDistance));
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.add(new Edge(nodes.get(i), nodes.get(j)));
            }
        }

        while (!edges.isEmpty()) {
            Edge now = edges.poll();

            int no1 = now.start.no;
            int no2 = now.end.no;

            if (findParent(parent, no1) == findParent(parent, no2)) continue;

            union(parent, no1, no2);
            answer += now.distance;
        }

        System.out.println((Math.floor(answer * 100.0) / 100));
    }

    private static void union(int[] parent, int no1, int no2) {
        int p1 = findParent(parent, no1);
        int p2 = findParent(parent, no2);

        if (p1 < p2) {
            parent[p2] = p1;
        } else {
            parent[p1] = p2;
        }
    }

    private static int findParent(int[] parent, int no) {
        if (parent[no] == no) return no;
        return findParent(parent, parent[no]);
    }

    static class Node {
        int no;
        double x;
        double y;

        public Node(int no, double x, double y) {
            this.no = no;
            this.x = x;
            this.y = y;
        }
    }

    static class Edge {
        Node start;
        Node end;
        double distance;

        public Edge(Node start, Node end) {
            this.start = start;
            this.end = end;
            this.distance = calcDistance(start, end);
        }

        private double calcDistance(Node start, Node end) {
            return Math.sqrt(
                    Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2)
            );
        }

        public double getDistance() {
            return distance;
        }
    }
}

/**
 * 최소 비용을 구해야 한다.
 * 거리가 가장 짧은 것부터 연결하면서 union find 를 사용해보자.
 *
 */