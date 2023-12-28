import java.util.Arrays;
import java.util.stream.IntStream;

class Solution {

    public int solution(int[] citations) {
        int n = citations.length;
        Arrays.sort(citations);

        int hIndex = 0;
        for (int i = 0; i < n; i++) {   
            int h = citations[i];
            int upperCount = n - i;

            int temp = Math.min(h, upperCount);
            hIndex = Math.max(temp, hIndex);
        }

        return hIndex;
    }
}
