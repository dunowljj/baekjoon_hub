class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        String answer = "";
        int playSecond = toSecond(play_time);
        int advSecond = toSecond(adv_time);
        
        long[] viewCounts = new long[playSecond + 1];
        
        for (String log : logs) {
            String[] split = log.split("-");
            int start = toSecond(split[0]);
            int end = toSecond(split[1]);
            
            // viewCounts[x] : x~x+1 시점에 시청자 수
            viewCounts[start] ++; // 시작하는 시점에 추가?
            viewCounts[end] --; // 끝나는 시점에 추가?
        }
        
        for (int i = 0; i < viewCounts.length - 1; i++) {
            viewCounts[i + 1] += viewCounts[i];
        }
        
        for (int i = 0; i < viewCounts.length - 1; i++) {
            viewCounts[i + 1] += viewCounts[i];
        }
        
        long maxCount = 0;
        int second = 0;
        for (int i = 0; i <= playSecond - advSecond; i++) {
            if (i == 0) { 
                if (maxCount < viewCounts[i + advSecond - 1]) {
                    maxCount = viewCounts[i + advSecond - 1];
                }
                continue;
            }
            
            if (maxCount < viewCounts[i + advSecond - 1] - viewCounts[i - 1]) {
                maxCount = viewCounts[i + advSecond - 1] - viewCounts[i - 1];
                second = i;
            }
        }
        
        return toTimeFormat(second);
    }
    
    private static int toSecond(String times) {
        String[] time = times.split(":");
        
        int second = 0;
        second += Integer.parseInt(time[0]) * 3600;
        second += Integer.parseInt(time[1]) * 60;
        second += Integer.parseInt(time[2]);

        return second;
    }
    
    private static String toTimeFormat(int seconds) {
        int hour = seconds / 3600;
        seconds -= hour * 3600;
        
        int minute = seconds / 60;
        seconds -= minute * 60;
        
        String answer = "";
        if (hour < 10) answer += "0";
        answer += hour + ":";
        
        if (minute < 10) answer += "0";
        answer += minute + ":";
        
        if (seconds < 10) answer += "0";
        answer += seconds;
        
        return answer;
    }
}