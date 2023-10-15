import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.Comparator.*;

public class Main {
    static class Area {
        int no;
        int distance;

        public Area(int no, int distance) {
            this.no = no;
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }
    }

    static List<Area>[] adjList;
    static int[] itemCounts;
    static int n;
    static int findRange;
    static int[] d;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        findRange = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        adjList = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList();
        }

        itemCounts = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            itemCounts[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            adjList[a].add(new Area(b, l));
            adjList[b].add(new Area(a, l));
        }

        int max = Integer.MIN_VALUE;
        for (int startNo = 1; startNo <= n; startNo++) {
            max = Math.max(max, countItem(startNo));
        }

        System.out.print(max);
    }

    private static int countItem(int startNo) {
        dijkstra(startNo);

        return IntStream.range(1, d.length)
                .filter((i) -> d[i] <= findRange)
                .map((i) -> itemCounts[i])
                .sum();
    }

    private static void dijkstra(int startNo) {
        boolean[] visited = new boolean[n + 1];
        d = new int[n + 1];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[startNo] = 0;
        PriorityQueue<Area> pq = new PriorityQueue<>(comparingInt(Area::getDistance));

        pq.offer(new Area(startNo, itemCounts[startNo]));
        visited[startNo] = true;

        while (!pq.isEmpty()) {
            Area now = pq.poll();
            visited[now.no] = true;

            for (Area next : adjList[now.no]) {
                if (!visited[next.no] && d[next.no] > next.distance + d[now.no]) {
                    d[next.no] = next.distance + d[now.no];
                    pq.offer(new Area(next.no, d[next.no]));
                }
            }
        }
    }


}

/**
 * 100개의 지역번호에서 시작하는 경우에 대해 다익스트라를 실행해서 최대 아이템 개수 찾기
 */
