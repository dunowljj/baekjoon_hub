import java.util.*;

class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        Set<Integer> set = new HashSet();
        
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);    
        }
        
        answer = Math.min(set.size(), nums.length/2);
        
        return answer;
    }
}
/*
N은 항상 짝수이다.
중복이 제거된 집합의 크기와 N/2중 작은 값이 최댓값이다.
*/