import java.util.*;

class Solution {
    public int solution(String[] words) {
        int answer = 0;
        
        Arrays.sort(words);
        
        for (int i = 0; i < words.length; i++) {
            int len = 0;
            if (i == 0) {
                len = getDuplicatedLen(words[i], words[i + 1]);
            } else if (i == words.length - 1) {
                len =  getDuplicatedLen(words[i - 1], words[i]);
            } else {
                int l1 = getDuplicatedLen(words[i - 1], words[i]);
                int l2 = getDuplicatedLen(words[i], words[i + 1]);
                
                len = Math.max(l1, l2);
            }
                 
            int count = Math.min(len + 1, words[i].length());
            
            answer += count;
        }
        
        return answer;
    }
    
    private int getDuplicatedLen(String before, String now) {
        int minLen = Math.min(before.length(), now.length());

        int count = 0;
        for (int i = 0; i < minLen; i++) {
            if (before.charAt(i) == now.charAt(i)) {
                count++;
            } else {
                break;
            }
        }
        
        return count; 
    }
}