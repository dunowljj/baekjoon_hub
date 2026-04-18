import java.util.*;

class Solution {
    public int solution(String message, int[][] spoiler_ranges) {
        int answer = 0;
        Set<String> preventWords = new HashSet<>(); // 스포 방지 단어
        Set<String> normalWords = new HashSet<>(); // 그냥 공개된 단어
        
        int rIdx = 0;
        boolean isPrevented = false;
        StringBuilder word = new StringBuilder();
        
        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            
            if (spoiler_ranges[rIdx][1] < i && rIdx < spoiler_ranges.length - 1) rIdx++;
            int[] range = spoiler_ranges[rIdx];
            
            // 공백 발견 -> 바로 이전 단어를 처리한다.
            if (ch == ' ') {
                if (isPrevented) {
                    preventWords.add(word.toString());
                    isPrevented = false;
                } else {
                    normalWords.add(word.toString());
                }
                
                word = new StringBuilder();
            
            // 소문자 or 숫자인 경우
            } else {
                if (range[0] <= i && i <= range[1]) {
                    isPrevented = true;
                }
                word.append(ch);
            }
        }
        
        // 처리안된 마지막 단어
        if (isPrevented) {
            preventWords.add(word.toString());
            isPrevented = false;
        } else {
            normalWords.add(word.toString());
        }
        
        preventWords.removeAll(normalWords);
        return preventWords.size();
    }
}

/**

알파벳 소문자, 숫자로 구성

문자 하나라도 가리면 스포방지단어

중요 단어 조건
- 스포방지단어
- 스포 방지에 속하지 않는 '어디에도' 등장한 적 없어야 함. -> 전체 단어를 먼저 검사해야한다.
- 동시 공개 시 왼쪽부터 중요단어 판별 -> 맨 왼쪽 첫 등장만 중요단어 가능


왼쪽부터 오픈 -> 맨 왼쪽에 스포방지단어는 중요단어이다. 단, 일반 단어중에 해당 단어가 없어야 한다.
중요 단어의 개수만 세면 되므로, '중복제거 스포방지단어'.removeAll('중복제거 일반 단어')를 하면, 결국 중요단어가 나온다.

*/