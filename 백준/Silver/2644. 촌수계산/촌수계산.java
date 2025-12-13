import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static List<Integer>[] graph;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        graph = new ArrayList[n + 1];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        visited = new boolean[n + 1];

        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            graph[x].add(y);
            graph[y].add(x);
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{a, 0});
        visited[a] = true;

        while (!queue.isEmpty()) {
            int[] now = queue.poll();
            int no = now[0];
            int count = now[1];

            for (int next : graph[no]) {
                if (visited[next]) continue;
                visited[next] = true;

                int nc = count + 1;
                if (next == b) {
                    System.out.print(nc);
                    return;
                }

                queue.offer(new int[]{next, nc});
            }
        }

        System.out.print(-1);
    }
}

/**
 * 그래프 생성 -> bfs -> 최단거리 탐색, 없으면 -1
 */