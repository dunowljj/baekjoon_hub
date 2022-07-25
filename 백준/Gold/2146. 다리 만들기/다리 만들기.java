import java.io.*;
import java.util.*;

public class Main {
    static int[][] mapper = {{-1, 1, 0, 0}, {0, 0, -1, 1}};
    static int[][] area;
    static int islandNum = 1;
    static List<int[]> islandLocation = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        area = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                area[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 모든 지역을 탐색하면서 섬마다 다른 번호 매기기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 특정 섬에 첫 방문시 섬 번호 생성하기
                if (area[i][j] == 1) {
                    islandNum++;
                    bfs(i,j,N);
                }
            }
        }

        /*
        섬번호를 이용해 모든 거리를 비교하여 최솟값 구하기 4중 포문은 좀..
        거리를 미리 구하기는 힘들어 보인다.
        애초에 좌표를 섬 번호와 함께 저장해놓고, 비교한다?
        어디에 저장할 것인가? int배열 리스트?
         */
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < islandLocation.size(); i++) {
            for (int j = 0; j < islandLocation.size(); j++) {
                int[] island1 = islandLocation.get(i);
                int[] island2 = islandLocation.get(j);
                int x = island1[1]; int y = island1[2];
                int nx = island2[1]; int ny = island2[2];

                if (island1[0] != island2[0]) {
                    min = Math.min(min, (Math.abs(x - nx) + Math.abs(y - ny)));
                }
            }
        }
        min--;

        bw.write(min+"");
        bw.flush();
        bw.close();
    }
    static void bfs(int x, int y, int N) {
        Queue<int[]> queue = new LinkedList();
        area[x][y] = islandNum;
        islandLocation.add(new int[]{islandNum, x, y});

        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            x = curr[0];
            y = curr[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + mapper[0][i];
                int ny = y + mapper[1][i];

                // 인덱스 범위 확인
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

                // 이웃 좌표가 방문한 적 없는 육지일 경우 섬에 번호 매기기
                if (area[nx][ny] == 1) {
                    area[nx][ny] = islandNum;
                    islandLocation.add(new int[]{islandNum, nx, ny});

                    queue.add(new int[]{nx, ny});
                }
            }
        }
    }
}
/*
1. BFS를 통해 탐색하여 같은 섬끼리 표시를 한다. -> int 변수로 해당 섬을 갱신 후, ++해주어 다른 섬과 차이를 둔다.
2. 섬 배열 전체를 돌면서 좌표 하나에 대한 다른 섬들의 거리(x,y좌표 차의 합)들의 최솟값을 구한다. N의 3제곱 연산 100의 3승
 */