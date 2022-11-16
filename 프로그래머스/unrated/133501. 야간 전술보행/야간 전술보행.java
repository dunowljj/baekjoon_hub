import java.util.*;

class Solution {
    int min = Integer.MAX_VALUE;
    
    public int solution(int distance, int[][] scope, int[][] times) {
        
        for (int i = 0; i < times.length; i++) {
            // 근무시간 범위
            int period = times[i][0] + times[i][1];
        
            // 3~4 -> 1~2 / 3~8. p:7
            // 5~8 -> 1~4 / 5~8. p:7 
            
            Arrays.sort(scope[i]);
            for (int j = scope[i][0]; j <= scope[i][1]; j++) {
                if (j % period == 0) continue;
                
                if (j % period <= times[i][0]) {
                    min = Math.min(j, min);
                }
            }
        }
        return min == Integer.MAX_VALUE ? distance : min;
    }
}
/*
time은 1~10이다.
times : [2,5]
--> 1~2 감시, 3~8 휴식 9~10감시, 11~16휴식  (period + 1) 씩 더한 값
최대이동거리 구해야한다!

scope[i]는 정렬되어 있지 않을 수 있습니다(예시 2번 참조).
모든 경우 탐색하고 가장 작은 값 찾아내야 한다. 아니면 1000개짜리 scope와 times를 묶어서 정렬해야한다.
*/