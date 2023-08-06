import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    static class Node {
        int no;
        int time;
        public Node(int no, int time) {
            this.no = no;
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;

            Node node = (Node) o;

            return no == node.no;
        }

        @Override
        public int hashCode() {
            int result = no;
            result = 31 * result + time;
            return result;
        }
    }



    static Set<Node>[] graph;
//    static int[][] graph;
    static long d[];
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int TestCases = Integer.parseInt(br.readLine());
        for (int t = 0; t < TestCases; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());

            graph = new HashSet[N + 1];
//            graph = new int[N + 1][N + 1];
//            for (int i = 1; i < N + 1; i++) {
//                Arrays.fill(graph[i], Integer.MAX_VALUE);
//            }
            for (int i = 0; i <= N; i++) graph[i] = new HashSet<>();

            d = new long[N + 1];

            // init Road
            // 도로 중복의 제거?
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int S = Integer.parseInt(st.nextToken());
                int E = Integer.parseInt(st.nextToken());
                int T = Integer.parseInt(st.nextToken());

//                graph[S][E] = Math.min(T, graph[S][E]);
//                graph[E][S] = graph[S][E];

                graph[S].add(new Node(E, T));
                graph[E].add(new Node(S, T));
            }

            // init wormhole
            for (int i = 0; i < W; i++) {
                st = new StringTokenizer(br.readLine());

                int S = Integer.parseInt(st.nextToken());
                int E = Integer.parseInt(st.nextToken());
                int T = -1 * (Integer.parseInt(st.nextToken()));

//                graph[S][E] = T;
                graph[S].add(new Node(E, T));
            }

            // bellmanFord
            boolean hasNegativeCycle = bellmanFord();
//            for (int src = 1; src <= N; src++) {
//                hasNegativeCycle |= bellmanFord(src);
//
//                if (hasNegativeCycle) break;
//            }

            if (hasNegativeCycle) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    private static boolean bellmanFord() {
        Arrays.fill(d, Integer.MAX_VALUE);
//        d[src] = 0;
        d[1] = 0;

        for (int i = 1; i <= N; i++) {

//            for (int start = 1; start <= N; start++) {
//                for (int end = 1; end <= N; end++) {
//                    int time = graph[start][end];
//
//                    if (d[start] != Integer.MAX_VALUE && time != Integer.MAX_VALUE && d[end] > d[start] + time) {
//                        d[end] = d[start] + time;
//
//                        if (start == N) {
//                            return true;
//                        }
//                    }
//                }
//            }
            for (int start = 1; start <= N; start++) {
                for (Node next : graph[start]) {
                    if (d[start] != Long.MAX_VALUE && d[next.no] > d[start] + next.time) {
                        d[next.no] = d[start] + next.time;

                        if (i == N) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}