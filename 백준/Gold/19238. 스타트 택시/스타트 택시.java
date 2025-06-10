import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class Main {

    public static final int WALL = -1;
    public static final int EMPTY = 0;
    private static int[][] guestMap;
    private static int N,M,fuel;
    private static Point[] dests;
    private static int guestCount = 0;

    private static final int[][] mapper = {{0, 0, -1, 1}, {1, -1, 0, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        fuel = Integer.parseInt(st.nextToken());

        guestCount = M;
        guestMap = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int e = Integer.parseInt(st.nextToken());
                if (e == 1) guestMap[i][j] = WALL;
            }
        }

        st = new StringTokenizer(br.readLine());
        int startRow = Integer.parseInt(st.nextToken());
        int startCol = Integer.parseInt(st.nextToken());
        Point start = new Point(startRow, startCol);

        dests = new Point[M + 1];
        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int guestRow = Integer.parseInt(st.nextToken());
            int guestCol = Integer.parseInt(st.nextToken());

            guestMap[guestRow][guestCol] = i;

            int destRow = Integer.parseInt(st.nextToken());
            int destCol = Integer.parseInt(st.nextToken());
            dests[i] = new Point(destRow, destCol);
        }

        while (guestCount > 0) {
            // 현재 위치 -> 손님 위치
            Guest guest = findNearestGuest(start);
            if (guest == null || guest.distance > fuel) {
                System.out.print(-1);
                return;
            }

            fuel -= guest.distance;

            // 손님 위치 -> 해당 손님 목적지
            Point dest = dests[guest.no];
            int distance = guest.now.distanceTo(dest);

            if (distance > fuel || distance == -1) {
                System.out.print(-1);
                return;
            } else {
                fuel += distance;
                start = dest;
            }
        }

        if (guestCount == 0) {
            System.out.print(fuel);
        } else {
            System.out.print(-1);
        }
    }

    private static Guest findNearestGuest(Point start) {
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N + 1][N + 1];
        queue.offer(start);
        visited[start.r][start.c] = true;

        List<Guest> target = new ArrayList<>();
        int distance = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Point now = queue.poll();

                if (guestMap[now.r][now.c] > 0) {
                    int nowNo = guestMap[now.r][now.c];
                    guestMap[now.r][now.c] = 0;
                    guestCount--;

                    return new Guest(now, 0, nowNo);
                }

                for (int dir = 0; dir < 4; dir++) {
                    int nr = now.r + mapper[0][dir];
                    int nc = now.c + mapper[1][dir];

                    if (nr < 1 || nr > N || nc < 1 || nc > N) continue;
                    if (guestMap[nr][nc] == WALL || visited[nr][nc]) continue;
                    visited[nr][nc] = true;

                    if (guestMap[nr][nc] != EMPTY)
                        target.add(new Guest(new Point(nr, nc), distance + 1, guestMap[nr][nc]));
                    else
                        queue.offer(new Point(nr, nc));
                }
            }
            distance++;

            if (!target.isEmpty()) {
                int minNo = Integer.MAX_VALUE;
                Collections.sort(target);
                Guest next = target.get(0);

                guestMap[next.now.r][next.now.c] = 0;
                guestCount--;
                return next;
            }
        }

        return null;
    }

    static class Guest implements Comparable<Guest> {
        Point now;
        int distance;
        int no;


        public Guest(Point now, int distance, int no) {
            this.now = now;
            this.distance = distance;
            this.no = no;
        }

        @Override
        public int compareTo(Guest o) {
            if (this.now.r == o.now.r) {
                return this.now.c - o.now.c;
            }
            return this.now.r - o.now.r;
        }
    }

    static class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public int distanceTo(Point end) {
            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(this.r, this.c));
            boolean[][] visited = new boolean[N + 1][N + 1];
            visited[this.r][this.c] = true;

            int distance = 0;
            while (!queue.isEmpty()) {

                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Point now = queue.poll();

                    if (now.r == end.r && now.c == end.c) {
                        return distance;
                    }

                    for (int dir = 0; dir < 4; dir++) {
                        int nr = now.r + mapper[0][dir];
                        int nc = now.c + mapper[1][dir];

                        if (nr < 1 || nr > N || nc < 1 || nc > N) continue;
                        if (guestMap[nr][nc] == WALL || visited[nr][nc]) continue;
                        visited[nr][nc] = true;

                        queue.offer(new Point(nr, nc));
                    }
                }

                distance++;
            }

            return -1;
        }
    }
}