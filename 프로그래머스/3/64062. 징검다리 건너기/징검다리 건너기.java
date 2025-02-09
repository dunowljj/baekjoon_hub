import java.util.*;

class Solution {
    public int solution(int[] stones, int k) {
        
        int lo = 0;
        int hi = 200_000_000;
        
        // 첫 F구하기
        // T T T F F F
        // T T T -> 계속 건널수 있는 경우는 없음
        // F F F -> 맨왼쪽 F
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (canCross(mid, stones, k)) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        
        return lo;
    }
    
    public boolean canCross(int passCount, int[] stones, int k) {
        int seqCount = 0;
        int maxSeq = 0;
        for (int size : stones) {
            // useCount만큼 지나가면 0인 경우
            if (size <= passCount) {
                seqCount++;
                maxSeq = Math.max(seqCount, maxSeq);
            } else {
                seqCount = 0;
            }
        }
        
        return maxSeq < k;
    }
}
/*
밟으면 1줄어듬
0이면 스킵하고 넘어감. 가장 가까운 디딤돌로만 점프 가능

건너야하는 친구는 무제한.
돌 1~20만
k는 돌개수 이하

각 원소 2억 이하

슬라이딩 윈도우로 탐색하면서, TreeMap으로 세주기 -> k가 5만이라면, 15만번 sliding을 해야하므로, 15만*20만*log(5만) -> 시간초과

이분탐색 사용시 log(2억) * 20만으로 훨씬 빠르다.

*/