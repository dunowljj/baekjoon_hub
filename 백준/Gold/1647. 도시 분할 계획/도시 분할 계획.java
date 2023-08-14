import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static class Node {
        int weight;
        int houseNo;

        public Node(int weight, int houseNo) {
            this.weight = weight;
            this.houseNo = houseNo;
        }

        public int getWeight() {
            return weight;
        }
    }

    static List<Node>[] graph;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];
        visited = new boolean[N + 1];
        for (int i = 0; i < N + 1; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            graph[A].add(new Node(C, B));
            graph[B].add(new Node(C, A));
        }

        int sum = 0;
        int max = Integer.MIN_VALUE;
        PriorityQueue<Node> pq = new PriorityQueue<>(comparingInt(Node::getWeight));
        pq.offer(new Node(0, 2));

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (visited[now.houseNo]) continue;

            visited[now.houseNo] = true;
            sum += now.weight;
            max = Math.max(now.weight, max);

            for (Node next : graph[now.houseNo]) {
                pq.offer(new Node(next.weight, next.houseNo));
            }
        }

        System.out.println(sum - max);
    }
}

/**
 * 양방향
 * 길 유지비
 * 임의의 두 집 사이 경로가 항상 존재
 */