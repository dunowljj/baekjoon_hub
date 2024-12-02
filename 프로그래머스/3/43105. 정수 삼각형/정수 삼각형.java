class Solution {
    public int solution(int[][] triangle) {
        int h = triangle.length;
        int[][] dp = new int[h][triangle[h - 1].length];
        
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < triangle[y].length; x++) {
                dp[y][x] = triangle[y][x];
            }
        }
        
        for (int y = 0; y < h - 1; y++) { // 마지막줄은 탐색할 필요없다.
            for (int x = 0; x < triangle[y].length; x++) { // 삼각형 모양만큼만 순회한다.
                int now = triangle[y][x];
                
                // nextY
                int ny = y + 1;
                
                // leftX
                if (dp[ny][x] < dp[y][x] + triangle[ny][x]) {
                    dp[ny][x] = dp[y][x] + triangle[ny][x];
                }
                
                // rightX
                int rx = x + 1; 
                if (rx >= triangle[y + 1].length) continue;
                if (dp[ny][rx] < dp[y][x] + triangle[ny][rx]) {
                    dp[ny][rx] = dp[y][x] + triangle[ny][rx];
                }
            }
        }
        
        int max = 0;
        for (int x = 0; x < triangle[h - 1].length; x++) {
            max = Math.max(dp[h - 1][x], max); 
        }
        
        // for (int y = 0; y < h; y++) {
        //     for (int x = 0; x < triangle[y].length; x++) {
        //         System.out.print(dp[y][x] +" ");
        //     }
        //     System.out.println();
        // } 
        
        return max;
    }
}

/**
반대로 위로 올라가게도 구현 가능하다.
*/