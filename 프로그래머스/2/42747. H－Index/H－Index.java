import java.util.Arrays;
import java.util.stream.IntStream;

class Solution {

    public int solution(int[] citations) {
        int n = citations.length;
        Arrays.sort(citations);

        int hIndex = 0;
        for (int i = 0; i < n; i++) {   
            int citationCount = citations[i];
            int upperCount = n - i;

            // // 이렇게 하면 중간에 값이 반영안된다.
            // if (citationCount <= upperCount) {
            //     hIndex = Math.max(h, citationCount);   
            // }
            
            int temp = Math.min(citationCount, upperCount);
            hIndex = Math.max(temp, hIndex);
        }

        return hIndex;
    }
}
