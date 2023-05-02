import java.util.stream.*;
import java.util.*;

class Solution {
     // hash
     public boolean solution(String[] phone_book) {
        Map<String, Integer> map = new HashMap<>();
        for (String pNum : phone_book) {
            map.put(pNum, Integer.MAX_VALUE);
        }
        
        for (int i = 0; i < phone_book.length; i++) {
            for (int j = 0; j < phone_book[i].length(); j++) {
                String subNum = phone_book[i].substring(0,j);
                if (map.containsKey(subNum)) return false;
            }
        }
            
        return true;
    }
    
    /*
    // sort
    public boolean solution(String[] phone_book) {
        Arrays.sort(phone_book);
        
        for (int i = 1; i < phone_book.length; i++) {
            if (phone_book[i].startsWith(phone_book[i - 1])) return false;
        }
            
        return true;
    }*/
}