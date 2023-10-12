import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static class Node {
        int no;
        int time;

        public Node(int no, int time) {
            this.no = no;
            this.time = time;
        }

        public int getTime() {
            return time;
        }
    }

    static List<Node>[] adjList;
    static boolean[] visited;
    static int N;
    static int maxTime;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken()); // X에 갔다가 돌아오는 길도 계산해야 한다.

        adjList = new ArrayList[N + 1];
        for (int i = 1; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int T = Integer.parseInt(st.nextToken());

            adjList[A].add(new Node(B, T));
        }

        // 다익스트라를 통해 dx 배열 초기화
        int[] dx = new int[N + 1];
        dijkstra(X, X, dx);

        // N 가지 노드에 대해 다익스트라 진행
        maxTime = 0;
        for (int start = 1; start <= N; start++) {
            if (start == X) continue;

            int[] d = new int[N + 1];
            maxTime = Math.max(
                    findMinDistance(start, X, d) + dx[start],
                    maxTime);
        }
        
        System.out.print(maxTime);
    }

    private static int dijkstra(int start, int dest, int[] d) {
        visited = new boolean[N + 1];

        // 거리 배열 초기화
        Arrays.fill(d, Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>(comparingInt(Node::getTime));
        pq.offer(new Node(start, 0));
        visited[start] = true;
        d[start] = 0;

        while (!pq.isEmpty()) {
            Node now = pq.poll();
            visited[now.no] = true;

            for (Node next : adjList[now.no]) {
                if (!visited[next.no] && d[next.no] > d[now.no] + next.time) {
                    d[next.no] = d[now.no] + next.time;
                    pq.offer(new Node(next.no, d[next.no]));
                }
            }
        }

        return d[dest];
    }

    private static int findMinDistance(int start, int dest, int[] d) {
        return dijkstra(start, dest, d);
    }
}

/**
 * X에 갈 수 있고, 돌아올 수 있는 데이터만 주어진다.
 */