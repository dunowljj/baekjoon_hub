class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = {};
        
        int total = brown + yellow;
        int whSum = (brown + 4) / 2; //brown == width * 2 + height * 2 - 4
        
        int width = whSum / 2;
        int height = whSum / 2;
        
        if (whSum % 2 == 1) width += 1;
        
        for (int i = 0; i < whSum / 2; i++) {
            int area = (width + i) * (height - i);
            
            if (area == total) return new int[]{width + i, height - i};
        }
        
        return answer;
    }
}
/*
가로 길이 >= 세로 길이


*/