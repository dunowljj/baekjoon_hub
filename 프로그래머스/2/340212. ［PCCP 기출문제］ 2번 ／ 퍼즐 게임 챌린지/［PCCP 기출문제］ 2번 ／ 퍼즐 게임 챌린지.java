class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        int lo = 1;
        int hi = 100_000;
        
        // 레벨이 높아지다보면 어느순간 limit을 만족
        // 첫 T 찾기
        // FFF -> 주어지지 않음
        // TTT
        // F F F F T T T
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            
            if (!canSolveInTime(mid, diffs, times, limit)) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        
        return lo;
    }
    
    public boolean canSolveInTime(int level,int[] diffs, int[] times, long limit) {
        long time = times[0];
        
        for (int i = 1; i < diffs.length; i++) {
            if (diffs[i] <= level) time += times[i];
            else time += (diffs[i] - level) * (times[i] + times[i - 1]) + times[i];            
        }
        
        return limit >= time;
    }
}
/**

숙련도 -> 틀리는 횟수

(diff - level) * (time_cur + time_prev) + time_cur


퍼즐 개수 최대 30만 
diff 값 1~10만
time 1~1만

10만 * 2만 -> 20억 + time_cur -> int 가능

30만에서 10만가지 모두 하면 300억이다. 이분탐색으로 줄여줘야한다.

최소 만족 숙련도 (level) 찾기


이전 퍼즐이 없는 경우는 어떻게? -> diffs[0] = 1이므로 항상 이전 퍼즐이 존재
*/