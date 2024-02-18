import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Bridge>[] adjList = new ArrayList[n + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int weightLimit = Integer.parseInt(st.nextToken());

            adjList[a].add(new Bridge(b, weightLimit));
            adjList[b].add(new Bridge(a, weightLimit));
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        boolean[] visited = new boolean[n + 1];

        PriorityQueue<Bridge> pq = new PriorityQueue<>(comparingInt(Bridge::getWeightLimit).reversed());
        pq.offer(new Bridge(start, Integer.MAX_VALUE));

        int minLimit = Integer.MAX_VALUE;
        while (!pq.isEmpty()) {
            Bridge now = pq.poll();
            if (visited[now.end]) continue;

            visited[now.end] = true;
            minLimit = Math.min(minLimit, now.weightLimit);

            if (now.end == end) break;

            for (Bridge next : adjList[now.end]) {
                if (!visited[next.end]) {
                    pq.offer(new Bridge(next.end, next.weightLimit));
                }
            }
        }

        System.out.print(minLimit);
    }

    static class Bridge {
        int end;
        int weightLimit;

        public Bridge(int end, int weightLimit) {
            this.end = end;
            this.weightLimit = weightLimit;
        }

        public int getWeightLimit() {
            return weightLimit;
        }
    }
}
/**
 * 무게 제한 10억
 * 한 번의 이동이란건 뭘 뜻하나? 문제가 모호하다.
 * 최대스패닝트리
 * - 갔던 곳을 또 갈 필요는 없다. 어짜피 최대 무게 제한을 찾으면 된다.
 * -
*/

