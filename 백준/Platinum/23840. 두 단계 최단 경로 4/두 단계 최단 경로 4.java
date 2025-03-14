import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    public static final long INF = (long) Math.pow(10, 12);
    private static int VISITED_ALL;
    private static int VISITED_EXCEPT_Z;
    private static int N,M,X,Z,P;

    private static List<Node>[] adj;
    private static long[][] minAdj;
    private static int[] yNodeNos;

    private static long[][] dp;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        adj = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // 중복제거 필요할수도 있음
            adj[u].add(new Node(v, w));
            adj[v].add(new Node(u, w));
        }

        st = new StringTokenizer(br.readLine());
        X = Integer.parseInt(st.nextToken());
        Z = Integer.parseInt(st.nextToken());

        P = Integer.parseInt(br.readLine());

        yNodeNos = new int[P];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < P; i++) {
            int y = Integer.parseInt(st.nextToken());
            yNodeNos[i] = y;
        }

        minAdj = new long[P + 2][P + 2];

        // 다익스트라로 (Y -> 모든 노드) 최소길이 구하기
        // 구한 값을 바탕으로 X,Y,Z만 존재하며, 최소거리로 양방향 연결된 인접리스트 생성
        // yNode목록의 Idx를 새 인접리스트의 no로 사용한다.
        for (int startYIdx = 0; startYIdx < P; startYIdx++) {
            int startYNo = yNodeNos[startYIdx];
            long[] dist = dijkstra(startYNo);

            for (int nodeNo = 1; nodeNo <= N; nodeNo++) {
                if (nodeNo == startYNo) continue;

                // [Y -> X]
                if (nodeNo == X) {
                    minAdj[P][startYIdx] = dist[nodeNo];
                    minAdj[startYIdx][P] = dist[nodeNo];
                    continue;
                }

                // [Y -> Z]
                if (nodeNo == Z) {
                    minAdj[P + 1][startYIdx] = dist[nodeNo];
                    minAdj[startYIdx][P + 1] = dist[nodeNo];
                    continue;
                }

                // [Y -> 다른 Y] 연결
                for (int yIdx = 0; yIdx < yNodeNos.length; yIdx++) {
                    int yNodeNo = yNodeNos[yIdx];

                    if (nodeNo == yNodeNo) {
                        minAdj[yIdx][startYIdx] = dist[nodeNo];
                        minAdj[startYIdx][yIdx] = dist[nodeNo];
                    }
                }
            }
        }

        VISITED_ALL = (1 << (P + 2)) - 1;
        VISITED_EXCEPT_Z = VISITED_ALL - (1 << (P + 1));
        dp = new long[P + 2][VISITED_ALL + 1];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        // 노드 번호를 X=P, Z=P+1로 변환한 상태
        // X는 방문했다고 가정
        System.out.print(TPS(P, (1 << P)));
    }

    private static long[] dijkstra(int start) {
        long[] minDist = new long[N + 1];
        Arrays.fill(minDist, INF);
        minDist[start] = 0;
        boolean[] visited = new boolean[N + 1];

        Queue<Node> queue = new PriorityQueue<>(comparingLong(Node::getDist));
        queue.offer(new Node(start, 0));
        while (!queue.isEmpty()) {
            Node now = queue.poll();
            if (visited[now.no]) continue;
            visited[now.no] = true;

            for (Node next : adj[now.no]) {
                if (minDist[next.no] > minDist[now.no] + next.dist) {
                    minDist[next.no] = minDist[now.no] + next.dist;
                    queue.offer(new Node(next.no, minDist[next.no]));
                }
            }
        }

        return minDist;
    }

    private static long TPS(int now, int bit) {
        if (bit == VISITED_EXCEPT_Z) {

            // 서로 연결이 불가능한 부분이 있다면, 불가
            if (minAdj[now][P + 1] == INF) {
                System.out.print(-1);
                System.exit(0);
            }

            // now -> Z까지의 거리 반환
            return minAdj[now][P + 1]; // 최종 도달하는  X=P, Z=P+1로 변환한 상태
        }

        if (dp[now][bit] != -1) {
            return dp[now][bit];
        }

        dp[now][bit] = INF;
        for (int nextNo = 0; nextNo < minAdj.length; nextNo++) {
            long toNext = minAdj[now][nextNo];

            if (toNext == 0) continue;
            if ((bit & (1 << nextNo)) == 1) continue;

            int nb = bit | (1 << nextNo);
            dp[now][bit] = Math.min(dp[now][bit], TPS(nextNo, nb) + toNext);
        }

        return dp[now][bit];
    }

    static class Node {
        int no;
        long dist;

        public Node(int no, long dist) {
            this.no = no;
            this.dist = dist;
        }

        public long getDist() {
            return dist;
        }
    }
}
/**
 * 양방향 간선임 - 중복 간선에 대한 언급이 없음
 * 싸이클이 존재 가능
 * 중복 방문해서 도착할 가능성 존재
 *
 * X -> P, P -> Y를 구하면 도착 가능성은 확인 가능하다. -> 2N
 * 하지만 최단거리를 구해야한다.
 *
 * 간선 M은 최대 30만이다.
 *  가중치 1~100만, 간선 30만개 -> int초과
 */