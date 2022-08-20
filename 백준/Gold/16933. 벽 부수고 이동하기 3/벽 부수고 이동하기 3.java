import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class SnapShot {
    int x;
    int y;
    int distance;
    int canBreak;
    int stayBit;
    boolean isNight;

    public SnapShot(int x, int y, int distance, int canBreak, int stayBit, boolean isNight) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.canBreak = canBreak;
        this.stayBit = stayBit;
        this.isNight = isNight;
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
        int[][][] visited = new int[2][N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = input.charAt(j);

                visited[0][i][j] = -1;
                visited[1][i][j] = -1;
            }
        }

        bw.write(bfs(K, visited)+"");
        bw.flush();
        bw.close();
    }

    static int bfs(int K, int[][][] visited) {
        Queue<SnapShot> queue = new LinkedList();
        queue.add(new SnapShot(0, 0, 1, K, 0,false));

        int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};
        visited[0][0][0] = K + 1;
        visited[1][0][0] = K + 1;

        while (!queue.isEmpty()) {
            SnapShot snapShot = queue.poll();

            int distance = snapShot.distance;
            int canBreak = snapShot.canBreak;
            boolean isNight = snapShot.isNight;

            // 도착시 종료
            if (snapShot.x == N - 1 && snapShot.y == M - 1) {
                return distance;
            }

            // 밤일때 제자리에서 기다리는 경우 추가
            if (isNight) {
                queue.add(new SnapShot(snapShot.x, snapShot.y, distance + 1, canBreak, 1,false));
            }

            for (int i = 0; i < 4; i++) {
                int nx = snapShot.x + mapper[0][i];
                int ny = snapShot.y + mapper[1][i];
                canBreak = snapShot.canBreak;
                int stayBit = snapShot.stayBit;
                if (nx < 0 || ny < 0 || nx >= N || ny >= M || (board[nx][ny] == '1' && canBreak == 0)) {
                    continue;
                }
                
                // 현재 문제는 사방이 막힌 경우에 제자리에서 기다리는 경우가 문제인 것 같다.
                // 큐에 기다리는 경우를 넣어놓고, 다음 위치들을 탐색하는 구조이다.
                // 밤의 경우 continue 한 후 나중에 처리하는데, 방문체크는 이미 해놓기떄문에, 기다렸다가 출발하는 경우를 나중에 처리할때 지나갈 수 없다.
                // 그렇다고 한번에 거리를 2개씩 전진시키는 방법을 쓰면, 최단경로를 구하기 위해 bfs를 한 이유가 없어진다.
                // stayBit을 추가해서 방문배열을 분리했다.

                if (visited[stayBit][nx][ny] < canBreak) {
                    visited[stayBit][nx][ny] = canBreak;

                    // stayBit == 1 -> 기다렸다가 온 경우, 주변이 벽이 아닌 경우는 탐색할 필요가 없다.
                    if (stayBit == 1 && board[nx][ny] == '0') continue;

                    // 벽이있으면 부술수 있는 횟수 차감
                    if (board[nx][ny] == '1') {
                        // 밤이면 못지나감
                        if (isNight) {
                            continue;
                        }
                        canBreak--;
                    }

                    queue.add(new SnapShot(nx, ny, distance + 1, canBreak, 0, !isNight));
                }
            }
        }
        return -1;
    }

}
/*
밤낮을 boolean으로 표현

밤이면 제자리에서 거리만 더하는 경우 추가
 */