class Solution {
    
    static final int TYPE_ATTACK = 1;
    static final int TYPE_RECOVERY = 2;
    
    public int solution(int[][] board, int[][] skill) {
        int[][] prefixSum_board = new int[board.length + 1][board[0].length + 1];
        
        for (int[] sk : skill) {
            if (sk[0] == TYPE_ATTACK) attack(prefixSum_board, sk[1], sk[2], sk[3], sk[4], sk[5]);
            else if (sk[0] == TYPE_RECOVERY) recovery(prefixSum_board, sk[1], sk[2], sk[3], sk[4], sk[5]);        
            
        }
        
        prefixSum(prefixSum_board); // 누적합 구하기
        merge(board, prefixSum_board);
        return countBuildings(board);
    }
    
    private void attack(int[][] prefixSum_board, int r1, int c1, int r2, int c2, int degree) {
        update(prefixSum_board, r1, c1, r2, c2, -1 * degree);
    }
    
    private void recovery(int[][] prefixSum_board, int r1, int c1, int r2, int c2, int degree) {
        update(prefixSum_board, r1, c1, r2, c2, degree);
    }
    
    private void update(int[][] prefixSum_board, int r1, int c1, int r2, int c2, int value) {
        prefixSum_board[r1][c1] += value;
        prefixSum_board[r1][c2 + 1] += -value;  
        prefixSum_board[r2 + 1][c1] += -value;
        prefixSum_board[r2 + 1][c2 + 1] += value;
    }
    
    private void prefixSum(int[][] prefixSum_board) {
        // 누적합 : 왼 -> 오
        for (int row = 0; row < prefixSum_board.length - 1; row++) {
            for (int col = 0; col < prefixSum_board[0].length - 1; col++) {    
                prefixSum_board[row][col + 1] += prefixSum_board[row][col];
            }
        }
        
        // 누적합 : 위 -> 아래
        for (int col = 0; col < prefixSum_board[0].length - 1; col++) {
            for (int row = 0; row < prefixSum_board.length - 1; row++) {
                prefixSum_board[row + 1][col] += prefixSum_board[row][col];
            }
        }
    }
    
    private int[][] merge(int[][] board, int[][] prefixSum_board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] += prefixSum_board[i][j];
            }
        }
        
        return board;
    }
    
    private int countBuildings(int[][] board) {
        int aliveBuilding = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] > 0) aliveBuilding++;
            }
        }
        return aliveBuilding;
    }
    
    private void testPrint(int[][] board ) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] +" ");
            }
            System.out.println();
        }
            System.out.println();
    }
    
    
}

/*
최대 100만칸
skill 최대 25만 -> 2500억

skill을 통합할 방법이 필요하다. 범위를 merge하는것과 비슷하지 않을까?


*/