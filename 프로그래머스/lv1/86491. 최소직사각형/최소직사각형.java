
import java.util.*;

class Solution {
    public int solution(int[][] sizes) {
        
        for (int i = 0; i < sizes.length; i++) {
            Arrays.sort(sizes[i]);
        }    
        
        int widthMax = 0;
        int heightMax = 0;
        
        for (int i = 0; i < sizes.length; i++) {
            widthMax = Math.max(sizes[i][0], widthMax);
            heightMax = Math.max(sizes[i][1], heightMax);
        }

        return widthMax * heightMax;
    }
    
   
    
   
}
