import java.util.*;

class Solution {
    
    private StringBuilder answer = new StringBuilder();
    private static final int[][] dirs = {{1, 0},{0, -1},{0, 1},{-1, 0}};
    private static final char[] dirCommands = {'d','l','r','u'};
    private static boolean isFinished = false;
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        
        
        int distance = getDistance(x, y, r, c);
        if (k < distance || (k - distance) % 2 == 1) return "impossible";
        
        dfs(n, m, x, y, r, c, k);
        return answer.toString().trim();
    }
    
    private void dfs(int n, int m, int x, int y, int r, int c, int k) {
        if (isFinished) return;
        
        if (k == 0) {
            if (x == r && y == c) isFinished = true;
            return;
        } 
        
        if (getDistance(x, y, r, c) > k) {
            return;
        }
        
        
        for (int i = 0; i < dirs.length; i++) {
            int nx = x + dirs[i][0];
            int ny = y + dirs[i][1];
            
            if (nx < 1 || nx > n || ny < 1 || ny > m) continue;
            
            answer.append(dirCommands[i]);
            dfs(n, m, nx, ny, r, c, k - 1);
            if (isFinished) return;
            
            answer.deleteCharAt(answer.length() - 1);
        }
        
    }
    
    private int getDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}


