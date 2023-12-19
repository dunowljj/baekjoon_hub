import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        Map<String, Integer> map = new HashMap<>();
        
        // 각 의상 종류의 개수 세기
        for (String[] c : clothes) {
            String kind = c[1];
            map.put(kind, map.getOrDefault(kind, 0) + 1);
        }
        
        // 조합 개수 계산
        int comb = 1;
        for (int count : map.values()) {
            comb *= (count + 1);
        }
        
        return comb - 1;
    }
}