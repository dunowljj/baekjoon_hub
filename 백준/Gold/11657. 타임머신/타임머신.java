import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Node {
        int cityNo;
        int distance;

        public Node(int cityNo, int distance) {
            this.cityNo = cityNo;
            this.distance = distance;
        }
    }

    static List<Node>[] graph;
    static long[] d;
    static int N;
    static int M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // init distance array
        d = new long[N + 1];
        Arrays.fill(d, Integer.MAX_VALUE);

        // init bus graph
        graph = new ArrayList[N + 1];
        for (int i = 0; i <= N ; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int dest = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());

            graph[start].add(new Node(dest, distance));
        }

        int start = 1;
        d[start] = 0;

        if (hasNegativeCycle()) {
            System.out.println("-1");
        } else {
            for (int i = 2; i <= N; i++) System.out.println(mapUnreachable(d[i]));
        }

    }

    private static boolean hasNegativeCycle() {
        return bellmanFord();
    }

    private static boolean bellmanFord() {
        // 이 싸이클을 한 번 순회할때마다 추적 가능한 모든 노드에 대해 다음 간선 1개를 탐색한다.
        // N 번 순회한다는 것은, 최대 N개의 간선을 탐색하는 것으로,
        // 시간복잡도는 결국 O((V + 1) * E)로 VE이다.
        for (int i = 1; i <= N; i++) {
            for (int start = 1; start <= N; start++) {
                for (Node next :  graph[start]) {
                    if (d[start] != Integer.MAX_VALUE && d[next.cityNo] > d[start] + next.distance) {
                        d[next.cityNo] = d[start] + next.distance; // long 자동변환

                        // 마지막 추가 순회에서 변화가 감지되면 싸이클이 존재한다는 의미다.
                        if (N == i) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private static long mapUnreachable(long dist) {
        return (dist == Integer.MAX_VALUE) ? -1 : dist;
    }

}
