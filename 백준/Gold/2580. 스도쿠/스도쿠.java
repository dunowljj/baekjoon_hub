import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static final int SUDOKU_BOARD_SIZE = 9;
    static int[][] board = new int[SUDOKU_BOARD_SIZE][SUDOKU_BOARD_SIZE];
    static StringBuilder answer = new StringBuilder();


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;



        for (int i = 0; i < SUDOKU_BOARD_SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < SUDOKU_BOARD_SIZE; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0,0);




    }

    private static void dfs(int row, int col) {

        /*
         마지막 열일 경우 다음 행 첫번째부터 시작
         재귀하지 않고 인수를 갱신하면, 아래쪽 코드에는 적용이 되지 않는다.
         */
        if (col == 9) {
            dfs(row + 1, 0);
            return;
        }

        // 완성 시 정답 출력
        if (row == 9) {
            for (int i = 0; i < SUDOKU_BOARD_SIZE; i++) {
                for (int j = 0; j < SUDOKU_BOARD_SIZE; j++) {
                    answer.append(board[i][j]).append(" ");
                }
                answer.append("\n");
            }
            System.out.print(answer.toString().trim());
            System.exit(0);
        }


        if (board[row][col] == 0) {
            for (int i = 1; i <= 9; i++) {
                if (isPossible(row, col, i)) {
                    board[row][col] = i;
                    dfs(row, col + 1);
                }
            }

            /*
             조건문이 실행됨 : 재귀했다가 돌아온 경우는 다 채우기를 실패했다는 것이다. 해당 보드에 시도했던 숫자를 지운다.
             조건문이 실행되지 않음 : 어짜피 0이므로 상관 없다.
             */
            board[row][col] = 0;

            // 0을 채우지 못했는데, 다음으로 넘어가면 안되기 때문에 종료한다.
            return;
        }

        // 0이 아닌 경우 다음 행으로 넘어간다.
        dfs(row, col + 1);
    }

    private static boolean isPossible(int row ,int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        row = (row / 3) * 3;
        col = (col / 3) * 3;

        for (int k = row; k < row + 3; k++) {
            for (int l = col; l < col + 3; l++) {
                if (board[k][l] == num) {
                    return false;
                }
            }
        }
        return true;
    }




}