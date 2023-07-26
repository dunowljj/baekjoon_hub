import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class toNext {
        int loc;
        int distance;

        public toNext(int loc, int distance) {
            this.loc = loc;
            this.distance = distance;
        }

        public int getLoc() {
            return loc;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());

        List<toNext>[] shortcuts = new ArrayList[D + 1];
        for (int i = 0; i < D + 1; i++) {
            shortcuts[i] = new ArrayList();
            shortcuts[i].add(new toNext(i + 1, 1));
        }

        shortcuts[D].remove(0);

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());

            if (end > D) continue;
            if (end - start <= distance) continue;

            shortcuts[start].add(new toNext(end, distance));
        }

        int[] distances = new int[D + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[0] = 0;

        PriorityQueue<toNext> pq = new PriorityQueue<>(Comparator.comparingInt(toNext::getLoc));
        pq.offer(new toNext(0, 0));
        while (!pq.isEmpty()) {
            toNext now = pq.poll();

            for (toNext toNext : shortcuts[now.loc]) {
                if (distances[toNext.loc] > distances[now.loc] + toNext.distance) {
                    distances[toNext.loc] = distances[now.loc] + toNext.distance;
                    pq.offer(new toNext(toNext.loc, distances[toNext.loc]));
                }
            }
        }

        System.out.println(distances[D]);
    }
}

/*
도착 - 출발이 기본적인 거리이며, 지름길이랑 비교해서 그래프를 만든다?
1) 0부터 근접한 노드(지름길이 존재하는 위치)를 탐색한다.
2) 각 위치를 노드로 삼아 최단경로를 각각 탐색한다.
3) 지름길로 더 멀리 가버리는 경우도 있다.

0부터 다른 노드까지를 모두 만들어놓으면 어떨까?
 */