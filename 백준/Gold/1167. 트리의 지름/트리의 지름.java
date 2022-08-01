import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<int[]>[] adjList;
    static boolean[] visited;
    static int maxDist = 0;
    static int farNode = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int V = Integer.parseInt(br.readLine());

        adjList = new ArrayList[V + 1];
        visited = new boolean[V + 1];

        for (int i = 1; i <= V; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < V; i++) {
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int next = -1;
            while ((next = Integer.parseInt(st.nextToken())) != -1) {
                int distance = Integer.parseInt(st.nextToken());
                adjList[node].add(new int[]{next, distance});
            }
        }

        // 현재에서 가장 먼 노드 찾기
        dfs(1, 0);
        // 찾은 노드에서 가장 먼 노드까지 거리 찾기
        dfs(farNode, 0);
        
        bw.write(maxDist+"");
        bw.flush();
        bw.close();
    }

    static void dfs(int start, int dist) {
        if (maxDist < dist) {
            farNode = start;
            maxDist = dist;
        }

        visited[start] = true;
        int next;
        int currDist;
        for (int[] element : adjList[start]) {
            next = element[0];
            currDist = element[1];

            if (!visited[next]) {
                visited[next] = true;
                dfs(next,dist + currDist);
                visited[next] = false;
            }
        }
        visited[start] = false;
    }
}
/*
해결 : 어떤 정점에서 시작하던 최장거리일때의 맨끝 노드 둘 중 하나를 만난다.
아무 정점에서 시작하여 한쪽 끝 노드를 찾고, 찾은 노드부터 시작하여 최장거리를 찾으면 해결된다.
 */
