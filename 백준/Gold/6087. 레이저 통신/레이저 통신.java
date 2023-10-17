import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static class Cell {
        int y;
        int x;
        int mirrorCount;
        int dir;

        public Cell(int y, int x, int mirrorCount, int dir) {
            this.y = y;
            this.x = x;
            this.mirrorCount = mirrorCount;
            this.dir = dir;
        }

        public int getMirrorCount() {
            return mirrorCount;
        }
    }

    public static final int START_DIR = -1;
    private static final int[][] mapper = {{0, 0, START_DIR, 1}, {1, START_DIR, 0, 0}}; // 우, 좌, 상, 하

    private static final char EMPTY = '.';
    private static final char WALL = '*';
    private static final char CELL_NEED_CONNECT = 'C';


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int W = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());

        char[][] grid = new char[H][W];
        boolean[][][] visited = new boolean[H][W][4];

        int[][] minMirrorCounts = new int[H][W];
        for (int i = 0; i < minMirrorCounts.length; i++) {
            Arrays.fill(minMirrorCounts[i], Integer.MAX_VALUE);
        }

        List<int[]> startAndEnd = new ArrayList<>();
        for (int h = 0; h < H; h++) {
            String line = br.readLine();
            for (int w = 0; w < W; w++) {
                char now = line.charAt(w);

                if (now == CELL_NEED_CONNECT) {
                    startAndEnd.add(new int[]{h, w});
                }

                grid[h][w] = now;
            }
        }

        int[] start = startAndEnd.get(0);
        int[] end = startAndEnd.get(1);

        PriorityQueue<Cell> pq = new PriorityQueue<>(comparingInt(Cell::getMirrorCount));
        pq.offer(new Cell(start[0], start[1], -1, START_DIR));
        minMirrorCounts[start[0]][start[1]] = 0;

        while (!pq.isEmpty()) {
            Cell now = pq.poll();

            if (now.dir != START_DIR) visited[now.y][now.x][now.dir] = true;

            // 도착점의 거울 수보다 이미 거울 수가 더 많으면 종료
            if (now.y == end[0] && now.x == end[1]) break;

            int ny;
            int nx;
            for (int dir = 0; dir < 4; dir++) {
                ny = now.y + mapper[0][dir];
                nx = now.x + mapper[1][dir];

                if (outOfIndex(ny, nx, H, W) || visited[ny][nx][dir] || grid[ny][nx] == WALL) continue;

                int mirror = now.dir == dir ? 0 : 1;

                if (minMirrorCounts[ny][nx] >= now.mirrorCount + mirror) {
                    minMirrorCounts[ny][nx] = now.mirrorCount + mirror;
                    pq.offer(new Cell(ny, nx, now.mirrorCount + mirror, dir));

//                    printAll(minMirrorCounts);

                }
            }
        }

//        printAll(minMirrorCounts);

        System.out.print(minMirrorCounts[end[0]][end[1]]);
    }

    private static void printAll(int[][] minMirrorCounts) {
        for (int[] minMirrorCount : minMirrorCounts) {
            for (int i : minMirrorCount) {
                if (i == Integer.MAX_VALUE) System.out.print("- ");
                else System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean outOfIndex(int ny, int nx, int H, int W) {
        return ny < 0 || ny >= H || nx < 0 || nx >= W;
    }
}
