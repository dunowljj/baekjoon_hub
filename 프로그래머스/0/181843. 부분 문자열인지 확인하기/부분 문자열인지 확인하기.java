class Solution {
    public int solution(String my_string, String target) {
        int mLen = my_string.length();
        int tLen = target.length();
        
        if (mLen < tLen) return 0;
        
        int mIdx = 0;
        int tIdx = 0;
        while (tIdx < tLen && mIdx < mLen) {
            
            char m = my_string.charAt(mIdx);
            char t = target.charAt(tIdx);
            
            if (m == t) {
                tIdx++;
                if (tIdx == tLen) return 1;
            }
            else {
                mIdx -= tIdx;
                tIdx = 0;
            }
            
            mIdx++;
        }
        
        return 0;
    }
}