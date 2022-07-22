import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<Integer>[] adjList;
    static boolean[] visited;
    static boolean[] visitedD;
    static boolean findCycle = false;

    static StringBuilder answer = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        adjList = new ArrayList[N + 1]; //1~N
        visited = new boolean[N + 1];
        visitedD = new boolean[N + 1];

        // 인접리스트 List 초기화
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        // 인접리스트 값 입력
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adjList[a].add(b);
            adjList[b].add(a);
        }

        // dfs로 싸이클 찾기. 찾을 시 해당 싸이클만 방문체크 되어 있음.
        for (int i = 1; i <= N; i++) {
            int first = i;
            visited[first] = true;
            dfs(0,first, i);
            if(findCycle) break;
            visited[first] = false;
        }

        /*// 싸이클 제대로 찾아지는지 확인
        for (int i = 1; i <= N; i++) {
            System.out.println(visited[i]);
        }*/

        // 방문된 지역까지 몇 번 가야하는지 확인
        for (int i = 1; i <= N; i++) {
            visitedD[i] = true;
            dfsDistance(i, 0);
            visitedD[i] = false;
        }

        bw.write(answer.toString().trim());
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, int first, int start) {
        /*System.out.println(start); //시작점 순서대로 찾는지 확인*/
        for (Integer next : adjList[start]) {
            // 시작점에 돌아오면 종료
            if (first == next && depth > 1) {
                visited[next] = true;
                findCycle = true;
                return;
            }
            // 다음 노드에 방문하지 않은 경우 추가 탐색
            if (!visited[next]) {
                visited[next] = true;
                dfs(depth + 1, first, next);
                if(findCycle) return; //이미 싸이클 찾은 경우 방문 해제 없이 종료
                visited[next] = false;
            }
        }
    }

    private static void dfsDistance(int start, int distance) {
        if (visited[start]) {
            answer.append(distance).append(" ");
            return;
        }

        for (Integer next : adjList[start]) {
            if(!visitedD[next]) {
                visitedD[next] = true;
                dfsDistance(next, distance + 1);
                visitedD[next] = false;
            }
        }
    }
}
/*
각 역과 순환선 사이의 거리 출력하기
- 역의 개수 3~ 3000
- 순환선1 지선2

싸이클이 형성되는 부분을 구하고, 해당 부분과 만나는 지점까지 몇 번을 이동해야하는지 구해야 한다.
*예상
깊이 우선 탐색으로 싸이클을 발견 -> 어떻게 발견할 것인가? 이전에는 시작점을 저장하고, 다시 돌아오는지 탐색하곤 했다.
-> 그런데, 시작점을 저장 후 탐색하면, 3000*3000가지를 모두 탐색해야한다. 9백만이다. 2가지 노드를 갈 수 있는 경우를 고려하면
최악에는 2천만 가까이 된다. 이 정도면 문제없긴하다.

인접 리스트를 구현하고, for문으로 처음부터 끝 역까지 각각 시작점으로 삼아 순차적으로 dfs하면서 방문 체크를 한다.
문제는 순환선의 표시를 어떻게 할 것이냐이다. 싸이클을 발견한 순간은 순환선을 한바퀴 순회해서 시작점에 도착한 순간이다. 크루스칼 알고리즘을 사용해서
부모노드를 표현해야하나?

-> 아니면 방문체크를 하고, 플래그를 사용해서 싸이클을 찾은 경우에는 방문체크를 해제하지 않고, 탈출하도록 만드는것은 어떨까?
순환선은 항상 존재하므로, 찾으면 탈출해서 각역까지의 거리를 계산하는 것이다.
 */