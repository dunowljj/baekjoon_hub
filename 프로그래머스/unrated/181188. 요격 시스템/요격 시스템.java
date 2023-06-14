import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        
        Arrays.sort(targets, (t1, t2) -> t1[1] - t2[1]);
        
        int answer = 1;        
        int last = targets[0][1] - 1;
        
        for (int i = 1; i < targets.length; i++) {
            if (targets[i][0] <= last && last < targets[i][1]) continue;
            
            answer++;
            last = targets[i][1] - 1;
        }
        
        return answer;
    }
}