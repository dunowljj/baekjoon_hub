import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import static java.util.Comparator.*;

public class Main {

    static class Cell {
        int y;
        int x;
        int rupee;

        public Cell(int y, int x, int rupee) {
            this.y = y;
            this.x = x;
            this.rupee = rupee;
        }

        public int getRupee() {
            return rupee;
        }
    }

    static int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String line;
        boolean[][] visited;

        int N;
        int pNum = 1;
        while ((N = Integer.parseInt(br.readLine())) != 0) {
            int[][] cave = new int[N][N];
            int[][] d = new int[N][N];
            for (int i = 0; i < d.length; i++) {
                Arrays.fill(d[i], Integer.MAX_VALUE);
            }

            visited = new boolean[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    cave[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            PriorityQueue<Cell> pq = new PriorityQueue<>(comparingInt(Cell::getRupee));
            pq.offer(new Cell(0, 0, cave[0][0]));
            visited[0][0] = true;

            while (!pq.isEmpty()) {
                Cell cell = pq.poll();
                visited[cell.y][cell.x] = true;

                int nx, ny;
                for (int i = 0; i < 4; i++) {
                    ny = cell.y + mapper[0][i];
                    nx = cell.x + mapper[1][i];

                    if (ny < 0 || ny >= N || nx < 0 || nx >= N || visited[ny][nx]) continue;

                    if (d[ny][nx] > cell.rupee + cave[ny][nx]) {
                        d[ny][nx] = cell.rupee + cave[ny][nx];
                        pq.offer(new Cell(ny, nx, d[ny][nx]));
                    }
                }
            }

            System.out.println("Problem " + pNum + ": " + d[N - 1][N - 1]);
            pNum++;
        }


    }
}
