class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = {};

        // (노랑너비 + 1) * 2 + (노랑높이 + 1) * 2 = 갈색 블록 개수
        int yellow_w = 0;
        int yellow_h = 0;

        for (int h = 0; h < 2_000_000; h++) {
            for (int w = h; w < 2_000_000; w++) {
                if (h * w == yellow && (h + 1) * 2 + (w + 1) * 2 == brown) {
                    return new int[]{w + 2, h + 2};
                }
            }
        }

        return answer;
    }
}