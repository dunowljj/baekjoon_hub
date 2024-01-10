import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";
    private static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int V = Integer.parseInt(st.nextToken());


        List<Integer>[] adjList = new ArrayList[N + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            adjList[start].add(end);
            adjList[end].add(start);
        }

        for (int i = 0; i < adjList.length; i++) {
            Collections.sort(adjList[i]);
        }

        boolean[] visited = new boolean[N + 1];
        visited[V] = true;
        dfs(V, adjList, visited);

        result.append(System.lineSeparator());

        visited = new boolean[N + 1];
        bfs(V, adjList, visited);

        System.out.println(result.toString());
    }

    private static void bfs(int start, List<Integer>[] adjList, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int poll = queue.poll();
            result.append(poll).append(SPACE);

            for (int next : adjList[poll]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
    }

    private static void dfs(int start, List<Integer>[] adjList, boolean[] visited) {
        visited[start] = true;
        result.append(start).append(SPACE);

        for (int next : adjList[start]) {
            if (!visited[next]) {
                dfs(next, adjList, visited);
            }
        }
    }
}
/**
 * 정점 번호는 1~N
 * 방문할 수 있는 정점이 여러 개인 경우에는 정점 번호가 작은 것을 먼저 방문
 */