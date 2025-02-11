class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;
        
        long lo = 0;
        long hi = (long) Math.pow(10, 18);
        
        // F F T T T
        while (lo < hi) {
            long mid = (hi - lo) / 2 + lo;
            
            if (canComplete(mid, times, n)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        
        return lo;
    }
    
    private boolean canComplete(long mid, int[] times, int n) {
        long completeCount = 0;
        for (int time : times) {
            completeCount += mid / (long) time;
            if (completeCount >= n) return true;
        }
        
        return completeCount >= n;
    }
}

/**
시간내에 심사가 불가하다면?
1명 심사관이 10억분이 걸려서 10억명을 심사한다면? -> 최대 10^18
*/