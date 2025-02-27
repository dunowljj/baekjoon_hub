import java.util.*;

class Solution {

    private static final String ROTATE = "Rotate";
    private static final String SHIFT_ROW = "ShiftRow";

    public int[][] solution(int[][] rc, String[] operations) {
        Board board = new Board(rc);

        for (String operation : operations) {
            if (operation.equals(ROTATE)) {
                board.rotate();
                continue;
            }

            if (operation.equals(SHIFT_ROW)) {
                board.shiftRow();
                continue;
            }
        }

        return board.pollResult();
    }

    static class Board {

        // 위가 first, 아래가 last로 통일
        Deque<Integer> left;
        Deque<Integer> right;
        Deque<Deque<Integer>> mids;
        int rLen;
        int cLen;
        int[][] board;

        public Board(int[][] rc) {
            rLen = rc.length;
            cLen = rc[0].length;

            board = new int[rLen][cLen];
            left = new LinkedList<>();
            right = new LinkedList<>();
            mids = new LinkedList<>();

            init(rc);
        }

        private void init(int[][] rc) {
            for (int i = 0; i < rLen; i++) {
                mids.offerLast(new LinkedList<>());
            }

            Iterator<Deque<Integer>> iter = mids.iterator();
            for (int i = 0; i < rLen; i++) {
                left.offerLast(rc[i][0]);
                right.offerLast(rc[i][cLen - 1]);

                Deque<Integer> mid = iter.next();
                for (int j = 1; j < cLen - 1; j++) {
                    mid.offerLast(rc[i][j]);
                }
            }
        }

        public int[][] pollResult() {
            for (int i = 0; i < rLen; i++) {
                board[i][0] = left.pollFirst();
                board[i][cLen - 1] = right.pollFirst();

                Deque<Integer> mid = mids.pollFirst();
                for (int j = 1; j < cLen - 1; j++) {
                    board[i][j] = mid.pollFirst();
                }
            }
            return board;
        }

        public void rotate() {
            Deque<Integer> top = mids.peekFirst();
            Deque<Integer> bottom = mids.peekLast();

            int leftTop = left.pollFirst();
            top.offerFirst(leftTop);

            int rightTop = top.pollLast();
            right.offerFirst(rightTop);

            int rightBottom = right.pollLast();
            bottom.offerLast(rightBottom);

            int leftBottom = bottom.pollFirst();
            left.offerLast(leftBottom);
        }

        public void shiftRow() {
            left.offerFirst(left.pollLast());
            mids.offerFirst(mids.pollLast());
            right.offerFirst(right.pollLast());
        }
    }
}