class Solution {
    public int solution(int[] money) {
        int n = money.length;
        
        if (n == 3) {
            int answer = 0;
        
            for (int m : money) {
                answer = Math.max(m, answer);
            }
            return answer;
        }
        
        int[] dp_include = new int[n]; 
        int[] dp_exclude = new int[n]; 
        
        // dp~[n] 해당 집을 털었을때 시점에서 여태까지 최대 수익
        
        // dp_exclude[n] 맨 앞집을 털지 않은 경우 n번째까지 최댓값
        dp_exclude[0] = 0;
        dp_exclude[1] = money[1];
        // dp_exclude[2] = Math.max(money[1], money[2]);
        
        // dp_include[n] 맨 앞집을 털은 것을 포함한 경우 -> 맨 뒷집을 털 수 없다.
        dp_include[0] = money[0];
        dp_include[1] = Math.max(money[0], money[1]); // 털지 않고 맨끝 집을 사용한 경우가 더 많을 수도 있다.
        // dp_include[2] = money[0] + money[2]; 
        // 1 3 5 6 9 4
        // 0 3 1 1 9 1
        // 0 0 3 1 1 9
        
        for (int i = 2; i < n; i++) {
            dp_exclude[i] = Math.max(dp_exclude[i - 2] + money[i], dp_exclude[i - 1]);
            dp_include[i] = Math.max(dp_include[i - 2] + money[i], dp_include[i - 1]);
        }
        
        return Math.max(dp_exclude[n - 1], dp_include[n - 2]);
    }
}
/**
인접한 집을 2집을 털면 안된다.
양끝도 안되는듯

2집
ox
xo

3집
oxx
xox
xxo

4집
oxox
xoxo

5집
oxoxx
oxxox
xoxox
xoxxo
xxoxo
**/