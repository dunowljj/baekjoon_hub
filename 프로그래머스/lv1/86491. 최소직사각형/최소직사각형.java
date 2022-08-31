
import java.util.*;

class Solution {
    public int solution(int[][] sizes) {
        int answer = Integer.MAX_VALUE;
        
        for (int i = 0; i < sizes.length; i++) {
            Arrays.sort(sizes[i]);
        }
        
        
        for (int i = 0; i < sizes.length; i++) {
            answer = Math.min(answer,getMax(sizes));    
        }
        
        return answer;
    }
    
   
    
    public int getMax(int[][] sizes) {
        int maxW = 0;
        int maxH = 0;

        for (int i = 0; i < sizes.length; i++) {
            maxW = Math.max(maxW, sizes[i][0]);
            maxH = Math.max(maxH, sizes[i][1]);
        }
        
        return maxW * maxH;
    }
}

/*
명함의 개수 : 1이상 10_000이하
w,h <= 1_000 자연수
뒤집는 모든 경우의 수? -> 드럽게 많다.

해결 : w혹은 h로 큰 수를 모두 몰아준다음 구하면, 최솟값이 된다.


*/