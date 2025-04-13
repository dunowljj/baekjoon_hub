import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static final int DOWN = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int RIGHT = 3;

    public static int R, C, M;

    public static final int[][] mapper = {{1, 0, -1, 0}, {0, -1, 0, 1}}; // 아래 왼 위 오

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Queue<Shark> sharks = new LinkedList<>();
        Shark[][] board = new Shark[R + 1][C + 1]; // 위치한 상어의 사이즈를 기록하는 배열
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            Shark shark = new Shark(r, c, s, d, z);
            board[r][c] = shark;
            sharks.offer(shark);
        }

        int totalCatchSize = 0;
        for (int col = 1; col <= C; col++) {

            // 낚시왕이 낚시를 한다.
            for (int row = 1; row <= R; row++) {
                if (board[row][col] != null) {
                    totalCatchSize += board[row][col].size;
                    //                    System.out.println("catch size = " + board[row][col].size);
                    board[row][col].isInvalid = true;
                    break;
                }
            }
            //            System.out.println("---");

            // 상어들의 이동
            Shark[][] temp = new Shark[R + 1][C + 1];
            int size = sharks.size();
            for (int i = 0; i < size; i++) {
                Shark shark = sharks.poll();
                if (shark.isInvalid) continue;

                //                System.out.print("["+shark.r+","+shark.c+"]");
                shark.move();
                //                System.out.println("-> ["+shark.r+","+shark.c+"]");


                if (temp[shark.r][shark.c] == null) {
                    temp[shark.r][shark.c] = shark;

                    // 해당칸에 도착하게 될 다른 상어가 있는 경우 -> 큰 상어가 잡아먹음
                } else {

                    if (temp[shark.r][shark.c].size < shark.size) {
                        temp[shark.r][shark.c].isInvalid = true;
                        temp[shark.r][shark.c] = shark;
                    } else if (temp[shark.r][shark.c].size > shark.size) {
                        shark.isInvalid = true;
                    }
                }

                sharks.offer(shark);
            }

            board = temp;
        }

        System.out.print(totalCatchSize);
    }

    static class Shark {
        int r;
        int c;
        int speed;
        int dir;
        int size;
        boolean isInvalid;

        public Shark(int r, int c, int speed, int dir, int size) {
            this.r = r;
            this.c = c;
            this.speed = speed;
            this.size = size;
            this.isInvalid = false;

            // 위
            if (dir == 1) {
                this.dir = UP;
            }

            // 아래
            if (dir == 2) {
                this.dir = DOWN;
            }

            // 오른
            if (dir == 3) {
                this.dir = RIGHT;
            }

            // 왼쪽
            if (dir == 4) {
                this.dir = LEFT;
            }
        }

        public void move() {
            int rm = mapper[0][dir] * (speed % (2 * R - 2));
            int cm = mapper[1][dir] * (speed % (2 * C - 2));

            int move = Math.max(Math.abs(rm), Math.abs(cm));
            for (int i = 0; i < move; i++) {
                r += mapper[0][dir];
                c += mapper[1][dir];

                // 경계선 넘어감
                if (r < 1 || r > R || c < 1 || c > C) {
                    // 방향 전환
                    dir = (dir + 2) % 4;

                    // 바뀐 방향으로 2만큼 이동
                    r += (mapper[0][dir] * 2);
                    c += (mapper[1][dir] * 2);
                }
            }

            //            r = r + (mapper[0][dir] * speed) % (2 * R - 2);
            //            c = c + (mapper[1][dir] * speed) % (2 * C - 2);
            //

            //            // 방향 반전
            //            if (r < 1 || r > R || c < 1 || c > C) dir = (dir + 2) % 4;
            //
            //            // 지나간 만큼 돌아가기
            //            if (r < 1) r = 1 + (1 - r);
            //            if (r > R) r = R - (r - R);
            //            if (c < 1) c = 1 + (1 - c);
            //            if (c > C) c = C - (c - C);
            //
            //            // 왕복 기준으로 %연산 중이므로 2회 시행
            //
            //            // 방향 반전
            //            if (r < 1 || r > R || c < 1 || c > C) dir = (dir + 2) % 4;
            //            if (r < 1) r = 1 + (1 - r);
            //            if (r > R) r = R - (r - R);
            //            if (c < 1) c = 1 + (1 - c);
            //            if (c > C) c = C - (c - C);
        }
    }
}

/**
     * 격자판 최대 100*100
     * 낚시왕은 열마다 검사를 하므로 최대 격자판 크기만큼 탐색 -> 따로 최적화 불필요?
     *
     * 두 상어 크기가 같은 경우는 없다.
     *
     * 1만마리 상어가 100번 이동
     * 초기엔 칸당 상어 한마리
     * 상어가 경계를 넘으면 방향 반대로 이동
     * 상어가 이동한 후 -> 칸에 겹치면 큰 상어가 모두 잡아먹음 -> max 연산으로 갱신
     *
     *
     * ### 풀이
     * - 1만개 칸을 모두 탐색하면서 마주치는 모든 상어 이동시킨 상태의 배열 갱신
     * - 이동한 상어 배열로 원본 배열 대체
     * - 낚시왕 이동, 낚시 수행

     아니면 모든 상어를 큐에 넣고, 먹히거나 잡혔는지 여부를 체크한다.
     모든 상어를 이동시키면서 갱신한다. 단, 이동한 위치에 더 큰 상어가 있으면 먹힘처리
     큰 상어가 작은 상어를 만날 경우, 작은 상어를 먹힘처리 해야한다.

     S가 1000이라서, 경계를 여러번 만날 가능성이 존재한다.
     */