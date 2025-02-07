import java.util.*;

class Solution {
    
    private static Integer[][] dp;
    private static int[] onboard;  
    private static List<Integer> nextOnboardTimes;
    private static int t1,t2,a,b;
    
    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        this.t1 = t1; this.t2= t2; this.a = a; this.b = b; 
        this.onboard = onboard;
        
        dp = new Integer[onboard.length + 1][3000];
        
        nextOnboardTimes = new ArrayList<>();
        // 맨 앞에 1이있다면 무시한다. 0에서 1로 전환하는 시점만 저장
        for (int i = 0; i < onboard.length - 1; i++) {
            if (onboard[i] == 0 && onboard[i + 1] == 1) {
                nextOnboardTimes.add(i + 1);   
            }
        } 
        
        find(0, temperature, temperature, 0, 0);
        
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < dp[0].length; i++) {
            if (dp[onboard.length - 1][i] == null) continue;
            answer = Math.min(answer, dp[onboard.length - 1][i]);
        }
        return answer;
    }
    
    public void find(int time, int nowTem, int outTem, int use, int key) {
        if (time >= onboard.length) return;
        if (onboard[time] == 1 && !(inProperTemperature(nowTem))) return;
        
        if (onboard[time] == 0 && nextOnboardTimes.size() > key) {
            int nextOnboardTime = nextOnboardTimes.get(key);
            if (!isAvailable(time, nextOnboardTime, nowTem)) {
                // System.out.println("nowTem:"+nowTem+", time:"+time+",nextOn:"+nextOnboardTime);
                // System.out.println();
                return;
            }
        }
        if (time != 0 && onboard[time - 1] == 0 && onboard[time] == 1) {
            key++;
        }
        
        if (dp[time][nowTem + 1100] == null || dp[time][nowTem + 1100] > use) {
            dp[time][nowTem + 1100] = use;    
        } else {
            return;
        }
        
        // 에어컨을 끈 경우
        if (nowTem < outTem) {
            find(time + 1, nowTem + 1, outTem, use, key);    
        } else if (nowTem > outTem) {
            find(time + 1, nowTem - 1, outTem, use, key);        
        } else {
            find(time + 1, nowTem, outTem, use, key);
        } 
        
        // 에어컨을 킨 경우
        find(time + 1, nowTem + 1, outTem, use + a, key);
        find(time + 1, nowTem - 1, outTem, use + a, key);   
        find(time + 1, nowTem, outTem, use + b, key);
    }
    
    private boolean inProperTemperature(int nowTem) {
        return t1 <= nowTem && nowTem <= t2;
    }
    
    private int findNextOnboardTime(int time) {
        for (int i = time + 1; i < onboard.length; i++) {
            if (onboard[i] == 1) {
                return i;
            }
        }
        
        return -1;
    }
    
    private boolean isAvailable(int time, int nextOnboardTime, int nowTem) {
        if (nowTem < t1) {
            return t1 - nowTem <= nextOnboardTime - time;
        }
        
        if (t2 < nowTem) {
            return nowTem - t2 <= nextOnboardTime - time;
        }
        
        // System.out.println("true");
        return true;
    }
}
/**

[에어컨 on]
if (실내온도와 희망온도가 다름) {
  희망온도를 향해 +1 or -1
  전력 += a
} else {
  전력 += b
}

[off]
실외온도를 향해 +1 or -1

결국 5가지 경우로 나뉜다.

- 에어컨을 키면, 실내온도는 희망온도에 의존한다. 
    -> 희망온도는 에어컨을 켰을때 아무때나 바꿀 수 있다. -> 즉, 온도를 1높이거나, 유지하거나, 1낮출 수 있다.
- 에어컨을 끄면, 실내온도는 실외온도에 의존한다. 
    -> 고정된 실외온도에 영향을 받아 변한다. -> 온도가 1오르거나, 1내려간다.


만약 a가 b보다 작은 경우면 그냥 희망온도를 높이는게 나을수도 있지 않은가?
temperature는 t1 ~ t2 범위 밖의 값입니다.

t1~t2가 50정도의 범위인데, dp안하면 너무 커질듯
문제는 온도별 dp를 하면 온도가 끝없이 치솟는 경우, 계속 0만나오면 문제가 된다. 점화식으로 다음 1까지 구해야하나?


예외?
- t1 < t2이므로 같은 온도에서 존재해야하는 경우는 없을듯

만약 입력이 onboard에 0이 매우 길게 나온다면, 온도가 이상한곳으로 빠지고 경우의 수가 너무 많아진다. 이를 처리해야할듯.

다음 1까지의 거리를 안다면, 그때까지 적정 온도에 도달할 수 있는가? 를 검사하는건 어떨까?

**/