import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;


class Solution {
    private static final String TIME_SPLITOR = ":";
    private static final String FRONT_ZERO = "0";
    
    class Log {
        int start;
        int end;
        
        Log(String log) {
            String[] startAndEnd = log.split("-");
            this.start = toSecond(startAndEnd[0]);
            this.end = toSecond(startAndEnd[1]);
        }
        
        Log(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        private int toSecond(String time) {
            String[] splited = time.split(":");
            int hour = Integer.parseInt(splited[0]);
            int minute = Integer.parseInt(splited[1]);
            int second = Integer.parseInt(splited[2]);

            return (60 * 60 * hour) + (60 * minute) + second;
        }
    }
    
    class Section {
        int[] viewCounts;
        
       
        Section(int play_second) {
            viewCounts = new int[play_second + 1];
        }
        
         /** 
         * 인덱스 0은 0~1초구간에 시청했는지 여부이다. 
         * log가 start = 0 , end = 1과 같이 주어졌다면, 0만 카운트해야한다. 즉, end범위를 포함해서는 안된다.
         */
        public void count(Log log) {
            for (int i = log.start; i < log.end; i++) {
                viewCounts[i] ++;
            }
        }
        
        public int findTime(int adv_second) {
            long viewSum = 0L;
            
            for (int i = 0; i < adv_second; i++) {
                viewSum += viewCounts[i];
            }
                            
            // sliding window
            int front = 0, back = adv_second;
            long viewMax = viewSum; int startTime = front;

            while(back < viewCounts.length) {
                viewSum -= viewCounts[front++];
                viewSum += viewCounts[back++];
                
                // 앞부터 순회, 큰 경우만 max로 교체 -> max값이 같으면 빠른 시각이 우선된다.
                if (viewMax < viewSum) {
                    viewMax = viewSum;
                    startTime = front; // 제거한 값의 다음값이 시작시각이다. 위에서 ++ 했으니 그냥 쓰면 된다.
                }
            }
            return startTime;
        }
    }
    
    public String solution(String play_time, String adv_time, String[] logs) {
        Section section = new Section(toSecond(play_time));
        
        Arrays.stream(logs)
            .map(Log::new)
            .forEach((log) -> section.count(log));
        
        int startTime = section.findTime(toSecond(adv_time));
        return toTimeString(startTime);
    }
    
    private int toSecond(String time) {
        String[] splited = time.split(":");
        int hour = Integer.parseInt(splited[0]);
        int minute = Integer.parseInt(splited[1]);
        int second = Integer.parseInt(splited[2]);

        return (60 * 60 * hour) + (60 * minute) + second;
    }
    
    private String toTimeString(int second) {
        int hour = (second / 60 / 60);
        second -= hour * 60 * 60;
        
        int minute = second / 60;
        second -= minute * 60;
        
        String time = String.format("%02d:%02d:%02d", hour, minute, second);
        return time;
    }
    
}

/*
동일한 결과를 얻을 경우 빠른 시각을 사용한다.

log는 0초부터 주어질 수 있다. 시청시간과 재생시간은 1초 이상이 주어진다.

### 주의
예제 1의 설명을 자세히 읽어봐야한다.
[재생 기록] 부분을 읽어보면, 
-> "00시 25분 50초 부터 00시 48분 29초 까지 총 00시간 22분 39초 동안 죠르디의 동영상을 재생"
<추가 예시>
- 0-1초를 시청하면 1초동안 시청한 것 -> 1초이므로 배열의 0,1을 모두 카운팅하면 안된다.
- 2-4초를 시청했다고 하면 2초를 시청한 것  -> 2초이므로 마찬가지로 2,3,4를 모두 카운팅하면 안된다.

:: "재생 기록"을 count할때는 끝 범위를 포함하면 안된다. start <= i < end로 세야한다.


view 수를 계산할때도 마찬가지로 적용해야한다. 
예제 1의 설명의 마지막 줄을 보면,
"01시 37분 44초 부터 01시 45분 14초 까지 : 4번, 1번, 5번 재생 기록이 세차례 있으므로 재생시간의 합은 00시간 07분 30초 X 3 = 00시간 22분 30초"
재생된 시간을 기준으로 계산하고 있다.
*/
