import java.util.*;

class Solution {
    public int solution(int N, int number) {
        Map<Integer, Set<Integer>> dp = new HashMap<>(); // <사용 횟수, 결과들>
        
        for (int i = 1; i <= 8; i++) {
           dp.put(i, new HashSet<>());
        }
        
        for (int i = 1; i <= 8; i++) {
            int repeatNum = repeat(N, i);
            dp.get(i).add(repeatNum);
            
            for (int j = 1; j < i; j++) {
                for (int a : dp.get(j)) {
                    for (int b : dp.get(i - j)) {
                        dp.get(i).add(a + b);
                        dp.get(i).add(a * b);
                        dp.get(i).add(a - b);
                        
                        if (b != 0) dp.get(i).add(a / b);
                    }
                }
            }
            
            if (dp.get(i).contains(number)) {
                return i;
            }
        }
        
        return -1;
    }
    
    private int repeat(int N, int i) {
        int result = 0;
        
        while(i-- > 0) {
            result += N * Math.pow(10, i);
        }
        
        return result;
    }
}
/**

11 111 1111 11111

9 7개-> 999만 *9 = 8~9천만 -> int 범위 이내

N을 i번
*/