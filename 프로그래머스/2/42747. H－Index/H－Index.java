import java.util.Arrays;
import java.util.stream.IntStream;

class Solution {

    public int solution(int[] citations) {
        int count = citations.length;
        Arrays.sort(citations);

        int maxH = 0;
        for (int i = 0; i < count; i++) {
            int candidate = citations[i];
            int upperCount = count - i;
            
            if (candidate >= upperCount) {
                return upperCount;
            }
        }
            
        return maxH;
    }
}
