import java.util.*;

class Solution {
    
    private static final int INF = 1000;
    
    public int solution(int[][] info, int n, int m) {
        int[][] dp = new int[info.length + 1][m];
    
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], INF);
        }
        
        dp[0][0] = 0;
        
        for (int i = 1; i <= info.length; i++) {
            int a = info[i - 1][0];
            int b = info[i - 1][1];
            
            for (int j = 0; j < m; j++) {
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + a);
                
                if (j + b < m) {
                    dp[i][j + b] = Math.min(dp[i][j + b], dp[i - 1][j]);
                }
            }
        }
        
        int answer = INF;
        for (int i = 0; i < m; i++) {
            answer = Math.min(dp[info.length][i], answer);
        }
        
        return answer >= n ? -1 : answer;
    }
}


/**
탐색?
2^40은 너무 많다.

정렬?
[1,1] [1,1] vs [2, 3] --> 후자를 훔치는게 이득일 수 있음

dp
A의 흔적은 n개 미만이어야 함
B의 흔적은 m개 미만이어야 함




*/