import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] nightMoveMapX = {1,1,-1,-1,2,-2,2,-2};
    static int[] nightMoveMapY = {2,-2,2,-2,1,1,-1,-1};
    static int tx;
    static int ty;
    static boolean[][] visited;
    static StringBuilder answer = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int caseNum = Integer.parseInt(br.readLine());

        for (int i = 0; i < caseNum; i++) {
            int I = Integer.parseInt(br.readLine());

            visited = new boolean[I][I];

            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            tx = Integer.parseInt(st.nextToken());
            ty = Integer.parseInt(st.nextToken());

            bfs(I, x, y, 0);
        }

        bw.write(answer.toString().trim());
        bw.flush();
        bw.close();
    }

    static void bfs(int I, int x, int y, int d) {
        Queue<int[]> queue = new LinkedList();
        queue.add(new int[]{x, y, d});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            x = curr[0];
            y = curr[1];
            d = curr[2];

            if (x == tx && y == ty) {
                answer.append(d).append("\n");
                return;
            }

            for (int i = 0; i < 8; i++) {
                int nx = x + nightMoveMapX[i];
                int ny = y + nightMoveMapY[i];
                int nd = d + 1;

                if (nx < 0 || nx >= I || ny < 0 || ny >= I) {
                    continue;
                }

                if (!visited[nx][ny]) {
                    queue.add(new int[]{nx, ny, nd});
                    visited[nx][ny] = true;
                }
            }
        }
    }
}
/*
체스판 배열이 꼭 필요한가? yes -> 개수 세기?.. -> 체스판 없이 I 길이만 사용하고, 배열에 깊이를 저장
각칸을 두수의 쌍 0 ~ l-1 ->  배열 인덱스와 같다.

 */