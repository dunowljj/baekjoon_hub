import java.util.*;

class Solution {
    
    Queue<int[]> queue = new LinkedList<>();

    public int solution(int alp, int cop, int[][] problems) {
        int answer = Integer.MAX_VALUE;
        
        int[][] dp = new int[151][151];
        
        int max_alp = 0;
        int max_cop = 0;
        for (int[] problem : problems) {
            if (max_alp < problem[0]) max_alp = problem[0];
            if (max_cop < problem[1]) max_cop = problem[1];
        }
        
        if (max_alp <= alp && max_cop <= cop) return 0;
        
        for (int i = alp; i <= max_alp; i++) {
            for (int j = cop; j <= max_cop; j++) {
                solve(dp, i, j, problems, max_alp, max_cop);
            }
        }

        // 상승한 다음 값부터 탐색하면 된다. 그 값을 큐에 넣는다면?
        queue.offer(new int[]{alp, cop});
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            alp = curr[0];
            cop = curr[1];
            
            if (alp == max_alp && cop == max_cop) continue;
            solve(dp, alp, cop, problems, max_alp, max_cop);
        }

        return dp[max_alp][max_cop];
    }
    

    private void solve(int[][] dp, int alp, int cop, int[][] problems, int max_alp, int max_cop) {
        // 현재 값에 풀 수 있는 모든 문제에 대해 적용, 공부도 포함
        for (int[] problem : problems) {
            
            if (canSolve(alp, cop, problem)) {
                int risedAlp = Math.min(alp + problem[2], max_alp);
                int risedCop = Math.min(cop + problem[3], max_cop);
                int risedTime = dp[alp][cop] + problem[4];

                solve(dp, risedAlp, risedCop, risedTime);
            }
        }

        if (alp < max_alp) study(dp, alp + 1, cop, dp[alp][cop] + 1);
        if (cop < max_cop) study(dp, alp, cop + 1, dp[alp][cop] + 1);
    }
    
    private boolean canSolve(int alp, int cop, int[] problem) {
        return alp >= problem[0] && cop >= problem[1];
    }    
    
    private void study(int[][] dp, int risedAlp, int risedCop, int risedTime) {
        solve(dp, risedAlp, risedCop, risedTime);
    }
    
    private void solve(int[][] dp, int risedAlp, int risedCop, int risedTime) {
        if (dp[risedAlp][risedCop] == 0) {
            dp[risedAlp][risedCop] = risedTime;
            queue.offer(new int[]{risedAlp, risedCop});
        }

        // 시간이 이미 존재하면, 더 낮은 시간으로 대체
        else {
            dp[risedAlp][risedCop] = Math.min(dp[risedAlp][risedCop], risedTime);  
        }
    }
}
/*
알고력 혹은 코딩력을 올릴 방법은 8가지이다. 
- 최대 6개의 문제가 주어진다. 
- 그냥 공부도 있으니 2가지 경우가 더 추가된다.


*/