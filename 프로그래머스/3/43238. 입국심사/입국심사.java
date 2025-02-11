class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;
        
        long lo = 0;
        long hi = Long.MAX_VALUE;
        
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
    
    private boolean canComplete(long minute, int[] times, int n) {
        long completeCount = 0;
        for (int time : times) {
            completeCount += minute / (long) time;
            if (completeCount >= n) return true;
        }
        
        return completeCount >= n;
    }
}

/**
시간내에 심사가 불가하다면?
*/