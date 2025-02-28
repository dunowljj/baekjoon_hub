import java.util.*;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        int n = board.length;
        Stack<Integer> bascket = new Stack<>();
        
        for (int move : moves) {
            int loc = move - 1;
            
            // 해당 열(위치) 모든 행에서 인형 찾기 (더 최적화 가능 부분)
            for (int h = 0; h < n; h++) {
                if (board[h][loc] != 0) {
                    
                    if (!bascket.isEmpty() && bascket.peek() == board[h][loc]) {
                        bascket.pop();
                        answer += 2;
                    } else {
                        bascket.push(board[h][loc]);
                    }
                    
                    board[h][loc] = 0;
                    break;
                }
            }
        }
        
        return answer;
    }
}
/**
일자로 맨 위 인형뽑기
stack바구니, 2개 인형 터짐
**/