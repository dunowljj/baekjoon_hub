import java.util.*;

class Solution {
    
    public int solution(String word) {
        final char[] alphabets = {'A', 'E', 'I', 'O', 'U'};
        List<String> dict = new ArrayList<>();
        
        // 모든 패턴 생성
        makeAll(dict, alphabets, word, new StringBuilder());
        
        // 정렬
        Collections.sort(dict);
        
        int answer = 0;
        for (String findWord : dict) {
            answer ++;
            if (findWord.equals(word)) break;
        }
        
        return answer;
    }
    
    private void makeAll(List<String> dict, char[] alphabets, String word, StringBuilder made) {
        if (made.length() == 5) return;
        
        for (int i = 0; i < alphabets.length; i++) {
            made.append(alphabets[i]);
            dict.add(made.toString());
            
            makeAll(dict, alphabets, word, made);
           
            made.deleteCharAt(made.length() - 1);
        }
    }
}
/*
A AA AAA AAAA AAAAA
AAAAE IOU
AAAE IOU
AAE IOU
AE IOU
*/