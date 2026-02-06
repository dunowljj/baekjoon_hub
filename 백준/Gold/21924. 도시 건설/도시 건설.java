import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M;
    static List<Node>[] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        adj = new ArrayList[N + 1];
        for (int i = 1; i < N + 1; i++) {
            adj[i] = new ArrayList<>();
        }

        long totalCost = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            totalCost += cost;

            adj[a].add(new Node(b, cost));
            adj[b].add(new Node(a, cost));
        }

        long result = prim();
        System.out.print(
                (result == -1) ?
                (result) : (totalCost - result)
        );
    }

    private static long prim() {
        boolean[] visited = new boolean[N + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        pq.offer(new Node(1, 0));
        long sum = 0;
        int connected = 0;

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (visited[now.no]) continue;
            visited[now.no] = true;

            sum += now.cost;
            connected++;

            if (connected == N) return sum;

            for (Node next : adj[now.no]) {
                if (visited[next.no]) continue;
                pq.offer(next);
            }
        }

        return -1;
    }

    static class Node {
        int no;
        int cost;

        public Node(int no, int cost) {
            this.no = no;
            this.cost = cost;
        }
    }
}
/**
 * 프림 연습
 *
 * 특정 노드 기준으로 탐색 시작
 * - 가장 짧은 간선 사용
 * - 도착 노드를 기준으로 방문 체크
 *
 */