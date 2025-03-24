import java.util.*;

class Solution {
    
    
    
    public int solution(int n, int m, int[][] edge_list, int k, int[] gps_log) {
        final int INF = 1000;
        int answer = 0;
        
        List<Integer>[] adj = new ArrayList[n + 1];
        for (int i = 0; i < adj.length; i++) adj[i] = new ArrayList<>();
        
        for (int[] e : edge_list) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }
        
        // 머무르기 위한 정보 갱신
        for (int i = 1; i <= n; i++) {
            adj[i].add(i);
        }
        
        int[][] dp = new int[k][n + 1];
        for (int i = 0; i < k - 1; i++) {
            Arrays.fill(dp[i], INF);
        }
        
        // 마지막 시점 정보 갱신
        Arrays.fill(dp[k-1], -1);
        dp[k-1][gps_log[k-1]] = 0; // 도착점은 변경 불가능으로, 정확히 도착점에 가는 경우만 가능
        
        // 시간 0~k-1 -> gps_log의 인덱스와 일치
        for (int t = k - 2; t >= 0; t--) {
            int originNode = gps_log[t]; // 해당 시점 기존 노드
            
            // 특정 시점(t)에 특정 노드로 바꿨을 경우에 대한 dp값 갱신
            for (int newNode = 1; newNode <= n; newNode++) {
               
                
                // 바꾼 노드의 인접 노드를 탐색. t+1시점의 인접노드의 값을 이용해 dp값 갱신
                boolean canAccessEnd = false;
                for (int nextAdjNode : adj[newNode]) {
                    if (dp[t + 1][nextAdjNode] == -1) continue; // 끝에 도달할 수 없는 노드면 무시
                    
                    canAccessEnd = true;    
                    
                    // 자기자신이므로 수정하지 않은 경우
                    if (originNode == newNode) {
                        dp[t][newNode] = Math.min(dp[t][newNode], dp[t + 1][nextAdjNode]);
                        
                    // t지점에서 오류를 수정한 경우
                    } else {
                        dp[t][newNode] = Math.min(dp[t][newNode], dp[t + 1][nextAdjNode] + 1);                    
                    }
                }
                
                // 마지막 지점에 도달 가능한 시점의 노드가 없으므로 불가능처리
                if (!canAccessEnd) {
                    dp[t][newNode] = -1;
                }
            }
        }
        
        return dp[0][gps_log[0]];
    }
}
/**
위치 오류가 있으나 승하차 위치는 오류가 없음
교통에 따라 한 거점에 머무르거나 되돌아가기 가능. 양방향도로

gps_log의 시작,끝은 정확함 k가 100까지 가능하므로 모든 조합의 탐색은 너무 많다.


[1,2,3,5,6,7]

dp[6][7] = 0;
dp[6][!7] = -1

dp[5][6] = dp[6][7];
dp[5][5] = dp[6][7]+ 1 = 1
dp[5][1~4] = dp[6][7]에 도달 불가 = -1;

// 4번째 6을 그대로 사용
dp[4][6] = when dp[5][x] != -1 -> Math.min(dp[5][x]) = 0;

// 4번째 6을 5로 수정
dp[4][5] = when dp[5][x] != -1 -> Math.min(dp[5][x]) + 1 = 1;


도착점이 자기 자신인 경우도 포함해야함.
*/