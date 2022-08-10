import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[][] board;
    static boolean[] visited;
    static int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};
    static int R;
    static int C;
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        board = new int[R][C];
        visited = new boolean[26];

        for (int i = 0; i < R; i++) {
            String input = br.readLine();
            for (int j = 0; j < C; j++) {
                board[i][j] = input.charAt(j) - 'A';
            }
        }

        visited[board[0][0]] = true;
        dfs(0,0,1);

        bw.write(max+"");
        bw.flush();
        bw.close();
    }

    static void dfs(int x, int y, int depth) {
        if (depth > max) {
            max = depth;
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + mapper[0][i];
            int ny = y + mapper[1][i];

            if (nx < 0 || ny < 0 || nx >= R || ny >= C) continue;

            if (!visited[board[nx][ny]]) {
                visited[board[nx][ny]] = true;
                dfs(nx,ny,depth + 1);
                visited[board[nx][ny]] = false;
            }
        }
    }
}
/*
>> 최대한 몇 칸 지날 수 있는가?

dfs  -> 배열로 방문체크, 이전 방문 금지

## 시간 2초
종료조건이 없어서 시간초과? -> 갈곳이 없으면 알아서 멈춰서 괜찮다.
최대 20*20 크기의 보드


 */