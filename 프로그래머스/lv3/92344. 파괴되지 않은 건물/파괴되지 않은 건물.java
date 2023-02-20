class Solution {
    
    enum Type {
        ATTACK(1), RECOVERY(2);

        private int value;
        
        Type(int value) {
            this.value = value;
        }
        
        private int value() {
            return this.value;
        }
        
        public static Type findTypeByValue(int value) {
            for(Type type : values()) {
                if (type.value() == value) return type;
            }
            
            throw new IllegalArgumentException("잘못된 타입입니다.");
        }
    }
    
    public int solution(int[][] board, int[][] skills) {
        int[][] sumBoard = new int[board.length + 1][board[0].length + 1];
        
        for (int[] skill : skills) {
            Type type = Type.findTypeByValue(skill[0]);
            if (type == Type.ATTACK) attack(sumBoard, skill[1], skill[2], skill[3], skill[4], skill[5]);
            else if (type == Type.RECOVERY) recovery(sumBoard, skill[1], skill[2], skill[3], skill[4], skill[5]);        
        }
        
        prefixSum(sumBoard);
        merge(board, sumBoard);
        return countBuildings(board);
    }
    
    private void attack(int[][] sumBoard, int r1, int c1, int r2, int c2, int degree) {
        update(sumBoard, r1, c1, r2, c2, -1 * degree);
    }
    
    private void recovery(int[][] sumBoard, int r1, int c1, int r2, int c2, int degree) {
        update(sumBoard, r1, c1, r2, c2, degree);
    }
    
    private void update(int[][] sumBoard, int r1, int c1, int r2, int c2, int value) {
        sumBoard[r1][c1] += value;
        sumBoard[r1][c2 + 1] -= value;  
        sumBoard[r2 + 1][c1] -= value;
        sumBoard[r2 + 1][c2 + 1] += value;
    }
    
    private void prefixSum(int[][] sumBoard) {
        // 누적합 : 왼 -> 오
        for (int row = 0; row < sumBoard.length - 1; row++) {
            for (int col = 0; col < sumBoard[0].length - 1; col++) {    
                sumBoard[row][col + 1] += sumBoard[row][col];
            }
        }
        
        // 누적합 : 위 -> 아래
        for (int col = 0; col < sumBoard[0].length - 1; col++) {
            for (int row = 0; row < sumBoard.length - 1; row++) {
                sumBoard[row + 1][col] += sumBoard[row][col];
            }
        }
    }
    
    private int[][] merge(int[][] board, int[][] sumBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] += sumBoard[i][j];
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