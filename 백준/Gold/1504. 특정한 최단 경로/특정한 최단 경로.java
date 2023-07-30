import java.io.*;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    static int N;
    static int[] d;
    static List<Node>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        d = new int[N + 1];
        graph = new ArrayList[N + 1];
        for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken()); // 거리

            graph[a].add(new Node(b, c));
            graph[b].add(new Node(a, c));
        }

        // 방문해야 하는 두 정점
        st = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        int startToV1 = dijkstra(1, v1);
        int V2ToEnd = dijkstra(v2, N);

        int startToV2 = dijkstra(1, v2);
        int V1ToEnd = dijkstra(v1, N);

        int V1ToV2 = dijkstra(v1, v2);

        long minDist = V1ToV2 + Math.min((long) startToV1 + V2ToEnd
                , (long) startToV2 + V1ToEnd);

        if (minDist >= Integer.MAX_VALUE) {
            bw.write("-1");
        } else {
            bw.write(minDist+"");
        }

        bw.flush();
        bw.close();
    }

    private static int dijkstra(int start, int end) {
        Arrays.fill(d, Integer.MAX_VALUE);
        d[start] = 0;
//        boolean[] visited = new boolean[N + 1];
//        visited[start] = true;

        PriorityQueue<Node> pq = new PriorityQueue<>(comparingInt(Node::getDist));
        pq.offer(new Node(start, 0));
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            for (Node next : graph[now.no]){
                if (d[next.no] > d[now.no] + next.dist) {
//                    visited[next.no] = true;
                    d[next.no] = d[now.no] + next.dist;
                    pq.offer(new Node(next.no, d[next.no]));
                }
            }
        }

        return d[end];
    }

    static class Node {
        int no;
        int dist;

        public Node(int no, int dist) {
            this.no = no;
            this.dist = dist;
        }

        public int getDist() {
            return dist;
        }
    }
}
/*
1 -> N 까지 가야하나 두 정점을 최소한 지나야 하한다.
- 이동했던 곳을 다시 갈 수 있으나 최단경로여야 한다.
- 즉, 들러야하는 정점을 왕복하는 경우도 생긴다.
    - 들르는 정점에서 왕복 가능하게? 혹은 방문체크를 하지 않고?

v1은 N이 아니고, v2는 1이 아니다.
-> v1은 1, v2는 N이 될수도 있다는 의미?

임의의 두 정점 사이에는 간선이 최대 1개 존재한다. --> ?
 */