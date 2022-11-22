class Solution {
    int removeCount = 0;
    int changeCount = 0;
    
    public int[] solution(String s) {
        
        while (!s.equals("1")) {
            int numOfOne = count(s); 
            s = Integer.toBinaryString(numOfOne);
            changeCount++;
        }
        
        return new int[]{changeCount, removeCount};
    }
    
    private int count(String s) {
        int count = 0;
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') count++;
            else removeCount++;
        }
        
        return count;
    }
}