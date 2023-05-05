import java.util.*;

class Solution {
    
    int answer = 0;
    
    public int solution(int k, int[][] dungeons) {
        int len = dungeons.length;
        boolean[] visited = new boolean[len];
        findMax(dungeons, visited, len, 0, k);
        
        return answer;
    }
    
    private void findMax(int[][] dungeons, boolean[] visited, int len, int count, int k) {
        
        for (int i = 0; i < len; i++) {
            if (!visited[i]) {
                visited[i] = true;
                
                int need = dungeons[i][0];
                int use = dungeons[i][1];
                if (need <= k) {
                    findMax(dungeons, visited, len, count + 1, k - use);
                    answer = Math.max(answer, count + 1);
                } else {
                    findMax(dungeons, visited, len, count, k);
                }
                
                visited[i] = false;
            }
        }
    }
}