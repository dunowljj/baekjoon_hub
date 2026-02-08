import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static List<Node>[] adj;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        int totalInc = t * (((N - 1) * (N - 2)) / 2);

        adj = new ArrayList[N + 1];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());

            adj[A].add(new Node(B, C));
            adj[B].add(new Node(A, C));
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(comparingInt((n1) -> n1.cost));
        pq.offer(new Node(1, 0));

        boolean[] visited = new boolean[N + 1];
        int totalCost = 0;
        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (visited[now.no]) continue;
            visited[now.no] = true;

            totalCost += now.cost;

            for (Node next : adj[now.no]) {
                if (!visited[next.no]) {
                    pq.offer(next);
                }
            }
        }

        System.out.print(totalCost + totalInc);
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
 * 모든 도시를 정복해야함. -> mst
 * 비용의 증가는 고정 값인 t이므로 순서는 상관없다.
 * E = (N - 1)
 * t(0 + 1 + 2..+ (E - 1))
 * t*E(E-1)/2 -> t*(N-1)(N-2)/2
 *
 * 연습겸 프림 사용
 *
 */