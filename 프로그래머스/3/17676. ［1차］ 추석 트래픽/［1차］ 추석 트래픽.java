import java.util.*;

class Solution {
    
    private final int MAX_TIME = 60*60*24*1000;
    
    public int solution(String[] lines) {
        int[] timeLine = new int[MAX_TIME+1];
        int[] startCount = new int[MAX_TIME+1];
        
        for (String line : lines) {
            String[] log = line.split(" ");
            
            int S = timeToMilisecond(log[1]);
            int T = (int) ((Double.parseDouble(log[2].substring(0, log[2].length() - 1))) * 1000);
            
            int startTime = Math.max(0, S - (T - 1));
            int endTime = S;
            
            timeLine[startTime]++;
            timeLine[endTime + 1]--; // end까지 포함해야하므로, 하나 미뤄서 갱신
            
            startCount[startTime]++;
        }
        
        for (int i = 1 ; i < timeLine.length; i++) {
            timeLine[i] += timeLine[i - 1];    
        }
        
        
        int answer = 0;
        int l = 0;
        int r = 999;
        int countSum = 0;
        for (int i = 0; i < 999; i++) countSum += startCount[i];
        
        // 이전에 0~998까지 합 구하고, 시작하자마자 1~999으로 변경
        // 현재시점 누적값이 현재 시점 시작한 것과 겹치는 경우 방지를 위해 
        // [0번째 누적값 사용 + 1~999까지 합] 이런식으로 1000초 구간 개수 구하기
        for (int ms = 0; ms < timeLine.length - 1000; ms++) {  
            
            countSum -= startCount[l++];
            countSum += startCount[r++];
            
            answer = Math.max(timeLine[ms] + countSum, answer);
        }
        
        return answer;
    }
    
    private int timeToMilisecond(String time) {
        String[] splited = time.split(":");
        
        int result = Integer.parseInt(splited[0]) * 60 * 60 * 1000;
        result += Integer.parseInt(splited[1]) * 60 * 1000;
        result += (int) (Double.parseDouble(splited[2]) * 1000);
        
        return result;
    }
    
     private int timeToSecond(String time) {
        String[] splited = time.split(":");
        
        int result = Integer.parseInt(splited[0]) * 60 * 60;
        result += Integer.parseInt(splited[1]) * 60;
        result += Integer.parseInt(splited[2]);
        
        return result;
    }
    
     private String timeFormat(int ms) {
        int h = ms / (60*60*1000);
        int m = (ms % (60*60*1000)) / (60*1000);
        int s = (ms % (60*1000)) / (1000);
        ms %= 1000;
        return h+":"+m+":"+s+":"+ms;
     }
    static class Time {
        int startTime;
        int endTime;
        
        public Time(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}

/**
초간 처리하는 최대 요청 개수

"S T" 
S는 응답이 완료된 시간. 2016-09-15 고정임


임의의 시간부터 1초간 처리하는 요청의 최대 개수는 뭘 말하는가? 1초간 시간 내에 완료처리되는 시간이 있는지를 묻는걸까?
"처리시간은 시작시간과 끝 시간을 포함"이라는 문구의 의미가 예제2에 나타나 있음.
로그의 시작시점이 "임의의 시간 1초"에 포함되어 있다면, 최대 처리량에 포함된다.

24 * 60 * 60 * 1000 밀리초 -> 9백만?
n2000, T3이면 600만개 갱신필요


투포인터, sliding window: 밀리세컨드 기준 800만 이상의 길이를 가지는데, 1초 간격으로 2000개 로그를 매번 검사한다면 시간이 너무 오래걸린다.

0) 밀리초 8천만 배열 만들기
1) 각 로그 시작점에 ++, 끝점에 --
2) 누적합으로 특정 1ms시점에 누적 개수 구하기
3) 여기서 배열을 순회한다. 각 시점의 ((현재)의 누적합 + (현재~1초)사이 시작점의 개수)는 1초 구간동안 처리량이 된다.

모든 startTime을 TreeSet에 넣거나, 배열을 하나더만들거나... 이분탐색을 하거나 해서 1초내에 startTime만 찾아주면 된다.

1초 범위동안 하나라도 + 된게 있는지 봐야해서...

slide해서 1000개 단위로 확인한다.


*/