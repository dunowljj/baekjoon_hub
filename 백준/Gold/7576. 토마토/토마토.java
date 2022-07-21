import java.io.*;
import java.util.*;

public class Main {
    static int[][] tomatoBox;
    static int[][] mapper = {{1,0,-1,0}, {0,1,0,-1}};
    static int M;
    static int N;
    static int becomeRipeCount = 0;
    static int unripeCount = 0;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        Queue<int[]> queue = new LinkedList<>();
        tomatoBox = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int status = Integer.parseInt(st.nextToken());
                tomatoBox[i][j] = status;

                if (status == 0) {
                    unripeCount++;
                } else if (status == 1) {
                    queue.add(new int[]{i,j,0});
                }
            }
        }
        int sum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (tomatoBox[i][j] == 1) {
                    sum += bfs(queue);
                }
            }
        }

        if (becomeRipeCount == unripeCount) {
            sb.append(sum+"");
        } else {
            sb.append("-1");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    static int bfs(Queue<int[]> queue) {
        int depth = 0;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = curr[0] + mapper[0][i];
                int ny = curr[1] + mapper[1][i];
                int nd = curr[2] + 1;


                if (0 <= nx && nx < N && 0 <= ny && ny < M) {
                    if (tomatoBox[nx][ny] == 0) {
                        tomatoBox[nx][ny] = 1;
                        queue.add(new int[]{nx, ny, nd});
                        becomeRipeCount++;
                        depth = nd;
                    }
                }
            }
        }
        return depth;
    }

}
/*
대각선 토마토에는 영향을 주지 못한다. 혼자 저절로 익지 않는다. -> 익은 토마토의 인접한 토마토들만 익는다.
하루 지나면 인접 토마토가 익는다.

며칠이 지나면 토마토들이 모두 익는지 최소 일수 구하기.

2 <= N, M <= 1000

-1 빈칸, 0 안익음, 1 익음
모두 익지 못하면 -1

문제는 모두 익지 못하는지 익는지 체크하는 것인데, 처음에 배열을 만들면서 0의 개수를 세고, 값을 고칠때마다 카운트를 추가해서 0 개수와 비교한다.


*문제 : 익는 것은 모든 1부터 동시에 시작된다. 예시로 양쪽끝에 1이 있다면, 1이 하나일때보다 시간이 절반이 걸릴 것이다.
-> 해결 : 탐색을 동시에 해야한다. 미리 큐에 1인 좌표들을 모두 넣어놓고, bfs탐색을 시작한다.
 */