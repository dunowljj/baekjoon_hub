import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<int[]>[] adjList;
    static boolean[] visited;
    static int max = 0;
    static int farNode = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        adjList = new ArrayList[n + 1];
        visited = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 1; i <= n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            adjList[node].add(new int[]{child, weight});
            adjList[child].add(new int[]{node, weight});
        }

        if (n != 1) {

            dfs(1, 0);

            dfs(farNode, 0);

            bw.write(max + "");
        } else {
            bw.write(0+"");
        }
        bw.flush();
        bw.close();
    }

    static void dfs(int start, int sum) {
        if (max < sum) {
            max = sum;
            farNode = start;
        }

        int next = 0;
        int len = 0;
        visited[start] = true;
        for (int[] curr : adjList[start]) {
            next = curr[0];
            len = curr[1];
            if (!visited[next]) {
                dfs(next, sum + len);
            }
        }
        visited[start] = false;
    }
}
/*
부모 노드의 번호가 작은 것부터 입력, 같으면 자식 노드의 번호가 작은 것 먼저 입력 -> 최소힙?
루트 노드가 1임

지름의 양끝노드중 하나 찾은 후, 해당 노드 활용해서 탐색
입력조건에 n이 1부터인데, 1인 경우 입력 간선이 없어서 dfs로직에서 nullpointer가 발생하므로 처리해줘야 한다.
 */