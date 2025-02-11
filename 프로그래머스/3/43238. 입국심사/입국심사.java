class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;
        
        long lo = 0;
        long hi = 100_000_000_000_000L;
        
        // F F T T T
        while (lo < hi) {
            long mid = (lo + hi) / 2;
            
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
            completeCount += minute / time;
        }
        
        return completeCount >= n;
    }
}

/**
시간내에 심사가 불가하다면?
*/