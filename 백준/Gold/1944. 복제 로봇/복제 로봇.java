import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class Main {

    public static int N, M;
    public static final int EMPTY = -1;
    public static final int WALL = -2;
    public static int[][] maze;
    public static final int[][] mapper = {{0, 0, -1, 1}, {-1, 1, 0, 0}};

    static Point start = new Point();
    static List<Node>[] keyAdj;
    static List<Point> keys = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // input, init
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        maze = new int[N][N];
        keyAdj = new ArrayList[M + 1]; // M + 시작점

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                char ch = line.charAt(j);

                if (ch == '1') {
                    maze[i][j] = WALL;
                    continue;
                }

                if (ch == '0') {
                    maze[i][j] = EMPTY;
                    continue;
                }

                if (ch == 'K') {
                    maze[i][j] = keys.size() + 1; // 키 인덱스 0~M을 직접 표시
                    keys.add(new Point(i, j));
                    continue;
                }

                if (ch == 'S') {
                    maze[i][j] = 0;
                    start = new Point(i, j);
                }
            }
        }

        keys.add(0, start);

        for (int i = 0; i < M + 1; i++) {
            keyAdj[i] = new ArrayList<>();
        }

        // create graph by bfs
        for (int i = 0; i < M + 1; i++) {
            createAdjByBFS(i);
        }


//        System.out.println();
//        for (int i = 0; i < M + 1; i++) {
//            System.out.println("i = " + i);
//            for (Node node : keyAdj[i]) {
//                System.out.println("node.no = " + node.no);
//                System.out.println("node.weight = " + node.weight);
//            }
//        }

        // mst prim
        boolean[] connected = new boolean[M + 1];
        int connectedCount = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.weight));
        queue.offer(new Node(0, 0));

        int total = 0;
        while (!queue.isEmpty()) {
            Node now = queue.poll();

            if (connected[now.no]) continue;
            connected[now.no] = true;
            total += now.weight;
            connectedCount++;

            if (connectedCount == M + 1) {
                System.out.print(total);
                return;
            }

            for (Node next : keyAdj[now.no]) {
                if (connected[next.no]) continue;
                queue.offer(next);
            }
        }

        System.out.print(-1);
    }

    // Key -> Other Keys 거리 찾아서 인접 그래프 생성
    private static void createAdjByBFS(int nowKeyNo) {

        boolean[][] visited = new boolean[N][N];
        Queue<Point> queue = new LinkedList<>();

        Point start = keys.get(nowKeyNo);
        visited[start.y][start.x] = true;
        queue.offer(start);

        int move = 1;
        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Point now = queue.poll();

                for (int dir = 0; dir < 4; dir++) {
                    int ny = now.y + mapper[0][dir];
                    int nx = now.x + mapper[1][dir];

                    if (ny < 0 || ny >= N || nx < 0 || nx >= N) continue;
                    if (visited[ny][nx]) continue;
                    if (maze[ny][nx] == WALL) continue;

                    visited[ny][nx] = true;
                    queue.offer(new Point(ny, nx));

                    if (maze[ny][nx] >= 0) {
                        int keyNo = maze[ny][nx];
                        keyAdj[nowKeyNo].add(new Node(keyNo, move));
                    }
                }
            }
            move++;
        }
    }

    static class Point {
        int y;
        int x;

        public Point() {}

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class Node {
        int no;
        int weight;

        public Node(int no, int weight) {
            this.no = no;
            this.weight = weight;
        }
    }
}
/**
 * 미로에 흩어진 열쇠들을 모두 찾는 것
 *
 * 열쇠 위치와 로봇 시작 위치에 복제 가능
 * 각 위치에 대해 그래프를 만들기 위해 depth별 bfs를 한다.
 *
 */