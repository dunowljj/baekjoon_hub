class Solution {
    int count = 0;
    int[][] mapper = {{1,1,-1,-1},{1,-1,-1,1}};
    
    public int solution(int n) {
        boolean[][] isQueen = new boolean[n][n];
        
        dfs(isQueen, 0, 0, n);
        return count;
    }

    private void dfs(boolean[][] isQueen, int row, int col, int n) {
        // 모든 열을 탐색했는데 퀸을 배치 못했다면 어짜피 놓을 수 없다.
        if (col == n) return;
        
        // 모든 row를 탐색했다는 것은 퀸 배치 성공
        if (row == n) {
            count++;
            return;
        }
        
        
        if (available(isQueen, n, row, col)) {
            isQueen[row][col] = true;
            dfs(isQueen, row + 1, 0, n);
            isQueen[row][col] = false;
        }

        
        dfs(isQueen, row, col + 1, n);
    }
    
    private boolean available(boolean[][] isQueen, int n, int row, int col) {
        return checkRow(isQueen, n, col) && checkColumn(isQueen, n, row) && checkDiagonal(isQueen, n, row, col);
    }
    
    private boolean checkRow(boolean[][] isQueen, int n, int col) {
        for (int i = 0; i < n; i++) {
            if (isQueen[i][col]) return false;
        }
        return true;
    }
    
    private boolean checkColumn(boolean[][] isQueen, int n, int row) {
        for (int i = 0; i < n; i++) {
            if (isQueen[row][i]) return false;
        }
        return true;
    }
    
    private boolean checkDiagonal(boolean[][] isQueen, int n, int row, int col) {
        
        // 4방향 탐색
        for (int dir = 0; dir < 4; dir++) {
            int nr = row;
            int nc = col;
            
            // 해당 방향으로 끝까지 탐색
            for (int i = 0; i < n ; i++) {
                nr = nr + mapper[0][dir];
                nc = nc + mapper[1][dir];
            
                // 인덱스 초과 시 다음 방향
                if (nr >= n || nc >= n || nr < 0 || nc < 0) break;
                if (isQueen[nr][nc]) return false;
            }
        }
        return true;
    }
}