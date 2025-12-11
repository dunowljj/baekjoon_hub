import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";

    static int N,M,K;
    static boolean[][] visited;
    static int group = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        visited = new boolean[M][N];

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());

            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            for (int y = y1; y < y2; y++) {
                for (int x = x1; x < x2; x++) {
                    visited[y][x] = true;
                }
            }
        }

        List<Integer> sizes = new ArrayList<>();
        for (int y = 0; y < M; y++) {
            for (int x = 0; x < N; x++) {
                if (visited[y][x]) continue;

                group++;
                sizes.add(bfs(y, x));
            }
        }

        Collections.sort(sizes);

        StringBuilder answer = new StringBuilder();
        answer.append(group).append(System.lineSeparator());
        for (int size : sizes) {
            answer.append(size).append(SPACE);
        }

        System.out.print(answer.toString().trim());
    }

    static final int[][] mapper = {{0, 0, 1, -1}, {-1, 1, 0, 0}};
    private static int bfs(int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        visited[i][j] = true;
        int count = 1;

        while (!queue.isEmpty()) {
            int[] now = queue.poll();

            int y = now[0];
            int x = now[1];

            for (int k = 0; k < 4; k++) {
                int ny = y + mapper[0][k];
                int nx = x + mapper[1][k];

                if (ny < 0 || ny >= M || nx < 0 || nx >= N) continue;
                if (visited[ny][nx]) continue;
                visited[ny][nx] = true;
                count++;

                queue.offer(new int[]{ny, nx});
            }
        }

        return count;
    }
}
