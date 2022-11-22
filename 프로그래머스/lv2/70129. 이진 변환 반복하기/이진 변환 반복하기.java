class Solution {
    int removeCount = 0;
    int changeCount = 0;
    
    public int[] solution(String s) {
        
        while (!s.equals("1")) {
            int zeroCount = s.length();
            
            s = s.replace("0", "");
            zeroCount -= s.length();
            removeCount += zeroCount;
            
            int oneCount = s.length();
            s = Integer.toBinaryString(oneCount);
            
            changeCount++;
        }
        
        return new int[]{changeCount, removeCount};
    }
}