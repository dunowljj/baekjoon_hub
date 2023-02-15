import java.util.*;

class Solution {

    static class Grid {
        private Deque<Integer> left = new LinkedList<>(); // 1 4 7
        private Deque<Integer> right = new LinkedList<>(); // 3 6 9
        private Deque<Deque<Integer>> mids = new LinkedList<>();
         
        Grid (int[][] rc) {
            for (int row = 0; row < rc.length; row++) {
                right.offer(rc[row][rc[0].length - 1]);
                left.offer(rc[row][0]);
            }

            // 양옆 기둥 제외한 중간부분 순차적으로 넣기
            for (int row = 0; row < rc.length; row++) { // row는 전체 범위
                Deque<Integer> deque = new LinkedList<>();

                for (int col = 1; col < rc[0].length - 1; col++) { // col은 양 옆 제외
                    deque.offer(rc[row][col]);
                }

                mids.offer(deque);
            }
                 
//             for (Deque<Integer> dq : mids) {
//                 for (int num : dq) {
//                     System.out.print(num + " ");
//                 }
//                 System.out.println();
//              }
//                 System.out.println("===deque===");
            
        }
        
        public void shiftRow() {
            // 양쪽 기둥 : 맨 아래 숫자 -> 맨 위
            left.offerFirst(left.pollLast()); 
            right.offerFirst(right.pollLast());
            
            // 중간 부분 : 맨 아래 deque -> 맨 위
            mids.offerFirst(mids.pollLast());
        }
        
        public void rotate() {
            Deque<Integer> topDeque =  mids.peekFirst();
            Deque<Integer> bottomDeque =  mids.peekLast();
            
            int topLeft = left.pollFirst();
            topDeque.offerFirst(topLeft);
            
            int topRight = topDeque.pollLast();
            right.offerFirst(topRight);
            
            int bottomRight = right.pollLast();
            bottomDeque.offerLast(bottomRight);
            
            int bottomLeft = bottomDeque.pollFirst();
            left.offerLast(bottomLeft);    
        }
    
        public int[][] updateIn(int[][] rc) {
            for (int row = 0; row < rc.length; row++) {
                rc[row][0] = left.pollFirst();
                rc[row][rc[0].length - 1] = right.pollFirst();
            }
            
            for (int row = 0; row < rc.length; row++) { // row는 전체 범위
                Deque<Integer> deque = mids.poll();
                for (int col = 1; col < rc[0].length - 1; col++) { // col은 양 옆 제외
                    rc[row][col] = deque.pollFirst();
                }
            }
            
            return rc;
        }
    }
    
    public int[][] solution(int[][] rc, String[] operations) {
        Grid grid = new Grid(rc);
        
        for (String operation : operations) {
            if (operation.equals("ShiftRow")) grid.shiftRow();
            if (operation.equals("Rotate")) grid.rotate();
            
            // rc = grid.toArray(rc);
            // grid = new Grid(rc);
            // testPrint(rc);
        }
        
        grid.updateIn(rc);
        return rc;
    } 
    
    private void testPrint(int[][] rc) {
        for (int[] row : rc) {
            for (int e : row) {
                System.out.print(e);
            }
            System.out.println();
        }
        System.out.println("-----!");
        
    }
}
/*
rotate는 가장 바깥쪽만 회전한다.

행렬을 직접 rotate하는 경우, rc행렬곱이 최대 10만, operations 길이가 100만이므로, 최대 1000억으로 연산으로 시간이 오래걸린다.
*/