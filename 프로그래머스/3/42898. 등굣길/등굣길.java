class Solution {

    private final static long DIVIDER = 1_000_000_007;
    private final static long PUDDLE = -1;
    
    public int solution(int m, int n, int[][] puddles) {
        long[][] grid = new long[n][m];
        
        for (int[] puddle : puddles) {
            int py = puddle[1] - 1;
            int px = puddle[0] - 1;
            
            grid[py][px] = PUDDLE;
        }
        
        
        // 첫줄 물에 잠긴 지역 발견 시, 해당 줄의 그 이후부분은 방문 불가이므로 숫자를 세줄 필요 없다.
        for (int y = 0; y < n; y++) {
            if (grid[y][0] == PUDDLE) break;
            grid[y][0] = 1;
        }
        
        for (int x = 0; x < m; x++) {
            if (grid[0][x] == PUDDLE) break;
            grid[0][x] = 1;
        }
        
        
        for (int i = 1; i < n; i++) { 
            for (int j = 1; j < m; j++) {
                if (grid[i][j] == PUDDLE) continue;
                
                if (grid[i - 1][j] == PUDDLE && grid[i][j - 1] == PUDDLE) {
                    continue;
                }
                
                // 둘중 하나만 PUDDLE인 경우
                else if (grid[i - 1][j] == PUDDLE || grid[i][j - 1] == PUDDLE) {
                    grid[i][j] = Math.max(grid[i - 1][j], grid[i][j - 1]) % DIVIDER;
                    continue;
                }
                
                grid[i][j] = (grid[i - 1][j] + grid[i][j - 1]) % DIVIDER;
            }
        }
        
        return (int) (grid[n - 1][m - 1]);
    }
    
    // 오른쪽과 아래쪽으로만 이동
    // 최단 경로의 개수 % 1_000_000_007
}