import java.util.Arrays;

class Solution {

    public int solution(int[] citations) {
        int n = citations.length;
        Arrays.sort(citations);

        int hIndex = 0;
        for (int i = 0; i < n; i++) {
            int quoteCount = citations[i];
            int upperCount = n - i;

            int temp = Math.min(quoteCount, upperCount);
            hIndex = Math.max(temp, hIndex);
        }
        return hIndex;
    }
}

// 이렇게 검사하면 하면 배열에 있는 값이 무조건 h-index가 된다.
// if (quote <= upperCount) {
// hIndex = Math.max(hIndex, quote);
// }