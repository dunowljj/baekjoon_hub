import java.util.*;

class Solution {
    
    List<Integer>[] adj;
    int[][] dp;
    
    public int solution(int[] sales, int[][] links) {
        int answer = 0;
         
        int len = sales.length;
        adj = new ArrayList[len + 1];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }
        
        for (int[] link : links) {
            adj[link[0]].add(link[1]);
        }
        
        dp = new int[len + 1][2];
        for (int i = 1; i < len + 1; i++) {
            dp[i][1] = sales[i - 1];
        }
        
        find(1);
        return Math.min(dp[1][0], dp[1][1]);
    }
    
    public void find(int now) {
        if (adj[now].size() == 0) {
            return;
        }
        
        // [now][0]일때는 최소 1개의 [next][1]이 포함되어야 한다. 
        // -> 우선 작은 것을 넣되,
        // -> 최소 한 개의 [next][1]을 포함시키기 위해 [0]과[1]의 최소 diff를 구한다.
        // [now][1]일때는, [0],[1]중 더 작은 것만 들어가면 된다.
        int remain = Integer.MAX_VALUE;
        for (int next : adj[now]) {
            
            find(next);
            
            if (dp[next][0] > dp[next][1]) {
                dp[now][0] += dp[next][1];
                dp[now][1] += dp[next][1];
                
                remain = 0; // 자식 중 하나라도 [1]을 포함시켰다면, 나머지를 더해줄 필요가 없다.
                continue;
            } 
            
            if (dp[next][0] <= dp[next][1]) {
                dp[now][0] += dp[next][0];
                dp[now][1] += dp[next][0];
                
                remain = Math.min(remain, dp[next][1] - dp[next][0]);
                continue;
            }
        }
        
        dp[now][0] += remain;
    }
}
/**
루트는 CEO로 팀장임
팀장은 1명, 
최대 2팀에 소속 가능. 하나의 팀에선 팀장, 하나의 팀에선 팀원이어야 함
- 2개팀 팀장 겸업 불가. 2개 팀 팀원 불가
- 진입 1 진출 1

dp[n][01] 해당 노드가 포함/불포함시 최솟값?



*/