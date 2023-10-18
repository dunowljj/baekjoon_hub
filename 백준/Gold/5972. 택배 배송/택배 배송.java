import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static class Point {
        int no;

        int cost;

        public Point(int no, int cost) {
            this.no = no;
            this.cost = cost;
        }
    }

    private static List<Point>[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        adjList = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        int[] d = new int[N + 1];
        Arrays.fill(d, Integer.MAX_VALUE);
        boolean[] visited = new boolean[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            adjList[A].add(new Point(B, C));
            adjList[B].add(new Point(A, C));
        }

        d[1] = 0;
        PriorityQueue<Point> pq = new PriorityQueue<>(comparingInt(p -> p.cost));
        pq.offer(new Point(1, 0));

        while (!pq.isEmpty()) {
            Point now = pq.poll();
            visited[now.no] = true;

            for (Point next : adjList[now.no]) {
                if (!visited[next.no] && d[next.no] > now.cost + next.cost) {
                    d[next.no] = now.cost + next.cost;
                    pq.offer(new Point(next.no, d[next.no]));
                }
            }
        }

        System.out.print(d[N]);
    }
}
