import java.io.*;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    public static final String NEW_LINE = "\n";
    public static final String INF = "INF";

    static class Node {
        int no;
        int distance;

        public Node(int no, int distance) {
            this.no = no;
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(br.readLine());

        int[] distances = new int[V + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        boolean[] visited = new boolean[V + 1];

        List<Node>[] graph = new ArrayList[V + 1];
        for (int i = 0; i < V + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            graph[u].add(new Node(v, w));
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(comparingInt(Node::getDistance));
        pq.offer(new Node(start, 0));
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            
            if (now.distance > distances[now.no]) continue;

            visited[now.no] = true;

            for (Node next : graph[now.no]) {
                if (!visited[next.no] && distances[next.no] > distances[now.no] + next.distance) {
                    distances[next.no] = distances[now.no] + next.distance;
                    pq.offer(new Node(next.no, distances[next.no]));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < distances.length; i++) { //0번 인덱스 제거 기억하기
            if (distances[i] == Integer.MAX_VALUE) {
                sb.append(INF).append(NEW_LINE);
            } else {
                sb.append(distances[i]).append(NEW_LINE);
            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}