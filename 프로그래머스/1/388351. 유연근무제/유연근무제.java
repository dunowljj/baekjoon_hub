import java.util.*;

class Solution {
    public int solution(int[] schedules, int[][] timelogs, int startday) {
        int answer = 0;
        
        for (int i = 0; i < timelogs.length; i++) {
            if (canBeAwarded(schedules[i], timelogs[i], startday)) {
                answer++;
            }
        }
        
        return answer;
    }
    
    private boolean canBeAwarded(int schedules, int[] timelog, int startday) {
        for (int j = 0; j < 7; j++) {
            int dayOfWeek = (startday + j) % 7;
            
            // 토요일 혹은 일요일
            if (dayOfWeek == 6 || dayOfWeek == 0) continue; 
            
            if (!commuteInTime(schedules, timelog[j])) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean commuteInTime(int schedules, int timelog) {
        return timeToMinute(schedules) + 10 >= timeToMinute(timelog);
    }
    
    private int timeToMinute(int time) {
        return (time / 100 * 60) + (time % 100);
    }
}
/**
일주일 동안 a+10이내에 출근한 직원들 찾기
토요일, 

*/