class Solution {
    private static final int MOD = 20170805;
    
    private static final int FREE = 0;
    private static final int PROHIBIT = 1;
    private static final int STRAIGHT_ONLY = 2;
    
    private static final int FROM_UP = 0;
    private static final int FROM_LEFT = 1;
    
    Integer[][][] dp;
    int[][] city;
    int maxY, maxX;
    
    public int solution(int m, int n, int[][] cityMap) {
        city = cityMap;
        maxY = m;
        maxX = n;
        dp = new Integer[m][n][2]; // [y][x]에 도달하기 위해 위에서 온 경로 수 합[0], 왼쪽에서 온 경로 수 합[1]
        dp[0][0][0] = 1;
        dp[0][0][1] = 0;
        
        return (recur(m-1,n-1,0) + recur(m-1,n-1,1)) % MOD;
    }
    
    
    // dir =>  0:위->아래 , 1:왼->오
    private static final int[][] mapper = {{-1,0},{0, -1}};
    
    public int recur(int y, int x, int dir) {
        if (city[y][x] == PROHIBIT) return dp[y][x][dir] = 0;
        
        if (dp[y][x][dir] != null) return dp[y][x][dir];
        dp[y][x][dir] = 0;
        
        int by = y + mapper[0][dir];
        int bx = x + mapper[1][dir];
        
        if (by < 0 || bx < 0) return 0;
        
        if (city[by][bx] == FREE) {
            dp[y][x][dir] += recur(by, bx, 0) + recur(by, bx, 1);
        }
                
        else if (city[by][bx] == STRAIGHT_ONLY) {
            dp[y][x][dir] += recur(by, bx , dir);
        }
        
        return dp[y][x][dir] = dp[y][x][dir] % MOD; 
    }
}
/**
new : 자동차 통햄 금지, 우회전 금지

0 자유
1 통행 불가
2 좌회전, 우회전 금지 -> 가던 방향에서 직진만 가능

이동 가능한 전체 경로 수? 오른쪽 또는 아래로만 이동 가능하다.

20170805로 나누자


**/