import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] parent = new int[N + 1];
        boolean[] visited = new boolean[N + 1];
        List<Integer>[] adjList = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adjList[a].add(b);
            adjList[b].add(a);
        }

        //bfs
        Queue<Integer> queue = new LinkedList();

        visited[1] = true;
        queue.add(1);

        while (!queue.isEmpty()) {
            int curr = queue.poll();

            for (Integer next : adjList[curr]) {
                if (!visited[next]) {
                    visited[next] = true;
                    parent[next] = curr;
                    queue.add(next);
                }
            }

        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= N; i++) {
            sb.append(parent[i]).append("\n");
        }

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
}
