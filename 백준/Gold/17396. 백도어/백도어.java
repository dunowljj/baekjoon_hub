import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static class Point {
        int no;
        long time;

        public Point(int no, long time) {
            this.no = no;
            this.time = time;
        }

        public long getTime() {
            return time;
        }
    }

    private static List<Point>[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        boolean[] cannotGo = new boolean[N];
        long[] d = new long[N];
        Arrays.fill(d, Long.MAX_VALUE);

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int visible = Integer.parseInt(st.nextToken());

            if (visible == 1) {
                cannotGo[i] = true;
            }
        }

        // 마지막 분기점은 시야에 보이지만 가야하는 곳이다.
        cannotGo[N - 1] = false;

        if (cannotGo[0]) {
            System.out.print("-1");
            System.exit(0);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b  = Integer.parseInt(st.nextToken());
            int t  = Integer.parseInt(st.nextToken());

            if (a == b) continue;

            adjList[a].add(new Point(b, t));
            adjList[b].add(new Point(a, t));
        }

        PriorityQueue<Point> pq = new PriorityQueue<>(comparingLong(Point::getTime));
        pq.offer(new Point(0, 0));
        d[0] = 0;

        while (!pq.isEmpty()) {
            Point now = pq.poll();
            cannotGo[now.no] = true; // 방문체크

            if (now.time > d[now.no]) continue;
            if (now.time > d[N - 1]) break;

            for (Point next : adjList[now.no]) {
                if (!cannotGo[next.no] && d[next.no] > now.time + next.time) {
                    d[next.no] = now.time + next.time;
                    pq.offer(new Point(next.no, d[next.no]));
                }
            }
        }

        if (d[N-1] == Long.MAX_VALUE) {
            System.out.print("-1");
        } else {
            System.out.print(d[N-1]);
        }
    }
}
// 제자리로 가는 경우?
// 한 분기점에서 다른 분기점으로 가는 간선은 최대 1개 존재한다.