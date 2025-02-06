class Solution {
    
    private static Time endTime;
    
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        endTime = new Time(video_len);
        
        Time nowTime = new Time(pos);
        Time opStartTime = new Time(op_start);
        Time opEndTime = new Time(op_end);
        
        for (String command : commands) {
            
            if (nowTime.isBetween(opStartTime, opEndTime)) {
                nowTime = new Time(op_end);
            }
            
            if (command.equals("prev")) {
                nowTime.prev();
            }
            
            if (command.equals("next")) {
                nowTime.next();
            }
            
            if (nowTime.isBetween(opStartTime, opEndTime)) {
                nowTime = new Time(op_end);
            }
        }

        return nowTime.toString();
    }
    
    static class Time {
        private static final String TIME_SPLITTER = ":";
        int minute;
        int second;        
        
        public Time(int minute,int second) {
            this.minute = minute;
            this.second = second;
        }
        
        public Time(String time) {
            String[] minuteSecond = time.split(TIME_SPLITTER);
            this.minute = Integer.parseInt(minuteSecond[0]);
            this.second = Integer.parseInt(minuteSecond[1]);
        }
        
        public void prev() {
            // 총 시간이 10초 미만인 경우
            if (minute == 0 && second < 10) {
                second = 0;
            }
            
            // 초가 10초 미만
            else if (second < 10) {
                minute -= 1;
                second = second + 50;
            }
            
            else second -= 10;
        }
        
         public void next() {
            // 50초 이상인 경우 처리
            if (second >= 50) {
                minute += 1;
                second -= 50;
            } else {
                second += 10;
            }            
             
            if (this.isAfterThan(endTime)) {
                this.minute = endTime.minute;
                this.second = endTime.second;
            }
        }
        
        public boolean isBetween(Time start, Time end) {
            return this.isAfterThan(start) && end.isAfterThan(this);
        }
        
        // 같은 경우도 true, isBetween에서 써야함
        public boolean isAfterThan(Time target) {
            if (target.minute == this.minute) {
                return target.second <= this.second;
            }
            
            return target.minute <= this.minute;
        }
        
        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            if (minute < 10) {
                result.append('0');
            }
            
            result.append(minute).append(TIME_SPLITTER);
            
            if (second < 10) {
                result.append('0');
            }
            
            result.append(second);
            
            return result.toString();
        }
    }
}
/**
10초미만이면 0초로
남은 시간 10초 미만 마지막 위치로
건너뛰기
- "현재 위치가 오프닝인 경우" ->  오프닝 끝으로 이동
- 이동 후에 시간이 오프닝 구간이면 오프닝 구간 맨끝으로 이동
- 이동 전에 시간이 오프닝 구간이면 오프닝 구간 맨끝이동 후 + 10초
*/