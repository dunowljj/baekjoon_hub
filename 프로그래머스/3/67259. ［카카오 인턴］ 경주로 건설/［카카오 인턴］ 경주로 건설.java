import java.util.*;

class Solution {
    
    private static final int[][] mapper = {{1,0,-1,0}, {0,1,0,-1}}; // 남,동,북,서
    
    public int solution(int[][] board) {
        int n = board.length;
        int m = board[0].length;
        Integer[][][] dp = new Integer[n][m][4];
        
        Queue<Point> queue = new LinkedList<>();
        
        // 남,동 한칸 이동해서 시작
        if (board[1][0] == 0) queue.offer(new Point(1, 0, 0));
        if (board[0][1] == 0) queue.offer(new Point(0, 1, 1));

        dp[1][0][0] = 100; 
        dp[0][1][1] = 100;
        
        while (!queue.isEmpty()) {
            Point now = queue.poll();
            
            for (int nd = 0; nd < 4; nd++) {
                int ny = now.y + mapper[0][nd];
                int nx = now.x + mapper[1][nd];
                
                if (ny < 0 || ny >= n || nx < 0 || nx >= m || board[ny][nx] == 1) continue;
                
                int cost = 0;
                // 직선도로
                if (now.dir == nd) { 
                    cost = dp[now.y][now.x][now.dir] + 100;
                
                // 반대
                } else if ((now.dir + 2) % 4 == nd) {
                    continue;
                
                // 커브 + 직선(한칸)
                } else { 
                    cost = dp[now.y][now.x][now.dir] + 600;
                }
                
                if (dp[ny][nx][nd] == null || dp[ny][nx][nd] > cost) {
                    dp[ny][nx][nd] = cost;
                    queue.offer(new Point(ny, nx, nd));
                }
            }
        }

        int answer = Integer.MAX_VALUE;
        if (dp[n - 1][m - 1][0] != null) answer = Math.min(answer, dp[n - 1][m - 1][0]); 
        if (dp[n - 1][m - 1][1] != null) answer = Math.min(answer, dp[n - 1][m - 1][1]);
        if (dp[n - 1][m - 1][2] != null) answer = Math.min(answer, dp[n - 1][m - 1][2]);
        if (dp[n - 1][m - 1][3] != null) answer = Math.min(answer, dp[n - 1][m - 1][3]);
                
        return answer;
    }
    
    static class Point {
        int y;
        int x;
        int dir;
        
        public Point(int y, int x, int dir) {
            this.y = y;
            this.x = x;
            this.dir = dir;
        }
    }
}
/**
최단경로가 아닌 최소비용을 계산해야한다.
상/하로만 나누어서 dp해도 가능하다.
*/