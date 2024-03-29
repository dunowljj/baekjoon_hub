import java.util.Arrays;

class Solution {
    public int solution(int[][] sizes) {
        int answer = 0;

        int max_w = 0;
        int max_h = 0;
        for (int i = 0; i < sizes.length; i++) {
            Arrays.sort(sizes[i]);
            max_w = Math.max(max_w, sizes[i][0]);
            max_h = Math.max(max_h, sizes[i][1]);
        }


        return max_w * max_h;
    }
}