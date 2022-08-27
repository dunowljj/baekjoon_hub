import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static boolean[][] visited;
    static char[][] grid;
    static int groupCount = 0;
    static int N;
    static int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        visited = new boolean[N][N];
        grid = new char[N][N];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < N; j++) {
                grid[i][j] = input.charAt(j);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    groupCount++;
                    bfs(i, j);
                }
            }
        }
        bw.write(groupCount+" ");

        visited = new boolean[N][N];
        groupCount = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    groupCount++;
                    bfsWeak(i, j);
                }
            }
        }

        bw.write(groupCount+"");
        bw.flush();
        bw.close();
    }

    static void bfs(int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0];
            int y = curr[1];

            for (int k = 0; k < 4; k++) {
                int nx = x + mapper[0][k];
                int ny = y + mapper[1][k];

                // 인덱스 초과 || 방문했거나 || 다음값과 다른 경우 넘어감
                if (nx < 0 || ny < 0 || nx >= N || ny >= N
                || visited[nx][ny] || grid[x][y] != grid[nx][ny]) continue;

                visited[nx][ny] = true;
                queue.offer(new int[]{nx, ny});
            }
        }
    }

    private static void bfsWeak(int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{i, j});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0];
            int y = curr[1];

            for (int k = 0; k < 4; k++) {
                int nx = x + mapper[0][k];
                int ny = y + mapper[1][k];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N
                        || visited[nx][ny]) continue;

                if (grid[x][y] == grid[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny});
                }

                // R-G, G-R 은 같은 색으로 취급하여 그룹핑
                if (grid[x][y] == 'R' && grid[nx][ny] == 'G'
                        || grid[x][y] == 'G' && grid[nx][ny] == 'R') {
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
    }

}
/*
## 시간복잡도
bfs -> V + E  -> (N^2 + N^2) * 2 -> 4N^2
O(N^2) -> 100 * 100

## 풀이
RG를 다른 구역으로 봤을때의 구역의 수, 같은 구역으로 봤을때의 구역의 수

배열순회하며 방문하지 않은 곳 찾기 -> 방문체크하면서 bfs로 주변 탐색 -> 탐색할 곳이 더 없으면 구역의 수 카운트 -> 배열 마저 순회
한번에 색약인 경우와 아닌 경우를 모두 체크하는 방법? -> 큐 두 개? 방문배열도 두개?..

방법1 그리드를 처음에 두개 만든다. 하나는 입력과 같은 그리드 하나는 R,G를 통일한 그리드
방법2 bfs를 두 가지 만든다.
 */
