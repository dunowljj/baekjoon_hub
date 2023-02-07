import java.util.*;

class Solution {
    static final int MAX_MINUTES = 24 * 60;
    
    public int solution(String[][] book_time) {
        int[] count = new int[MAX_MINUTES];
        
        for (String[] times : book_time) {
            int startMinutes = getMinutes(times[0]);
            int endMinutes = getMinutes(times[1]) + 9;
            
            endMinutes = (endMinutes >= MAX_MINUTES) ? MAX_MINUTES - 1 : endMinutes; // 10분 추가때문에 최대시간 넘어가면 범위 조정
            
            for (int i = startMinutes; i <= endMinutes; i++) {
                count[i]++;
            }
        }
        
        int answer = 0;
        for (int c : count) {
            answer = Math.max(c, answer);
        }
        
        return answer;
    }
    
    private int getMinutes(String time) {
        String[] hoursAndMinutes = time.split(":");
        return Integer.parseInt(hoursAndMinutes[0]) * 60 + Integer.parseInt(hoursAndMinutes[1]);
    }
}

/*
동시에 겹치는 구간의 개수를 어떻게 구할까?

24 * 60개의 배열을 선언해서 카운트하면 해결되긴 한다.

아니면 book_time을 순회하면서 더 작은 배열로 합쳐나가는건 어떤가? -> 순회량이 너무 많을듯
*/