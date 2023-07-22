import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int numOfCity = Integer.parseInt(st.nextToken());
        int numOfRoad = Integer.parseInt(st.nextToken());
        int targetDist = Integer.parseInt(st.nextToken());
        int startNum = Integer.parseInt(st.nextToken());

        boolean[] visited = new boolean[numOfCity + 1];
        int[] distances = new int[numOfCity + 1]; // X는 1~N이다.
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startNum] = 0;

        List<Integer>[] cityGraph = new ArrayList[numOfCity + 1]; // X는 1~N이다.
        for (int i = 0; i < cityGraph.length; i++) {
            cityGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < numOfRoad; i++) {
            st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            cityGraph[start].add(end); // 단방향 추가
        }


        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startNum);
        while (!queue.isEmpty()) {
            int start = queue.poll();
            visited[start] = true;

            for (int dest : cityGraph[start]) {
                if (!visited[dest]) {
                    if (distances[dest] > distances[start] + 1) distances[dest] = distances[start] + 1;
                    queue.offer(dest);
                }
            }
        }

        PriorityQueue<Integer> pq = new PriorityQueue();
        for (int i = 1; i < distances.length; i++) {
            if (distances[i] == targetDist) pq.offer(i);
        }

        if (pq.isEmpty()) bw.write("-1");

        for (int cityNum : pq) {
            bw.write(cityNum + "\n");
        }

        bw.flush();
        bw.close();
    }
}
/*
- 1~ N번까지 도시
- M개 단방향
- 모든 도로 거리는 1
- X에서 각 도시까지 거리 중 최단 거리가 정확히 K인 경우

[시간 복잡도]
- 초기화시 N + M
- 결국 간선의 개수만큼 탐색 M
- O(N + M)

[공간 복잡도]
2N(방문체크, 거리배열) + M(그래프)
O(N + M)
 */