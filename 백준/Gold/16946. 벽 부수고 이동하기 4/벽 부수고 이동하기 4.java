import java.io.*;
import java.util.*;

class Point2 {
    int x;
    int y;

    public Point2(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    static int N;
    static int M;
    static int groupNum = 2;
    static int[][] board;
    static int[][] answerSheet;
    static int[][] mapper = {{1, -1, 0, 0}, {0, 0, -1, 1}};
    static Map<Integer, Integer> groups = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        answerSheet = new int[N][M];

        // 입력
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = input.charAt(j) - '0';
            }
        }

        // 정답지로 쓸것 따로 복사 (0인 상태)
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, answerSheet[i], 0, board[i].length);
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == 0) {
                    grouping(i, j);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == 1) {
                    searchAround(i,j);
                }
            }
        }


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                bw.write(answerSheet[i][j]+"");
            }
            bw.write("\n");
        }


        bw.flush();
        bw.close();
    }

    private static void grouping(int i, int j) {
        Queue<Point2> queue = new LinkedList<>();
        queue.add(new Point2(i, j));
        board[i][j] = groupNum;

        int count = 1;
        while (!queue.isEmpty()) {
            Point2 point = queue.poll();

            for (int k = 0; k < 4; k++) {
                int nx = point.x + mapper[0][k];
                int ny = point.y + mapper[1][k];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
                    continue;
                }

                if (board[nx][ny] == 0) {
                    board[nx][ny] = groupNum;
                    queue.add(new Point2(nx, ny));
                    count++;
                }
            }
        }

        groups.put(groupNum, count);
        groupNum++;
    }

    private static void searchAround(int i, int j) {

        Set<Integer> set = new HashSet();

        for (int k = 0; k < 4; k++) {

            int nx = i + mapper[0][k];
            int ny = j + mapper[1][k];

            // 주변이 1이 아닌 경우만 탐색
            if (nx < 0 || ny < 0 || nx >= N || ny >= M || board[nx][ny] == 1) {
                continue;
            }

            // set에 그룹 번호 추가
            set.add(board[nx][ny]);
        }

        //보드에 해당 그룹번호를 이용해서 그룹의 개수를 찾아서 해당 벽에 추가
        set.forEach((num) -> answerSheet[i][j] += groups.get(num));
        answerSheet[i][j] %= 10;
    }

}

/*
벽이 있는 위치 탐색 후, 0인 부분을 모두 방문. 방문체크하면서 방문해야한다.
1) 0방문 시 방문 체크하기 -> 체크를 다시 풀어주기가 힘들다. x
2) 0을 -1로 체우면서 방문하고 -1 제거해주기 o

--> 이와 같이 그냥 Bfs 하면 시간초과가 난다.

## 해결
분리 집합
1) 인접한 0끼리 그룹을 만들어주고, 그룹마다 번호를 부여한다.
2) 해당 그룹에 속하는 좌표의 개수를 HashMap에 저장
3) 전체 board를 순회하며 1을 찾고, 주변 4방향에 있는 그룹번호들을 HashSet에 저장(중복 제거)
4) HashSet에 저장된 값을 이용해 HashMap에 개수 검색, 추가, %10


 */