import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int[] visited;
    static int V;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int E;
        List<Integer>[] adjList;

        int k = Integer.parseInt(br.readLine()); //case 개수

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            visited = new int[V + 1];
            adjList = new ArrayList[V + 1];

            // 인접리스트 초기화
            for (int j = 1; j <= V; j++) {
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

            bfs(1, adjList);
        }

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    static void bfs(int start, List<Integer>[] adjList) {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 1; i <= V; i++) {
            if (visited[i] == 0) {
                queue.add(i);
                visited[i] = 1;
            }

            while (!queue.isEmpty()) {
                start = queue.poll();
                for (Integer next : adjList[start]) {
                    if (visited[next] == 0) {
                        giveTeamNum(start, next);
                        queue.add(next);
                    }

                    if (visited[start] == visited[next]) {
                        sb.append("NO").append("\n");
                        return;
                    }
                }
            }
        }
        sb.append("YES").append("\n");
    }

    private static void giveTeamNum(Integer start, Integer next) {
        if (visited[start] == 1) {
            visited[next] = 2;
        } else {
            visited[next] = 1;
        }
    }
}
/*
각 집합에 속한 정점끼리는 서로 인접하지 않도록??
탐색하면서 집합을 나누고, 방문했는데 집합이 다른 경우 NO 출력?

bfs
큐에 집어넣을때, depth 별로 팀을 줘야함
팀을 이미 주었던 노드를 만났는데 팀이 다르면 No

무조건 하나로 이어지는 그래프이다? -> bfs(1)만 체크하면 됨

정점이 2개 간선이 1개일 경우 무조건 옳음
 */