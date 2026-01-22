import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int SIZE = 19;
    static int[][] board = new int[SIZE][SIZE];
    static int[] answer = new int[2];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < SIZE; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) continue;

                if (check(i,j)) {
                    System.out.println(board[i][j]);
                    System.out.print((answer[0]+1)+" "+(answer[1]+1));
                    return;
                }
            }
        }

        System.out.print("0");
    }

    static final int[][] mapper = {{0,1,1,-1},{1,0,1,1}};

    private static boolean check(int i, int j) {
        int color = board[i][j];

        for (int dir = 0; dir < mapper[0].length; dir++) {
            int count = 1;

            int ny = i;
            int nx = j;

            // 정방향 확인. 오른쪽, 아래, 오른아래, 오른쪽위
            while (true) {
                ny = ny + mapper[0][dir];
                nx = nx + mapper[1][dir];

                if (ny >= SIZE || ny < 0 || nx >= SIZE || nx < 0) break;
                if (color != board[ny][nx]) break;
                count++;
            }

            ny = i;
            nx = j;

            int sy = i;
            int sx = j;
            boolean flag = false;
            // 역방향 확인
            while (true) {
                ny = ny - mapper[0][dir];
                nx = nx - mapper[1][dir];

                if (ny >= SIZE || ny < 0 || nx >= SIZE || nx < 0) break;
                if (color != board[ny][nx]) break;

                sy = ny;
                sx = nx;
                count++;
            }

            if (count == 5) {
                answer = new int[]{sy,sx};
                return true;
            }
        }

        return false;
    }
}

/**
 * 왼쪽위에서부터 순회하며 센다.
 * 전체로 방문체크를 하면, 다른 방향으로 5개인 경우가 체크가 안된다.
 * 6개면 끝이 아니다. 6개의 연속된 돌의 2번째부터 세었다가 5개로 오인할 가능성이 있으므로 양방향을 모두 세준다.
 * 가장 왼쪼겡 있는 알을 찾아야하므로, 왼쪽 아래가 아닌, 오른쪽위로 탐색해야한다.
 */