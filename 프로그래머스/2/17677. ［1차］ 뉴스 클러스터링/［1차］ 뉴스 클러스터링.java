import java.util.*;

class Solution {
    
    private static final int CONSTANT = 65536;
    
    public int solution(String str1, String str2) {
        int answer = 0;
        
        if (str1.length() == 0 && str2.length() == 0) return CONSTANT;
        if (str1.length() <= 1 || str2.length() <= 1) return 0;
        
        return findJ(str1.toUpperCase(), str2.toUpperCase());
    }
    
    private int findJ(String str1, String str2) {
        Map<String, Integer> count1 = getCountMap(str1);
        Map<String, Integer> count2 = getCountMap(str2);
        
        int crossCount = 0;
        int uniteCount = 0;
        
        for (String key : count1.keySet()) {
            if (count2.containsKey(key)) {
                int c1 = count1.get(key);
                int c2 = count2.get(key);
                
                crossCount += Math.min(c1, c2);   
                uniteCount += Math.max(c1, c2);
            } 
            else {
                uniteCount += count1.get(key);
            }
        }
        
        for (String key : count2.keySet()) {
            if (!count1.containsKey(key)) {
                uniteCount += count2.get(key);
            }
        }
        
        // System.out.println("cross:"+crossCount);
        // System.out.println("unite:"+uniteCount);
        
        if (crossCount == 0 && uniteCount == 0) return CONSTANT;
        if (crossCount == 0) return 0;
        else return crossCount * CONSTANT / uniteCount;
    }
    
    private Map<String, Integer> getCountMap(String str) {
        Map<String, Integer> count = new HashMap<>();
        
        for (int i = 0; i < str.length() - 1; i++) {
            char now = str.charAt(i);
            char next = str.charAt(i + 1);
            
            if (!isAlphabet(now) || !isAlphabet(next)) {
                continue;
            }
            
            String e = new String(str.substring(i, i+2));
            count.put(e, count.getOrDefault(e, 0) + 1);
        }
        
        return count;
    }
    
    private boolean isAlphabet(char ch) {
        return 'A' <= ch && ch <= 'Z';
    }
}

/**
A, B모두 공집합 -> J(A,B) = 1

J(A,B)는  AB교집합 나누기 AB합집합
각 문자의 카운트값끼리 min으로 비교하여 자카드 유사도를 구해라.
66536곱하고 정수부만 출력

str1,2 각 2~1000
대소문자 구분 X, 2개씩끊어서 읽기. 인덱스 주의 -> 대소문자 영문자만 사용. 공백, 특수문자 포함시 버린다.

*/