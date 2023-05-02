import java.util.*;

// 모두 안입은 경우 -1 
// Map에 의상 종류를 key로 넣고 value + 1 갱신, 마지막에 +1 해서 곱하기
class Solution {
    public int solution(String[][] clothes) {
        Map<String, Integer> countMap = new HashMap<>();
        
        for (String[] c : clothes) {
            countMap.put(c[1], countMap.getOrDefault(c[1], 0) + 1);
        }
        
        int answer = 1;
        for (int count : countMap.values()) {
            answer *= count + 1;
        }
        
        return answer - 1;
    }
}