import java.io.*;
import java.util.*;

public class Main {
    static int[] visited;
    static List<Integer>[] adjList;
    static int order[];
    static int answer = 1;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // 정점 수
        int N = Integer.parseInt(br.readLine());

        // 방문 여부, 인접리스트를 위해 1~N
        visited = new int[N + 1];
        adjList = new ArrayList[N + 1];
        order = new int[N + 1];

        // 인접리스트 초기화 1~N
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        // 인접리스트 값 입력 N-1개 간선 /a,b범위 1~N
        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adjList[a].add(b);
            adjList[b].add(a);
        }

        //0~n-1
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            order[i] = Integer.parseInt(st.nextToken());
        }

        if (order[0] != 1) {
            answer = 0;
        } else {
            bfs(1);
        }
        
        bw.write(answer+"");
        bw.flush();
        bw.close();
    }

    static void bfs(int start) {
        visited[start] = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        int index = 1;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            int count = 0;
            for (Integer next : adjList[curr]) {
                if (visited[next] == 0) {
                    visited[next] = 1;
                    count++;
                }
            }

            // 1~n-1
            for (int i = index; i < index + count; i++) {
                if (visited[order[i]] == 0) {
                    answer = 0;
                    return;
                } else {
                    queue.add(order[i]);
                }
            }
            index += count;
        }
    }
}
/*
여러 개의 간선 루트를 모두 구해야 한다.
2 <= N <= 100_000

해당 깊이를 모두 탐색한게 아닌데 어떻게 체크가 가능? 해당 노드와 연결된 다음 깊이만 체크되는게 아닌가?
-> 애초에 queue에 집어넣는 값이 주어지는 방문 수열이다. bfs는 먼저 넣은 값이 먼저 나오기 때문에, 먼저 넣은 방문수열에 대한 다음 깊이 탐색이 된다.
 */