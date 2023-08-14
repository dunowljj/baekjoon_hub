import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static class Connect {
        int no;
        int cost;

        public Connect(int no, int cost) {
            this.no = no;
            this.cost = cost;
        }

        public Connect(int cost) {
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }
    }

    static List<Connect>[] graph;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        graph = new ArrayList[N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < N + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph[a].add(new Connect(b, c));
            graph[b].add(new Connect(a, c));
        }

        int sum = 0;
        PriorityQueue<Connect> pq = new PriorityQueue<>(comparingInt(Connect::getCost));
        pq.offer(new Connect(1, 0));
        while (!pq.isEmpty()) {
            Connect now = pq.poll();

            if (visited[now.no]) continue;
            visited[now.no] = true;
            sum += now.cost;

            for (Connect next :graph[now.no]){
                pq.offer(next);
            }
        }

        System.out.println(sum);
    }
}
