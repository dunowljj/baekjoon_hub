import java.io.*;
import java.util.*;

class Snapshot {
    int[][] board;
    int depth;

    public Snapshot(int[][] board, int depth) {
        this.board = board;
        this.depth = depth;
    }

}

public class Main {
    static int max = 0;
    static int N;
//    static Set<Integer> set = new HashSet();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        bfs(board, 0);

        bw.write(max+"");
        bw.flush();
        bw.close();
    }

    static void bfs(int[][] board, int depth) {
        Queue<Snapshot> queue = new LinkedList();
        queue.add(new Snapshot(board, depth));
        int[][] temp = new int[N][N];
        while (!queue.isEmpty()) {
            Snapshot snapshot = queue.poll();
            board = snapshot.board;
//            bit = snapshot.bit;
            depth = snapshot.depth;

            if (depth == 5){
                //최댓값
                findMax(board);
                continue;
            }

            // temp에 board 이동 전 값 저장
            copy(board, temp);

            // 상하좌우 밀기
            for (int way = 0; way < 4; way++) {
                move(board, way);
                // 비트로 방문여부 체크 -> bfs라 필요 없음
//                int nextBit = bit | (1 << way << (depth * 4));


                int[][] arr = new int[N][N];
                copy(board, arr);
                queue.add(new Snapshot(arr,depth + 1));

                /*if (!set.contains(nextBit)) {
                    set.add(nextBit);
                }*/

                // 이동마다 temp에 저장했던 값 board에 입력
                copy(temp, board);
            }
        }
    }

    private static void copy(int[][] src, int[][] dest) {
        for (int i = 0; i < N; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, N);
        }
    }

    private static void move(int[][] board, int way) {
        if (way == 0) {
            pushUpward(board);
        }

        if (way == 1) {
            pushDownward(board);
        }

        if (way == 2) {
            pushLeftward(board);
        }

        if (way == 3) {
            pushRightward(board);
        }
    }

    private static void pushUpward(int[][] board) {
        for (int i = 0; i < N; i++) {
            int row = 0;
            int temp = 0;
            for (int j = 0; j < N; j++) {

                if (board[j][i] != 0) {

                    if (temp == board[j][i]) {
                        board[row - 1][i] = temp * 2;

                        temp = 0;

                        board[j][i] = 0;

                        // 합쳐졌으므로, 넣는 위치의 row를 +하지 않는다.

                    } else {
                        temp = board[j][i];
                        board[j][i] = 0;
                        board[row][i] = temp;

                        row++;
                    }
                }
            }
        }
    }

    private static void pushDownward(int[][] board) {
        for (int i = 0; i < N; i++) {
            int row = N - 1;
            int temp = 0;
            for (int j = N - 1; j >= 0; j--) {

                if (board[j][i] != 0) {

                    if (temp == board[j][i]) {
                        board[row + 1][i] = temp * 2;

                        temp = 0;

                        board[j][i] = 0;

                        // 합쳐졌으므로, 넣는 위치의 row를 -하지 않는다.

                    } else {
                        temp = board[j][i];
                        board[j][i] = 0;
                        board[row][i] = temp;

                        row--;
                    }

                }
            }
        }
    }

    private static void pushLeftward(int[][] board) {
        // 앞쪽부터 찾아서 앞쪽 인덱스에 넣기;  0이 아닌 경우만 밀어야한다. 빈 칸은 다시 0 처리해줘야한다.
        // row, col은 이동한 값이나 합쳐진 값을 넣는 위치
        for (int i = 0; i < N; i++) {
            int col = 0;
            int temp = 0; //행 넘어갈때마다 이전값 초기화
            for (int j = 0; j < N; j++) {
                // 빈값이 아닌 경우
                if (board[i][j] != 0) {

                    // 부딪히는 블록이 서로 같을 때 합치기
                    if (temp == board[i][j]) {
                        board[i][col - 1] = temp * 2;
                        // 합쳐진 수는 바로 합쳐질 수 없음. 합치기 위한 이전값 없애기
                        temp = 0;

                        // 합쳐서 없어진 수의 자리를 0 표시
                        board[i][j] = 0;

                        // 합쳐졌으므로, 넣는 위치의 col을 +하지 않는다.
                    }

                    // 맨앞 인덱스부터 채우고, 이전에 이동시킨 값 저장하기.
                    else {
                        temp = board[i][j];
                        board[i][j] = 0;
                        board[i][col] = temp;

                        // 다음 넣을 위치 지정
                        col++;
                    }

                }
            }
            // 한 행 모두 처리 시 다음 행으로 넣는 위치 바꾸기
        }
    }

    private static void pushRightward(int[][] board) {
        for (int i = 0; i < N; i++) {
            int col = N - 1;
            int temp = 0;
            for (int j = N - 1; j >= 0; j--) {
                if (board[i][j] != 0) {

                    if (temp == board[i][j]) {
                        board[i][col + 1] = temp * 2;

                        temp = 0;

                        board[i][j] = 0;

                        // 합쳐졌으므로, 넣는 위치의 col을 +하지 않는다.

                    } else {
                        temp = board[i][j];
                        board[i][j] = 0;
                        board[i][col] = temp;

                        col--;
                    }
                }
            }
        }
    }

    private static void findMax(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int cur = board[i][j];
                if (cur > max) {
                    max = cur;
                }
            }
        }
    }
/*
   //출력 확인
   private static void test(int[][] board) {
        for (int[] ints : board) {
            for (int anInt : ints) {
                System.out.print(anInt+" ");
            }
            System.out.println();
        }
        System.out.println();
    }*/
}


/*
N : 1~20

0은 빈칸

상하좌우 * 5번 -> 경우의 수 비트마스크로 체크

해당 방향을 이동 시,
- 해당 방향의 끝부분 인덱스부터 값을 채운다.
- 같은 수일 경우 끝부분 기준으로 합쳐진다. 합치고 바뀌는 인덱스를 고려해서 for문이라면 인덱스를 1개 더 증가시킨다.
- 이동 전 값을 비운다. -> 이동해도 같은 위치인 경우 생각

주어지는 수가 2의 제곱수들이기 때문에, 밑이 2인 log로 나타낼 수 있다. 이건 굳이 안해도 될듯.

bfs 경우
- 바뀐 상태의 배열을 큐에 넣어야 한다. -> 메모리 낭비 심할듯
- 새로운 배열을 매번 만들어서 넣어야함.

dfs 사용?
이전 배열을 저장해서 되돌리고 탐색을 이어가야하는 문제 -> 이전 배열을 깊은 복사하려면, 매번 실행마다 N제곱의 연산을 해야한다.


비트마스크 방문체크? -> 애초에 bfs에 방문체크가 필요없다. 잘못 구현했다. dfs로 다시해보기.
0000 0000 0000 0000 0000 -> 상하좌우 5번. 2의 20제곱으로 어디로 이동했는지 확인 가능
[1 << (방향 인덱스값) << ((이동 횟수(깊이)) * 4)] -> 당장 이동한 값에 대한 정보
-> 이것들을 계속해서 합치면 해당 탐색이 거쳐온 모든 경로이다.
-> 문제는 이것을 어떻게 체크하냐인데, 2의 20제곱만큼 boolean 배열-> 메모리 터짐
-> HashSet에 넣어서 체크


*/