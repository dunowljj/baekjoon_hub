import java.util.*;
import java.util.stream.*;

class Solution {
    List<Integer> answer = new ArrayList<>();
    
    public int[] solution(String msg) {
        Map<String, Integer> dict = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            String w = (char)('A' + i) + "";
            dict.put(w , i + 1);
        }

        for (int i = 0; i < msg.length();) {
            int interval = check(i, msg, dict);
            i += interval;
        }
        
        return answer.stream()
            .mapToInt(Integer::intValue)
            .toArray();
    }
    
    /** 
     * 시작점부터 사전에 일치하는 내용이 있는지 체크
     * 일치 -> 값 before에 저장, 다음 일치 탐색
     * 불일치 -> 인덱스 반환, 다음거랑 더해서 사전에 추가
     */
    private int check(int start, String msg, Map<String, Integer> dict) {
        String before = "";
        for (int i = start + 1; i <= msg.length(); i++) {
            String now = msg.substring(start, i);
            
            // 해당 키가 존재하지 않으면 이전 값을 사전에 추가
            if (!dict.containsKey(now)) {
                answer.add(dict.get(before));
                
                dict.put(now, dict.size() + 1); // 사전 등재
                
                return before.length();
            }
            
            before = now;
        }
        
        // 마지막이 처리 안된 경우 -> 마지막에 2개 이상의 문자인 경우도 고려해야 한다.
        if (dict.containsKey(before)) {
            answer.add(dict.get(before));
            return before.length();
        }
        
        // unreachable
        return 0;
    }
}

/*
결국 사전의 형태를 어떻게 만들것인가? 일수 있다.

msg는 1~1000글자이다. 완전탐색해도 100만이다.
*/