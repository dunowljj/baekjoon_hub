import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static boolean[][] visited;
    static char[][] gameBoard;
    static int[][] idxMapper = {{1,0,-1,0}, {0,1,0,-1}};
    static int[] start;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new boolean[N][M];
        gameBoard = new char[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                gameBoard[i][j] = input.charAt(j);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                start = new int[]{i, j};
                visited[i][j] = true;
                dfs(i, j, 0);
            }
        }

        System.out.print("No");
    }
    static void dfs(int x, int y, int depth) {

        for (int i = 0; i < 4; i++) {
            int nx = x + idxMapper[0][i];
            int ny = y + idxMapper[1][i];
            int nd = depth + 1;

            // 점을 이은 것이 4번째 이상이고, 시작점과 좌표가 같을때
            if (start[0] == nx && start[1] == ny && nd >= 4) {
                System.out.print("Yes");
                System.exit(0);
            }

            // 방문 및 인덱스 out시 넘어감 -> 방문여부 뒤로 빼야지 ArrayIndexOutofBoundsException 회피 가능
            if (nx < 0 || ny < 0 || nx >= N || ny >= M || visited[nx][ny]) {
                continue;
            }

            // 비교 기준인 현재 위치와 주변 값의 알파벳 값이 같을 때 다음 깊이 탐색
            if (gameBoard[x][y] == gameBoard[nx][ny]) {
                visited[nx][ny] = true;
                dfs(nx, ny, nd);
                visited[nx][ny] = false;
            }
        }
    }
}
/*
2 <= N,M <= 50

풀이 : 같은 알파벳이 있는곳으로 방문을 체크하면서 탐색한다. 맨 처음 시작점을 저장해서 다시 돌아오는지 확인. 
1) 방문 체크하면서 다음 점으로 이동
2) 방문 이력 무관하게, 최초 시작점을 만나면 싸이클 -> 문제 시작점 다음점이 탐색 시바로 시작점을 싸이클로 판단하게된다.
3) 맨 처음 시작점을 따로 저장하고, depth 제한을 두어 바로 돌아오지 못하게해서 해결 (depth != 1 혹은 depth >= 4)
-> 애초에 탐색을 시작전에 for문으로 시작점을 지정한다. -> 해당 시작점과 좌표가 같으면 싸이클로 판단한다.
4) dfs로 구현, 방문 체크를 하고, 첫 순환에 제한을 두면 될 듯

문제 : 처음에 bfs로 구현했는데, 싸이클이 있어도 시작점으로 돌아오지 못한다. 너비를 우선적으로 탐색하기 때문에,
같은 depth의 탐색들이 서로 갈 수 있는 경로를 방문체크를 해버려서 지나갈 수가 없다.ㄷ 깊이 우선을 써야한다.

 */