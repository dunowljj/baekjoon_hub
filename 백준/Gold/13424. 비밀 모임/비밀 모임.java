import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";
    static List<Room>[] routes;
    static int[][] dists;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            routes = new ArrayList[N + 1];
            for (int i = 0; i < routes.length; i++) {
                routes[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                routes[a].add(new Room(b, c));
                routes[b].add(new Room(a, c));
            }

            int K = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            int[] friends = new int[K];
            for (int i = 0; i < K; i++) {
                friends[i] = Integer.parseInt(st.nextToken());
            }

            dists = new int[K][N + 1];
            for (int i = 0; i < dists.length; i++) {
                Arrays.fill(dists[i], Integer.MAX_VALUE);
            }

            for (int startIdx = 0; startIdx < K; startIdx++) {
                PriorityQueue<Room> pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.dist));
                boolean[] visited = new boolean[N + 1];

                int[] dist = dists[startIdx];
                int start = friends[startIdx];

                pq.offer(new Room(start, 0));
                dist[start] = 0;

                while (!pq.isEmpty()) {
                    Room now = pq.poll();

                    if (visited[now.no]) continue;
                    visited[now.no] = true;

                    for (Room next : routes[now.no]) {
                        if (dist[next.no] > dist[now.no] + next.dist) {
                            dist[next.no] = dist[now.no] + next.dist;
                            pq.offer(new Room(next.no, dist[next.no]));
                        }
                    }
                }
            }

            int min = Integer.MAX_VALUE;
            int minDest = 0;
            for (int dest = 1; dest <= N; dest++) {
                int sum = 0;
                for (int startIdx = 0; startIdx < K; startIdx++) {
                    int[] dist = dists[startIdx];
                    sum += dist[dest];
                }

                if (min > sum) {
                    min = Math.min(min, sum);
                    minDest = dest;
                }
            }

            answer.append(minDest).append(System.lineSeparator());
        }

        System.out.print(answer.toString().trim());
    }

    static class Room {
        int no;
        int dist;

        public Room(int no, int dist) {
            this.no = no;
            this.dist = dist;
        }
    }
}
