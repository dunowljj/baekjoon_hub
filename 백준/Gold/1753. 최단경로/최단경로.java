import java.io.*;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    public static final String NEW_LINE = "\n";

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

            for (Node next : graph[now.no]) {
                if (distances[next.no] > distances[now.no] + next.distance) {
                    distances[next.no] = distances[now.no] + next.distance;
                    pq.offer(new Node(next.no, distances[next.no]));
                }
            }
        }
        
        for (int i = 1; i < distances.length; i++) { //0번 인덱스 제거 기억하기
            if (distances[i] == Integer.MAX_VALUE) {
                bw.write("INF" + NEW_LINE);
            } else {
                bw.write(distances[i] + NEW_LINE);
            }
        }
        
        bw.flush();
        bw.close();
    }
}
