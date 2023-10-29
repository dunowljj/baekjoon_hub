import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    static class Node {
        int no;
        int weight;

        public Node(int no, int weight) {
            this.no = no;
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }

    static List<Node>[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] d = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        Arrays.fill(d, Integer.MAX_VALUE);
        adjList = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) adjList[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            adjList[a].add(new Node(b, c));
            adjList[b].add(new Node(a, c));
        }

        st = new StringTokenizer(br.readLine());
        int startNo = Integer.parseInt(st.nextToken());
        int endNo = Integer.parseInt(st.nextToken());

        PriorityQueue<Node> pq = new PriorityQueue<>(comparingInt(Node::getWeight));
        pq.offer(new Node(startNo, 0));
        d[startNo] = 0;

        while (!pq.isEmpty()) {
            Node now = pq.poll();
            visited[now.no] = true;

            if (d[now.no] < now.weight) continue;
            if (d[endNo] < now.weight) break;

            for (Node next : adjList[now.no]) {
                if (!visited[next.no] && d[next.no] > next.weight + d[now.no]) {
                    d[next.no] = next.weight + d[now.no];
                    pq.offer(new Node(next.no, d[next.no]));
                }
            }
        }

        System.out.print(d[endNo]);
    }
}
