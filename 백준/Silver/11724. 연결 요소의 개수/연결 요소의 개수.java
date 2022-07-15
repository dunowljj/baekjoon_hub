import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Main {
    static List<Integer>[] adjList;
    static boolean[] visited;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        adjList = new ArrayList[N + 1];
        visited = new boolean[N + 1];

        for (int i = 0; i < N + 1; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            adjList[u].add(v);
            adjList[v].add(u);
        }

        int count = 0;
        for (int i = 1; i < N + 1; i++) {
            if (!visited[i]) {
                count++;
            } else {
                continue;
            }
            dfs(i);
        }

        bw.write(count+"");
        bw.flush();
        bw.close();
    }

    static void dfs(int start) {
        visited[start] = true;

        for (Integer next : adjList[start]) {
            if (!visited[next]) {
                visited[next] = true;
                dfs(next);
            }
        }
    }
}
/*
인접리스트를 만들고, 연결된 모든 곳을 탐색
방문한 곳은 다시 방문하지 않도록 하고, 첫 노드에서 개수 세기

 */