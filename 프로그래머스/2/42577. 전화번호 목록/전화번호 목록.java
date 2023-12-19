import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        Set<String> set = new HashSet<>();
        Arrays.sort(phone_book);
        
        for (String phone_number : phone_book) {
            
            if (hasPrefix(set, phone_number)) {
                return false;
            }
            
            set.add(phone_number); 
        }
        
        return true;
    }
    
    private boolean hasPrefix(Set<String> set, String phone_number) {
        for (int i = 0; i < phone_number.length(); i++) {
            String sub = phone_number.substring(0, i + 1);   
            
            if (set.contains(sub)) {
               return true;  
            }
        }
        
        return false;
    }
}

/**
각 번호를 substring해서 set에서 검색하기
- 중복 번호는 없다.
- 자기 자신을 검색해서는 안된다.
*/