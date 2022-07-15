import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int M;
    static int V;
    static ArrayList<Integer>[] adjList;
    static boolean[] visited;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());

        adjList = new ArrayList[N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < N + 1; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            adjList[x].add(y);
            adjList[y].add(x);
        }

        for (int i = 0; i < adjList.length; i++) {
            Collections.sort(adjList[i]);
        }

        dfs(V);
        sb.append("\n");
        bfs(V);
        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    static void dfs(int start) {
        visited[start] = true;
        sb.append(start).append(" ");

        for (Integer next : adjList[start]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
    }

    static void bfs(int start) {
        Queue<Integer> queue = new LinkedList();
        visited = new boolean[N + 1];
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            start = queue.poll();
            sb.append(start).append(" ");

            for (Integer next : adjList[start]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }
    }
}
/*
여러 개의 간선 가능
정점 주어짐
정점 번호 여러개 -> 작은 것 먼저 방문 -> 정렬

인접 리스트 사용, 간선의 개수가 적기도 함.
 */