import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static final int[][] mapper = {{-1, 1, 0, 0}, {0, 0, 1, -1}};
    public static final int WATER = 0;
    private static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int totalIceSpaceCount = 0;
        int[][] iceHeight = new int[N][M];
        int[][] willMelt = new int[N][M];
        Queue<Point> queue = new LinkedList<>(); // 모든 빙산위치 저장
        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < M; x++) {
                int ice = Integer.parseInt(st.nextToken());

                if (ice > 0) {
                    iceHeight[y][x] = ice;
                    queue.add(new Point(y, x));
                    totalIceSpaceCount++;
                }
            }
        }

        int answer = 0;
        int time = 0;
        while (!queue.isEmpty()) {
            boolean someIceDie = false;
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Point now = queue.poll();

                int waterCount = 0;
                for (int dir = 0; dir < 4; dir++) {
                    int ny = now.y + mapper[0][dir];
                    int nx = now.x + mapper[1][dir];

                    if (ny < 0 || ny >= N || nx < 0 || nx >= M) continue;
                    if (iceHeight[ny][nx] == WATER) waterCount++;
                }


               // 다녹게 될 빙산도 queue에 넣어야함. melt할때 다 녹은거 빼버리기
                if (iceHeight[now.y][now.x] <= waterCount) {
                    someIceDie = true;
                    totalIceSpaceCount--;
                }

                willMelt[now.y][now.x] = waterCount;
                queue.offer(now);
            }

            melt(iceHeight, willMelt,  queue); // 여기서 다 녹은 빙산을 큐에서 뺀다.
            time++;

//            printSnapshot(iceHeight, totalIceSpaceCount, time);

            // 다 녹은 빙산이 존재할때
            if (someIceDie) {

                // 동시에 다녹음
                if (queue.isEmpty()) {
                    answer = 0;
                    break;
                }

                // 분리됨이 확인됨
                if (!queue.isEmpty() && isSeperated(iceHeight, queue, totalIceSpaceCount)) {
                    answer = time;
                    break;
                }
            }
        }

        System.out.print(answer);
    }

    private static void melt(int[][] sea, int[][] willMelt, Queue<Point> queue) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            Point p = queue.poll();

            int afterMelt = Math.max(0, sea[p.y][p.x] - willMelt[p.y][p.x]);
            sea[p.y][p.x] = afterMelt;
            willMelt[p.y][p.x] = 0;

            if (afterMelt != 0) queue.add(p);
        }
    }

    private static boolean isSeperated(int[][] sea, Queue<Point> queue, int totalIceSpaceCount) {
        Point point = queue.peek();
        int SequencedIceCount = countSeqIceByBFS(sea, point);

        return totalIceSpaceCount > SequencedIceCount;
    }

    private static int countSeqIceByBFS(int[][] sea, Point point) {
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];

        visited[point.y][point.x] = true;
        queue.offer(point);

        int iceSpaceCount = 1;
        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int dir = 0; dir < 4; dir++) {
                int ny = now.y + mapper[0][dir];
                int nx = now.x + mapper[1][dir];

                if (ny < 0 || ny >= N || nx < 0 || nx >= M) continue;
                if (sea[ny][nx] == WATER || visited[ny][nx]) continue;

                iceSpaceCount++;
                visited[ny][nx] = true;
                queue.offer(new Point(ny, nx));
            }
        }

        return iceSpaceCount;
    }

    private static void printSnapshot(int[][] sea, int totalIce, int time) {
        System.out.println("time:"+time+", totalIce:"+totalIce);
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                System.out.print(sea[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}

/**
 * 각 칸에 들어가는 값 0~10
 * N,M 3~300
 *
 * 빙산은 한 덩어리 주어진다.
 *
 * ### 시간복잡도
 *
 * 최대 9만칸 -> 모두 10씩값을 가진다면, 90만번 탐색?
 * 10 10 10
 * 10 10 10
 * 10 10 0
 *
 * +10초
 * 10 10 10
 * 10 10 0
 * 10 0 0
 *
 * + 10초
 * 10 5 0
 *  5 5 0
 *  0 0 0
 *
 * +5초
 * 10 0 0
 *  0 0 0
 *  0 0 0
 *
 * 모두 녹이는데 처음 10초 + (15초 * 대각선 길이) 정도 걸리는 듯)
 * 1이상 정수가 들어가는 칸의 개수가 10000개 이하
 * 대각선도 해봐야 100 -> 1500번?
 *
 * bfs로 분리 확인 시 만번이면 되고, 1초씩 녹이도록 구현하더라도 1500회면 된다.
 * 대략 최악 1500만번 연산
 *
 * ### 풀이
 *
 * 1 2차원 배열 초기화, 큐에 빙산 위치 모두 넣기, 빙산이 있는 곳의 개수 세기
 * 2 주변 0 개수 모두 세기, 녹은 횟수 적용은 나중에 한번에 -> 0 세고 바로 녹이면 다른 값에 영향이 감
 * 3 빙산의 위치는 계속해서 큐에 그대로 넣어주되, 다 녹은 빙산은 넣지 않는다.
 * 4 완전히 녹은 빙산이 발견되었다면, 아무 빙산을 뽑아 BFS로 인접 연속된 빙산 수를 구한다. 처음에 구한 빙산이 있는 곳의 개수와 비교해서 분리됐는지를 판별
 *
 */