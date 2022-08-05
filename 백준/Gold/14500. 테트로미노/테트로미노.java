import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int row;
    static int col;
    static int[][] paper;
    static int max = Integer.MIN_VALUE;
    static int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};

    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());

        visited = new boolean[row][col];

        paper = new int[row][col];
        for (int i = 0; i < row; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < col; j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                visited[i][j] = true;
                dfs(1, paper[i][j], i, j);
                visited[i][j] = false;
            }
        }

        bw.write(max+"");
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, int sum, int x, int y) {
        if (depth == 4) {
            if (max < sum) {
                max = sum;
            }
            return;
        }

        for (int i = 0; i < 4; i++) {

            int nx = x + mapper[0][i];
            int ny = y + mapper[1][i];

            // 인덱스 검사
            if (nx < 0 || ny < 0 || nx >= row || ny >= col) continue;


            if (!visited[nx][ny]) {

                if (depth == 2) {
                    visited[nx][ny] = true;
                    dfs(depth + 1, sum + paper[nx][ny], x,y);
                    visited[nx][ny] = false;
                }

                visited[nx][ny] = true;
                dfs(depth + 1, sum + paper[nx][ny], nx, ny);
                visited[nx][ny] = false;

            }
        }

    }

}
/*
인접한 칸으로 이동하면서 합을 검사하기. 4번만 이동해야 한다.

최대 500 * 500 -> 25만칸, 1000 이하의 자연수 -> 최대 합 2.5억

25만개 인덱스마다 탐색하면? 이전위치를 방문하지 않는다면, 겹치는 도형 없다.

이전 방문을 막는 dfs를 사용하면, ㅗㅓㅏㅜ 모양이 나오지 못한다. -> 방문 여부를 체크하고, 깊이가 2일때 탐색을 추가한다.
 */