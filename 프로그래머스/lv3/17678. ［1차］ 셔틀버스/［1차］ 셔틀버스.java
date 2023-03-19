class Solution {
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        int[] arrivals = new int[24 * 60];
        for (String time : timetable) {
            int minute = toMinute(time);
            arrivals[minute]++;
        }
            
        int firstBus = (9 * 60);
        int lastBus = firstBus + (n - 1) * t;
        int ready = 0;
        int total = n * m;
        
        // 첫차 이전 도착한 크루 -> 첫차가 오기전에 대기가 모두 차는 경우 체크
        for (int time = 0; time < firstBus; time ++) {
            ready += arrivals[time];
            if (ready >= total) {
                return toHourMinute(time - 1);
            }
        }
        
        // 첫차부터 막차까지 도착한 크루 종합
        // 남은 수용인원을 계산하면서 지금 안타면 못탄다면 합류하기
        for (int time = firstBus; time <= lastBus; time ++) {
            int arrival = arrivals[time];
            ready += arrival;
            
            if (ready >= total) {
                
                return toHourMinute(time - 1);
            }
            
            // 셔틀 다녀감
            if ((time - firstBus) % t == 0) {
                ready = Math.max(0, ready - m); // m명 이하가 대기중이면 있는 인원만 데려간다.
                total -= m; // 총량에서는 버스의 수용인원만큼 빼야한다.
            }
        }
             
        return toHourMinute(lastBus);
    }
    
    private int toMinute(String time) {
        String[] s = time.split(":");
        return Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
    }
    
     private String toHourMinute(int time) {
        int hour = time / 60;
        int minute = time % 60;
        
        StringBuilder result = new StringBuilder();
        if (hour < 10) result.append('0');
        result.append(hour);
        
         result.append(':');
        
        if (minute < 10) result.append('0');
        result.append(minute);
        
         return result.toString();
    }
}
/*
가능한 도착 시각 중 제일 늦은 시간

같은 시각에 도착한 크루 중 대기열에서 제일 뒤에 선다.

셔틀은 도착 순간 기준으로 대기 순서대로 크루를 태우고 바로 출발. 빈 자리 있어도 그냥 갈듯

n * m -> 총 태워갈 수 있는 인원수
09:00 + (n - 1) * t -> 막차 시간

경우의 수

셔틀을 타는 크루원들이 태워가는 총량(n*m)보다 많으면,
- n*m을 넘기기전에 줄을 서야한다.
- 버스가 바로 떠나는 것도 고려해야 한다.

총량보다 적으면, 막차시간에 딱 맞춘다.

*/