import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<Integer>[] adjList;
    static int[] visited;
    static boolean isFinish;
    static int V;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int k = Integer.parseInt(br.readLine()); //case 개수

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            visited = new int[V + 1];
            adjList = new ArrayList[V + 1];

            // 인접리스트 초기화
            for (int j = 1; j < V + 1; j++) {
                adjList[j] = new ArrayList<>();
            }

            // 인접리스트 내용 입력
            for (int j = 0; j < E; j++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                adjList[x].add(y);
                adjList[y].add(x);
            }


            isFinish = false;
            for (int j = 1; j < V + 1; j++) {
                if (visited[j] == 0) {
                    dfs(j);
                }
                if (isFinish) break;
            }
            if (!isFinish) {
                sb.append("YES").append("\n");
            }
        }

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    static void dfs(int start) {
        if (isFinish) return;

        // 첫 시작 방문 체크
        if (visited[start] == 0) {
            visited[start] = 1;
        }

        // 방문한 노드의 인접 노드들중에 방문하지 않은 곳 탐색
        for (int i = 0; i < adjList[start].size(); i++) {
            // NO 판정시 종료
            if (isFinish) return;
            int next = adjList[start].get(i);

            if (visited[next] == 0) {
                // 팀 분류가 안되어있으면, 분류해준다.
                classifyNode(start, next);

                dfs(next);
            }
            // 팀 분류가 되어있으면, 인접 요소들이 같은 집합인지 확인하고, 그렇다면 No표시한다.
            else if (visited[next] == visited[start]) {
                    sb.append("NO").append("\n");
                    isFinish = true;
                    return;
            }
        }
    }
    private static void classifyNode(int start, int next) {
        // 새로 방문할 노드를 현재 노드와 다른 팀으로 표시
        if (visited[start] == 1) {
            visited[next] = 2;
        } else if (visited[start] == 2) {
            visited[next] = 1;
        }
    }
}
/*
각 집합에 속한 정점끼리는 서로 인접하지 않도록??
탐색하면서 집합을 나누고, 방문했는데 집합이 다른 경우 NO 출력?

 */