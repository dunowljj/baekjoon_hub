import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static boolean[][] needSearch;
    static int h;
    static int w;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 최대 50, idx편의를 위해 +2하려 배열의 둘레에 0을 한 줄 생성
        needSearch = new boolean[52][52];
        StringTokenizer st;
        boolean noIsland = true;
        int islandCount = 0;
        int[] idxMapper = {-1, 0, 1};

        while(true) {
            st = new StringTokenizer(br.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            // 0 0 나오면 탈출
            if (w == 0 && h == 0) break;

            /*
            boolean으로 변환 1 -> true(방문x), 0 -> false(방문o)
            boolean은 false가 초깃값인데, 초기화시 둘레 값은 false가 된다.
            둘레는 방문해서는 안되므로, false인 값을 방문하지 않도록 설정해야 한다.
            논리적으로; 탐색이 필요하다(true) -> 방문(dfs) / 탐색이 필요없다(false) -> 방문 x
             */
            for (int i = 1; i <= h; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= w; j++) {
                    needSearch[i][j] = Integer.parseInt(st.nextToken()) == 1;
                }
            }

            for (int i = 1; i <= h; i++) {
                for (int j = 1; j <= w; j++) {
                    if (needSearch[i][j]) {
                        dfs(i, j, idxMapper);
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
        System.out.print(sb.toString());
    }
    static void dfs(int x, int y, int[] idxMapper) {
        needSearch[x][y] = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int nx = x + idxMapper[i];
                int ny = y + idxMapper[j];

                if (needSearch[nx][ny]) {
                    dfs(nx, ny, idxMapper);
                }
            }
        }
    }
}

/*
가로, 세로, 대각선까지 이동 가능
너비, 높이 <= 50
테스트케이스의 개수 모름. 여러개.

섬이 0개일때 -> 아예 값이 생기지 않음
덩어리 개수를 세야한다.
*/