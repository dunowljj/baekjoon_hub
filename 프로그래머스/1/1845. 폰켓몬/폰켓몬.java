import java.util.*;
import java.util.stream.*;

class Solution {
    public int solution(int[] nums) {
        int half = nums.length / 2;
        int maxMonsterKind = getMaxMonsterKind(nums);
        
        return Math.min(half, maxMonsterKind);
    }
    
    private int getMaxMonsterKind(int[] nums) {
        return Arrays.stream(nums)
            .boxed()
            .collect(Collectors.toSet())
            .size();
    }
}

/**
3, 1, 2 ,3 
가장 많은 종류의 폰켓몬을 선택하는 방법일때 종류의 개수

Set으로 종합하고, 개수가 최댓값이다.
**/