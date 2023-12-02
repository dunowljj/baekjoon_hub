class Solution {
    public int solution(int[] stones, int k) {
        int low = 1; // T
        int high = 200_000_000; // F

        while (low + 1 < high) {
            int mid = (low + high) / 2;

            if (canCrossBridge(mid, stones, k)) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return low;
    }

    // 주어진 passCount 값보다 작거나 같은 수를 가진 돌은 건널수 없다.
    // 문제는 passCount 만큼 지나갔을때, 건널수 있는지 여부를 판단하는 내용이다.
    // passCount만큼 딱 지나갔을때, 건널 수 없게 된 걸 수도 있다. -> -1 한 채로 검사
    private boolean canCrossBridge(int passCount, int[] stones, int k) {
        int seq = 0;
        passCount --;

        for (int i = 0; i < stones.length; i++) {
            if (cannotStep(stones[i], passCount)) {
                seq ++;
            } else {
                seq = 0;
            }

            if (seq == k) return false;
        }

        return true;
    }

    private boolean cannotStep(int stoneValue, int passCount) {
        return stoneValue <= passCount;
    }
}
/*
F..F..T..T
T의 최솟값
*/
/*
F..F..T..T
T의 최솟값
*/