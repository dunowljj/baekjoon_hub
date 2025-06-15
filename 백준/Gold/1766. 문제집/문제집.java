import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";
    private static int N,M;
    private static Set<Integer>[] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[] indegree = new int[N + 1];
        adj = new HashSet[N + 1];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new HashSet<>();
        }

        // adj : a -> b
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adj[a].add(b);
            indegree[b]++;
        }

        // 진입차수가 0인 노드 작업대기
        Queue<Integer> preNodes = new PriorityQueue<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                preNodes.add(i);
            }
        }

        // 리프 처리 및 갱신, 위상정렬
        StringBuilder sb = new StringBuilder();
        while (!preNodes.isEmpty()) {
            int pre = preNodes.poll();
            sb.append(pre).append(SPACE);

            for (Integer next : adj[pre]) {
                indegree[next]--;

                if (indegree[next] == 0) preNodes.add(next);
            }
        }

        System.out.print(sb.toString().trim());
    }
}
/**
 * 항상 풀 수 있는 경우로 주어진다. -> 싸이클 존재 x
 * 그래프를 만들고, 맨 왼쪽부터 가장 작은 것부터 꺼내기
 */