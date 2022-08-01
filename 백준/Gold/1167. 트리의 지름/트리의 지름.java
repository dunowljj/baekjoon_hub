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
연결된 곳으로 계속해서 이동

모든 곳을 시작점으로 dfs를 실행해서, 간선의 길이를 합하고 최댓값을 구한다.
bfs를 사용하기에는 맨 끝점부터 시작해야하는데, 간선이 하나뿐인 노드가 있을거란 보장도 없고, 그 위치를 특정하기 힘들다.
값도 매번 저장해야 한다.

생각해보니 트리에는 사이클이 존재하지 않는다. 간선이 하나뿐인 노드는 무조건 존재한다.

10만인데 N제곱의 복잡도? 오히려 맨 끝이 아닌 노드에서 시작하여 하나만 탐색하면 어떨까? 어짜피 탐색하지 않은 부분까지 더한다
 */