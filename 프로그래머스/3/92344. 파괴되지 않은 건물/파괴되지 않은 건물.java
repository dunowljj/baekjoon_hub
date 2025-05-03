class Solution {
    
    private int ATTACK = 1;
    private int RECOVER = 2;
    private int n,m;
    
    public int solution(int[][] board, int[][] skills) {
        int answer = 0;
        n = board.length;
        m = board[0].length;
        
        int[][] arr = new int[n + 1][m + 1];
        
        // r1,c2 <= r2,c2
        for (int[] skill : skills) {
            int type = skill[0];
            int r1 = skill[1];
            int c1 = skill[2];
            int r2 = skill[3];
            int c2 = skill[4];
            int degree = skill[5];
            
            if (type == ATTACK) {
                update(arr, r1, c1, r2, c2, degree * -1);
            } 
            
            if (type == RECOVER) {
                update(arr, r1, c1, r2, c2, degree);
            }
        }
        
        sum(arr);
        
        return countExistBuilding(board, arr);
    }
    
    
    // r1,c1  r1,c2
    // r2,c1  r2,c2
    public void update(int[][] arr, int r1, int c1, int r2, int c2, int degree) {
        arr[r1][c1] += degree;
        arr[r1][c2 + 1] -= degree;
        arr[r2 + 1][c1] -= degree;
        arr[r2 + 1][c2 + 1] += degree;
    }
    
    public void sum(int[][] arr) {
        // to right
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m - 1; c++) {
                arr[r][c + 1] += arr[r][c]; 
            }
        }
        
        // to bottom
        for (int c = 0; c < m; c++) {
            for (int r = 0; r < n - 1; r++) {
                arr[r + 1][c] += arr[r][c]; 
            }
        }
    }
    
    public int countExistBuilding(int[][] board, int[][] sumArr) {
        int existCount = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (board[r][c] + sumArr[r][c] > 0) {
                    existCount++;
                }
            }
        }
        
        return existCount;
    }
}
/**
최대 100만개 건물 -> 25만 스킬 -> 10^6 * 25 * 10^5 -> 너무 많다.
스킬들을 병합해야할듯. 어떻게?
구간 정보를 4꼭짓점만 갱신하고, 누적합을 활용해 합친다.

[0,0,0,0] -> [1,1,1,0]
[1,0,0,-1]라고만 표시하고, 누적합 처리한 것과 같다.

*/