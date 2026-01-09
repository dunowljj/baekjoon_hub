import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int SIZE = 9;
    static int[][] board;
    static int[] rowBit, colBit, boxBit;
    static List<Point> empties = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        board = new int[SIZE][SIZE];
        rowBit = new int[SIZE];
        colBit = new int[SIZE];
        boxBit = new int[SIZE]; // 0~8번 box

        for (int r = 0; r < SIZE; r++) {
            String line = br.readLine();
            for (int c = 0; c < SIZE; c++) {
                int val = line.charAt(c) - '0';

                if (val == 0) {
                    empties.add(new Point(r, c));
                } else {
                    int bit = 1 << val;

                    rowBit[r] |= bit;
                    colBit[c] |= bit;
                    boxBit[boxIdx(r,c)] |= bit;
                }

                board[r][c] = val;
            }
        }

        dfs(0);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean dfs(int index) {
        if (index == empties.size()) {
            return true;
        }

        int r = empties.get(index).r;
        int c = empties.get(index).c;
        int bIdx = boxIdx(r, c);

        int used = rowBit[r] | colBit[c] | boxBit[bIdx];

        for (int num = 1; num <= 9; num++) {
            int bit = 1 << num;
            if ((used & bit) != 0) continue;

            board[r][c] = num;
            rowBit[r] |= bit;
            colBit[c] |= bit;
            boxBit[bIdx] |= bit;

            if (dfs(index + 1)) return true;

            board[r][c] = 0;
            rowBit[r] ^= bit;
            colBit[c] ^= bit;
            boxBit[bIdx] ^= bit;
        }

        return false;
    }

    private static int boxIdx(int r, int c) {
        return (r / 3) * 3 + (c / 3);
    }

    static class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
