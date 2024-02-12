import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int N;
    private static int M;
    private static final int SEA = 0;
    private static final int LAND = 1;
    private static int seq = 1;
    private static final int[][] mapper = {{0, 0, -1, 1}, {1, -1, 0, 0}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // init
        int[][] grid = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 섬 구분
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == LAND) {
                    seq++;
                    distinguishIslands(grid, i, j);
                }
            }
        }

        List<Bridge>[] adjList = new ArrayList[seq + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        // 다리 정보 인접리스트에 초기화
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] > LAND) {
                    findBridge(grid, i, j, adjList);
                }
            }
        }

        // 프림
        PriorityQueue<Bridge> queue = new PriorityQueue<>(Comparator.comparingInt(Bridge::getLen));
        queue.offer(new Bridge(2, 0)); // seq는 2부터 존재한다.
        boolean[] visited = new boolean[seq + 1];
        int len = 0;
        while (!queue.isEmpty()) {
            Bridge now = queue.poll();
            if (visited[now.end]) continue;
            visited[now.end] = true;
            len += now.len;

            for (Bridge next : adjList[now.end]) {
                queue.offer(next);
            }
        }

//        printAdjList(adjList);

        if (allConnected(visited)) {
            System.out.print(len);
        } else {
            System.out.print("-1");
        }
    }

    private static void printAdjList(List<Bridge>[] adjList) {
        for (int i = 0; i < N; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < adjList[i].size(); j++) {
                System.out.print(adjList[i].get(j).end + " ");
            }
            System.out.println();
        }
    }

    private static boolean outOfRange(int ny, int nx) {
        return ny < 0 || ny >= N || nx < 0 || nx >= M;
    }

    private static void distinguishIslands(int[][] grid, int y, int x) {
        boolean[][] visited = new boolean[N][M];
        Queue<Point> queue = new LinkedList<>();

        queue.offer(new Point(y, x));
        visited[y][x] = true;
        grid[y][x] = seq;

        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];

                if (outOfRange(ny, nx) || grid[ny][nx] != LAND) continue;

                grid[ny][nx] = seq;
                queue.offer(new Point(ny, nx));
            }
        }
    }

    private static void findBridge(int[][] grid, int y, int x, List<Bridge>[] adjList) {
        int startNo = grid[y][x];

        // 4방향 직선 탐색
        for (int i = 0; i < 4; i++) {

            int len = 0;
            int ny = y + mapper[0][i];
            int nx = x + mapper[1][i];

            // 바다인 경우 탐색한다.
            while (!outOfRange(ny, nx) && grid[ny][nx] == SEA) {
                len++;
                ny += mapper[0][i];
                nx += mapper[1][i];
            }

            // 길이 2 이상, 다른 섬을 만났을때 -> 다리 정보 인접리스트에 추가
            if (!outOfRange(ny, nx) && len >= 2 && grid[ny][nx] > LAND && grid[ny][nx] != startNo) {
                int endNo = grid[ny][nx];
                adjList[startNo].add(new Bridge(endNo, len));
            }
        }
    }

    private static boolean allConnected(boolean[] visited) {
        for (int i = 2; i <= seq; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }

    static class Point {
        int y;

        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class Bridge {
        int end;

        int len;

        public Bridge(int end, int len) {
            this.end = end;
            this.len = len;
        }

        public int getLen() {
            return len;
        }
    }
}
/**
 * N,M 1~10
 * 너비 3~100
 * 섬 2~6
 * -> 각 위치에서 다리 길이 탐색 30만 훨씬 미만??
 * 다리
 * - 한 다리의 방향이 중간에 바뀌면 안된다.
 * - 다리의 양 끝은 섬과 인접한 바다 위에 있어야
 * - 다리가 겹칠 수 있나? 예시를 보니 가능하다.
 * - 다리 길이 2 이상이어야 한다.
 *
 * 풀이
 * 1.탐색을 통해 직접 다리 정보를 구해야한다.
 * - 양의 정수로 섬을 구분해서 배열에 표시하기
 * - 전체 배열을 순회하면서 4방향 탐색
 *   - 각 섬의 둘레에서 다리 탐색을 출발시켜야한다.
 *   - 같은 섬을 만나면 종료, 바다면 탐색, 다른 섬이면 다리 정보 생성
 *
 * 2. 프림알고리즘 사용
 * - 인접리스트에 다리 정보를 등록한다.
 * - 모두 연결되면 종료한다.
 *
 *
 * 연결이 안되는 경우가 있나?
 */
