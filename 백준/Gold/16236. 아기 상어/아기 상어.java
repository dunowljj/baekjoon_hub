import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int[][] blocks;
    static PriorityQueue<int[]> nexts = new PriorityQueue(new Comparator<int[]>() {

        // 비교 우선순위 1)거리 2)위에 있는것 3)왼쪽에 있는 것
        @Override
        public int compare(int[] o1, int[] o2) {
            // 거리가 같다면 x좌표(행)로 비교
            if (o1[2] == o2[2]) {
                // 행이 같다면 y좌표(열)로 비교
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
            return o1[2] - o2[2];
        }
    });

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        blocks = new int[N][N];
        int[] start = null;
        boolean canEat = false;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                blocks[i][j] = Integer.parseInt(st.nextToken());

                if (blocks[i][j] == 9) {
                    start = new int[]{i, j};
                    blocks[i][j] = 0;

                } else if (blocks[i][j] == 1) {
                    canEat = true;
                }
            }
        }

        if (!canEat) {
            bw.write("0");
        } else {
            bw.write(bfs(start)+"");
        }

        bw.flush();
        bw.close();
    }

    static int bfs(int[] start) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0],start[1],0});

        int[][] mapper = {{-1, 0, 0, 1}, {0, -1, 1, 0}};
        int[] feed = new int[]{0,0,0};

        int sharkSize = 2;
        int eatCount = 0;

        boolean[][] visited;
        while (true) {

            visited = new boolean[N][N];

            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                visited[curr[0]][curr[1]] = true;

                for (int j = 0; j < 4; j++) {
                    int nx = curr[0] + mapper[0][j];
                    int ny = curr[1] + mapper[1][j];
                    int distance = curr[2];
                    if (nx < 0 || ny < 0 || nx >= N || ny >= N ||
                            blocks[nx][ny] > sharkSize || visited[nx][ny]
                    ) continue;

                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny, distance + 1});

                    if (blocks[nx][ny] < sharkSize && blocks[nx][ny] != 0) {
                        nexts.offer(new int[]{nx, ny, distance + 1});
                    }
                }
            }

            if (!nexts.isEmpty()) {
                feed = nexts.poll();

                nexts.clear();

                blocks[feed[0]][feed[1]] = 0;

                queue.offer(feed);

                eatCount++;

                if (sharkSize == eatCount) {
                    eatCount = 0;
                    sharkSize++;
                }
            } else {
                return feed[2];
            }
        }
    }


    private static void test() {
        for (int k = 0; k < N; k++) {
            for (int l = 0; l < N; l++) {
                System.out.print(blocks[k][l]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean canEat(int sharkSize) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] < sharkSize) {
                    return true;
                }
            }
        }
        return false;
    }

}

/*
##시간
노드 개수 N제곱, 최대 이동거리 38


 */