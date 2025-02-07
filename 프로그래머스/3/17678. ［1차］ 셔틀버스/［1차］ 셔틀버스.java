import java.util.*;
import java.util.stream.*;

class Solution {
    private static final int MAX_TIME = (24 * 60) - 1;
    
    public String solution(int n, int busTerm, int m, String[] timetable) {
        final int firstBusTime = 9 * 60;
        final int lastBusTime = firstBusTime + (n - 1) * busTerm;
        
        Map<Integer, Long> crewTimeCount = Arrays.stream(timetable)
            .map(this::toMinute)
            .filter(minute -> minute <= lastBusTime)
            .collect(Collectors.groupingBy(
                (time) -> time, 
                Collectors.counting())
            );
         
       
        Set<Integer> busTimes = new HashSet<>();
        for (int i = firstBusTime; i <= lastBusTime; i += busTerm) {
            busTimes.add(i);
        }
        
        int totalRemainSeat = n * m;
        int waiting = 0;
        int now = 0;
        
        for (now = 1; now <= lastBusTime; now++) {
            if (crewTimeCount.containsKey(now)) {
                waiting += crewTimeCount.get(now);
            }
            
            
            // 자리가 부족해 미리 줄서야하는 경우
            if (waiting >= totalRemainSeat) {
                return toTimeFormat(now - 1);
            }
            
            // now 시점에 콘이 도착한다면 버스를 탈 수 있는가?를 구분하는게 중요하다.
            // 마지막 탐색의 경우 좌석이 갑자기 0이 되면 무조건 결과를 반환하므로 불가능 체크 후 갱신한다.
            if (busTimes.contains(now)) {
                totalRemainSeat -= m; 
                waiting = Math.max(0, waiting - m);
            }
        }
        
        return toTimeFormat(lastBusTime);
    }
    
    private int toMinute(String time) {
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);
        return hour * 60 + minute;
    }
    
    private String toTimeFormat(int time) {
        int hour = time / 60;
        int minute = time % 60;
        
        StringBuilder sb = new StringBuilder();
        
        if (hour < 10) sb.append("0");
        sb.append(hour).append(":");
        
        if (minute < 10) sb.append("0");
        sb.append(minute);
        
        return sb.toString();
    }
}
/**
역 -> 회사
n회 t분 간격, 도착한 순간 탑승 가능
하나의 셔틀 m명

같은 시간 도착한 사람들 중 가장 뒤에 선다.
최대한 늦게 가고 싶다.

예제 2를 보면 중간에 빈 좌석이 있더라도, 더 늦은게 있다면 그때 탄다. 다른 크루가 못타더라도 상관없다

범위를 합치는 것도 좋지만, 그냥 배열을 쓰는게 훨씬 빠르고 직관적일듯하다.

못가는 경우는 없나? -> 크루 도착은 00:01부터이고, 콘은 00:00에 갈 수 있다.

*/