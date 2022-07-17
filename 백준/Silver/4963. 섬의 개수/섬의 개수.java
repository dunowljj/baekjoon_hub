import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[][] map;
    static boolean[][] visited;
    static int[] idxMapper = {-1, 0, 1};

    static int h;
    static int w;
    static int islandCount = 0;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        map = new int[50][50];
        StringTokenizer st;
        boolean noIsland = true;


        while(true) {
            st = new StringTokenizer(br.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            // 0 0 나오면 탈출
            if (w == 0 && h == 0) break;

            // map에 값 입력
            for (int i = 0; i < h; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < w; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            // while 순회마다 초기화
            visited = new boolean[h][w];

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (map[i][j] == 1 && !visited[i][j]) {
                        dfs(i, j);
                        islandCount++;
                        noIsland = false;
                    }
                }
            }
            if (noIsland) {
                sb.append(0).append("\n");
            } else {
                sb.append(islandCount).append("\n");
                islandCount = 0;
            }

        }

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
    static void dfs(int x, int y) {
        visited[x][y] = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int nx = x + idxMapper[i];
                int ny = y + idxMapper[j];

                if (0 <= nx && nx < h && 0 <= ny && ny < w) {
                    if (!visited[nx][ny] && map[nx][ny] == 1) {
                        dfs(nx, ny);
                    }
                }
            }
        }
    }
}

/*
가로, 세로, 대각선까지 이동 가능
너비, 높이 <= 50
테스트케이스의 개수 모름. 여러개.

for문 순회 -> 섬 발견 시 dfs로 주변 모두 탐색 -> 발견한 섬 방문체크 -> 섬 개수를 세서 append후 초기화

섬이 0개일때 -> 아예 값이 생기지 않음
덩어리 개수를 세야한다.
*/