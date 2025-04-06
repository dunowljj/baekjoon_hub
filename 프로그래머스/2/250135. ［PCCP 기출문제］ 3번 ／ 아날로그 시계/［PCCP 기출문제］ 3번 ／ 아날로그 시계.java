

class Solution {
    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        final int startAt = h1 * 3600 + m1 * 60 + s1;
        final int endAt = h2 * 3600 + m2 * 60 + s2;
        
        int startAlert = 0;
        if (startAt * 59 % 3600 == 0 || startAt * 719 % 43200 == 0) {
            startAlert = 1;
        }
      
        return countAlert(endAt) - countAlert(startAt) + startAlert;
    }
    
    public int countAlert(int time) {          
        int alertCount = 0;
        
        int minuteAlert = time * 59 / 3600;
        int hourAlert = time * (60*12-1) / (60*60*12);

        // 0시,12시에 시/분/초침이 모두 겹친다.
        alertCount--;
        if (time >= 3600 * 12) {
            alertCount--;
        }
        
        alertCount += (minuteAlert + hourAlert);
        
        return alertCount;
    }
}
/**
60초마다 분침, 시침을 한번씩 만난다.

0시0분0초 1회
0시1분1초쯤 1회
...
0시59분59초쯤 1회
(59회)


시침 1바퀴
3600 * 12초

*/