import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        int no;
        int cost;

        public Node(int no, int cost) {
            this.no = no;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int numOfCity = Integer.parseInt(br.readLine());
        int numOfBus = Integer.parseInt(br.readLine());

        int[] costs = new int[numOfCity + 1];
        Arrays.fill(costs, Integer.MAX_VALUE);

        boolean[] visited = new boolean[numOfCity + 1];

        List<Node>[] adjList = new ArrayList[numOfCity + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        // [시작점] -> {도착점, 비용} 인접리스트 생성
        for (int i = 0; i < numOfBus; i++) {
            st = new StringTokenizer(br.readLine());
            int startNo = Integer.parseInt(st.nextToken());
            int endNo = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            adjList[startNo].add(new Node(endNo, cost));
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> n1.cost - n2.cost);
        pq.offer(new Node(start, 0));
        visited[start] = true;
        costs[start] = 0;

        while (!pq.isEmpty()) {
            Node now = pq.poll();
            visited[now.no] = true;

            for (Node dest : adjList[now.no]) {
                if (visited[dest.no]) continue;

                if (costs[dest.no] > costs[now.no] + dest.cost) {
                    costs[dest.no] = costs[now.no] + dest.cost;
                    pq.offer(new Node(dest.no, costs[dest.no]));
                }
            }

            if (now.no == end) break;
        }

        bw.write(costs[end] +"");
        bw.flush();
        bw.close();
    }
}