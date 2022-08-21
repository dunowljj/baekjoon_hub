import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class SnapShot {
    int x;
    int y;
    int distance;
    int canBreak;

    public SnapShot(int x, int y, int distance, int canBreak) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.canBreak = canBreak;
    }

}

public class Main {
    static char[][] board;
    static int N;
    static int M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        board = new char[N][M];
        int[][] visited = new int[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = input.charAt(j);

                visited[i][j] = -1;
            }
        }

        bw.write(bfs(K, visited)+"");
        bw.flush();
        bw.close();
    }

    static int bfs(int K, int[][] visited) {
        Queue<SnapShot> queue = new LinkedList();
        queue.add(new SnapShot(0, 0, 1, K));

        int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};
        visited[0][0] = K + 1;

        while (!queue.isEmpty()) {
            SnapShot snapShot = queue.poll();

            int distance = snapShot.distance;

            // 도착시 종료
            if (snapShot.x == N - 1 && snapShot.y == M - 1) {
                return distance;
            }



            for (int i = 0; i < 4; i++) {
                int nx = snapShot.x + mapper[0][i];
                int ny = snapShot.y + mapper[1][i];
                int canBreak = snapShot.canBreak;


                // 인덱스 초과, 기다려도 부술수 없는 경우
                if (nx < 0 || ny < 0 || nx >= N || ny >= M || (board[nx][ny] == '1' && canBreak == 0)) {
                    continue;
                }

                // 한턴 기다린 작업이 부술수 있는 경우가 더 많을 수 있나?

                if (visited[nx][ny] >= canBreak) continue;

                if (board[nx][ny] == '1') {
                    // 부순다고 가정했을때보다 많은 횟수가 남아있다면, 다음번 탐색할 필요가 없다.
                    if (visited[nx][ny] >= canBreak - 1) continue;

                    // 기다리면 부술 수 있는 경우 = 밤일때 제자리에서 기다리는 경우 -> 현재 정보 넣고 바로 다음 탐색
                    if (distance % 2 == 0) {
                        queue.add(new SnapShot(snapShot.x, snapShot.y, distance + 1, canBreak));
                        continue;
                    }

                    // 당장 부수는 경우
                    else {
                        canBreak--;
                    }
                }

                // 0인경우, 당장 부순 경우 추가
                visited[nx][ny] = canBreak;
                queue.add(new SnapShot(nx, ny, distance + 1, canBreak));
            }
        }
        return -1;
    }

}
/*
밤낮을 boolean으로 표현

밤이면 제자리에서 거리만 더하는 경우 추가


다른 풀이
부순개수를 세면서 탐색
1) 부순개수 > K

방문체크 구조를 바꾸면 된다.
거리를 % 연산한다.
 */