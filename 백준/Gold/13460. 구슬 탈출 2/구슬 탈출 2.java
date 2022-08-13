import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int answer = -1;
    static int[][] board;
    static boolean[][][][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] red = null;
        int[] blue = null;
        visited = new boolean[N][M][N][M]; // 0:red , 1:blue 방문체크

        // 둘레를 0(벽)으로 채우고, 나머지는 숫자값으로 입력
        board = new int[N][M];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                char cur = line.charAt(j);

                // int 기본값인 0을 벽으로 생각

                // # == 35
                if (cur == 35) continue;

                // O == 97
                if (cur == 79) {
                    board[i][j] = 79;
                    continue;
                }

                // R == 82
                if (cur == 82) {
                    red = new int[]{i, j};
                }

                // B == 66
                if (cur == 66) {
                    blue = new int[]{i, j};
                }


                board[i][j] = 1;
            }
        }

        //방문조건이 문제인게, 두 개의 위치를 한번에 나타내질 못한다. 비트를 아예 큐에 넣어야한다.
        bfs(red,blue);

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }

    static void bfs(int[] red, int[] blue) {
        Queue<int[]> q = new LinkedList();
        q.add(new int[]{red[0], red[1], blue[0], blue[1], 0});

        int[][] mapper = {{1,-1,0,0}, {0,0,-1,1}};

        while (!q.isEmpty()) {
            int[] beads = q.poll();
            int redX = beads[0];
            int redY = beads[1];
            int blueX = beads[2];
            int blueY = beads[3];
            int depth = beads[4];

            if (depth == 10) return;

            // 4방향 기울이기 , 상하좌우 순서
            for (int i = 0; i < 4; i++) {
                int nrx = redX;
                int nry = redY;
                int nbx = blueX;
                int nby = blueY;

                int mx = mapper[0][i];
                int my = mapper[1][i];

                boolean redBlocked = false;
                boolean redOut = false;

                // red 구슬 벽 이전까지 전진; 숫자 0 은 벽
                while (board[nrx + mx][nry + my] != 0) {
                    nrx += mx;
                    nry += my;
                    // '0' 은 구멍의 위치 -> 구멍을 만나면 out
                    if (board[nrx][nry] == 79) {
                        redOut = true;
                        break;
                    }

                    // 가는 길목에 파란 구슬 있는지 체크
                    if (blueX == nrx && blueY == nry) {
                        redBlocked = true;
                    }
                }

                // 길목에 구슬이 있으면, 맨 끝에서 한자리 전까지만 이동. 구멍이 있으면 이미 out이라 상관없다.
                if (redBlocked) {
                    nrx -= mx;
                    nry -= my;
                }

                boolean blueBlocked = false;
                boolean blueOut = false;

                // blue구슬 마찬가지로 처리
                // 0 이면 실행이 안된다. 근데 구멍은 왜 못찾지?
                while (board[nbx + mx][nby + my] != 0) {
                    nbx += mx;
                    nby += my;

                    if (board[nbx][nby] == 79) {
                        blueOut = true;
                        break;
                    }


                    // 가는 길목에 빨간 구슬 있는지 체크
                    if (redX == nbx && redY == nby) {
                        blueBlocked = true;
                    }
                }

                if(blueBlocked) {
                    nbx -= mx;
                    nby -= my;
                }

                // 파란 구슬 빠진 경우 -> 두개 다 빠진 경우도 걸러진다.
                if (blueOut) {
                    continue;
                }

                // 빨간 구슬만 빠진 경우
                if ((redOut && !blueOut)) {
                    answer = depth + 1;
                    return;
                }

                // 아무 구슬도 빠지지 않은 경우
                if (!redOut && !blueOut) {
                    if (!visited[nrx][nry][nbx][nby]) {
                        visited[nrx][nry][nbx][nby] = true;
                        // 도착점에 방문하지 않은 경우만 큐에 넣고 체크.
                        q.add(new int[]{nrx, nry, nbx, nby, depth + 1});
                    }
                }
            }
        }
    }
}
/*
빨간 구슬, 파란 구슬 겹칠 수 없다.
파란 구슬이 빠지면 안됨. 동시에 빠져도 실패.

기울이는 동안 동작을 그만하는 것은 구슬이 더 움직이지 않을때

# 벽 -> 0
0 구멍 : 48
. 빈칸 : 46

3<= N <= 10

4방향 기울이는 경우 * 4 * 4 ..
빨간구슬, 파란구슬 위치가 이전과 같으면 중복 -> 이차원배열
결국에는 방문했던 위치인지 검사하면서 탐색

굴리는 방향에 다른 구슬 있는 경우?
    1) 벽없이 존재 : 해당방향끝 - 1
    2) 벽을 끼고 존재 : 상관 x
    
!비트마스크로 다시 풀어보기
 */