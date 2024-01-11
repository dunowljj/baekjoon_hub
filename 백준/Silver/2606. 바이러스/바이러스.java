import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numOfComputer = Integer.parseInt(br.readLine());
        int numOfEdge = Integer.parseInt(br.readLine());

        List<Integer>[] network = new ArrayList[numOfComputer + 1]; // 컴퓨터 번호는 1번부터 차례로
        for (int i = 0; i < network.length; i++) {
            network[i] = new ArrayList<>();
        }

        for (int i = 0; i < numOfEdge; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            network[a].add(b);
            network[b].add(a);
        }

        System.out.print(bfs(1, numOfComputer, network));
    }

    private static int bfs(int start, int n, List<Integer>[] network) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        visited[start] = true;
        queue.offer(start);

        int count = 0; //1번 미포함 카운트
        while (!queue.isEmpty()) {
            int now = queue.poll();

            for (int next : network[now]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                    count++;
                }
            }
        }

        return count;
    }
}
