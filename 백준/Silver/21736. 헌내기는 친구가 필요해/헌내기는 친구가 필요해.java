import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static char[][] campus;
    static int N, M, answer;

    static int[][] mapper = {{-1, 0, 0, 1}, {0, -1, 1, 0}};

    static int[] start = new int[2];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        campus = new char[N][M];
        answer = 0;


        for (int i = 0; i < N; i++) {
            String row = br.readLine();
            for (int j = 0; j < M; j++) {
                char now = row.charAt(j);
                campus[i][j] = now;

                if (now == 'I') start = new int[]{i, j};
            }
        }

        bfs();
        if (answer == 0) System.out.print("TT");
        else System.out.print(answer);
    }

    private static void bfs() {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        visited[start[0]][start[1]] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int[] poll = queue.poll();

            int y = poll[0];
            int x = poll[1];

            for (int i = 0; i < 4; i++) {
                int ny = y + mapper[0][i];
                int nx = x + mapper[1][i];

                if (ny >= N || ny < 0 || nx >= M || nx < 0 || visited[ny][nx]) continue;
                if (campus[ny][nx] == 'X') continue;
                if (campus[ny][nx] == 'P') answer++;

                visited[ny][nx] = true;

                queue.offer(new int[]{ny, nx});
            }
        }
    }
}
