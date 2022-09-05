import java.util.Stack;

class Solution {
    static Stack<Integer> bascket = new Stack();
    static int count = 0;
    
    public int solution(int[][] board, int[] moves) {
        
        for (int i = 0; i < moves.length; i++) {
        	moveDollAt(moves[i] - 1, board);
        }
        
        return count;
    }
    
    private void moveDollAt(int location, int[][] board) {
        for (int row = 0; row < board.length; row++) {
			int doll = board[row][location];
            if (doll != 0) {
                
                board[row][location] = 0;

                // 바구니에 같은 인형이면 바구니에 인형 제거 / 아니면 인형 집어넣기
                if (!bascket.isEmpty() && bascket.peek() == doll) {
                    bascket.pop();
                    count += 2;
                    
                } else {
                    bascket.push(doll);
                }
                break;
            }
        }
    }
}