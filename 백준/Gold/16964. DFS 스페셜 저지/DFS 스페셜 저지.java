import java.io.*;
import java.util.*;

public class Main {
    static int[] visited;
//    static List<Integer>[] adjList;
    static Set<Integer>[] adjSet;
    static int order[];
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 정점 수
        N = Integer.parseInt(br.readLine());

        // 방문 여부, 인접리스트를 위해 1~N
        visited = new int[N + 1];
//        adjList = new ArrayList[N + 1];
        adjSet = new HashSet[N + 1];
        order = new int[N];

        // 인접리스트 초기화 1~N
        for (int i = 1; i <= N; i++) {
            adjSet[i] = new HashSet<>();
        }

        // 인접리스트 값 입력 N-1개 간선 /a,b범위 1~N
        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adjSet[a].add(b);
            adjSet[b].add(a);
        }

        //0~n-1 -> 0 -> 1 / 1 -> 다음 수  // 1~n-1 : n-1개
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            order[i] = Integer.parseInt(st.nextToken());
        }

        // 첫 값 1이 아닌경우 체크하고, 인접 리스트 탐색 시작을 adjList[1]부터 하기.
        // 마찬가지로 깊이도 order[1] 부터 탐색이므로 1부터 시작.
        if (order[0] == 1) {
            //방문 체크 + 종료 조건(첫 노드인 1보다 위로 거슬러 올라간 경우)
            visited[1] = -1;
            dfs(1, 1);
        }
        System.out.print("0");

    }
    static void dfs(int start, int orderIdx) {
        if (orderIdx == N) {
            System.out.print("1");
            System.exit(0);
        }
        // 노드를 거슬러 오르다 첫노드인 1, visited[1]이 start 이면 종료
        if (start == -1) {
            return;
        }
        //해당 깊이의 노드에서 방문 가능한곳이 있는지 체크
        boolean existToGo = false;
        int answer = order[orderIdx];
        if (adjSet[start].contains(answer) && visited[answer] == 0) {
            visited[answer] = start;
            existToGo = true;
            dfs(answer, orderIdx + 1);
        }


        /*for (Integer next : adjList[start]) {
            if (visited[next] == 0 && order[orderIdx] == next) {
                existToGo = true;
                visited[next] = start;
                dfs(next, orderIdx + 1);
            }
        }*/

        // 일치하는 노드가 하나도 없으면 이전 노드로 회귀
        if (!existToGo) {
            dfs(visited[start], orderIdx);
        }
        // 아무것도 못찾으면 기본값인 0
    }
}
/*
1부터 시작하고, 주어진 방문순서로 갈 수 있는지 확인?
인접리스트를 확인하여, 다음에 갈 수 있는 목록들 중 주어진 순서와 일치하는 것만 탐색.

깊이 우선 탐색을 하는 것이지, 다음 노드와 연결되지 않은 부분도 탐색해야한다.
 -> 전체를 탐색하되, 체크하면서 탐색, 존재하면 1저장 후 리턴 -> 모두 탐색 후, 남은 것들을 탐색해야 한다.

0번째부터 포문 돌리는게 문제가 아니고, 포기하고 깊이가 낮은 상위 노드로 이동해야한다.
방문순서 일치 조건과 방문체크가 함께 있기때문에, 상위 노드 탐색을 못한다.
 */