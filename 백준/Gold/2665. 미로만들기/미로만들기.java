import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static class Cell {
        int y;
        int x;
        int blackCount;

        public Cell(int y, int x, int blackCount) {
            this.y = y;
            this.x = x;
            this.blackCount = blackCount;
        }

        public int getBlackCount() {
            return blackCount;
        }
    }

    public static final int WHITE_ROOM = 0;
    public static final int BLACK_ROOM = 1;
    private static final int mapper[][] = {{-1, 1, 0, 0}, {0, 0, 1, -1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[][] maze = new int[n][n];
        boolean[][] visited = new boolean[n][n];

        // 거리 배열 최댓값으로 초기화
        int[][] d = new int[n][n];
        for (int i = 0; i < d.length; i++) {
            Arrays.fill(d[i], Integer.MAX_VALUE);
        }
        d[0][0] = 0;

        // 미로 흰/검은 방 여부 초기화
        for (int i = 0; i < n; i++) {
            String row = br.readLine();

            // 계산 편의를 위해 검은방을 1, 흰방을 0으로 초기화
            for (int j = 0; j < n; j++) {
                if (row.charAt(j)  == '1') maze[i][j] = WHITE_ROOM;
                else maze[i][j] = BLACK_ROOM;
            }
        }

        PriorityQueue<Cell> pq = new PriorityQueue<>(comparingInt(Cell::getBlackCount));
        pq.offer(new Cell(WHITE_ROOM, WHITE_ROOM, WHITE_ROOM));

        while (!pq.isEmpty()) {
            Cell now = pq.poll();
            visited[now.y][now.x] = true;

            int ny, nx;
            for (int i = 0; i < 4; i++) {
                ny = now.y + mapper[0][i];
                nx = now.x + mapper[1][i];

                // 방문체크를 하는게 맞는가? 가장적은 black을 가진게 먼저 실행될텐데, 방문이 꼬이지 않을까?
                // 어짜피 최소경로를 먼저 탐색한다. 그리고 중간에 다른 경로가 있다면 알아서 탐색하기때문에, 방문체크때문에 최소경로를 탐색하지 못할 경우는 없다.
                if (ny < 0 || ny >= n || nx < 0 || nx >= n|| visited[ny][nx]) continue;

                // 다음 방이 검은 방이면 1 추가해서 계산, 흰방이면 0 추가해서 계산한다.
                if (d[ny][nx] > d[now.y][now.x] + maze[ny][nx]) {
                    d[ny][nx] = d[now.y][now.x] + maze[ny][nx];
                    pq.offer(new Cell(ny, nx, d[ny][nx]));
                }
            }
        }
        System.out.print(d[n - 1][n - 1]);
    }
}

/**
 * 검은 방을 지날때마다 개수를 카운트한다.
 * 검은 방 개수가 가장 적은 경로를 구하는 다익스트라 알고리즘을 실행시킨다.
 * 시작방은 항상 흰 색이다.
 */