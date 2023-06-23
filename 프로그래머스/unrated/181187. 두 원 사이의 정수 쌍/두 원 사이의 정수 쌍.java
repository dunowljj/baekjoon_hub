class Solution {
    private static final double epsilon = 0.000001d;
    
    public long solution(long r1, long r2) {    
        long answer = 0;
        double count2;
        double count1;
        
        for (long i = 1; i < r2; i++) {
            if (i < r1) {
                count2 = Math.sqrt(r2 * r2 - i * i);
                count1 = Math.sqrt(r1 * r1 - i * i);
                
                // x축에 붙어있지 않는 경우 && 작은 원의 한 지점의 높이가 정수인 경우

                if (count1 - (int) count1 == 0.0) answer++;
            
                answer += (long) count2 - (long) count1;   
            }
            
            else {
                count2 = Math.sqrt(r2 * r2 - i * i);
                
                answer += (long) count2;
            }
        }
               
        // 축의 진행 4방향 중 한방향에 존재하는 점의 개수 더하기
        answer += r2 - r1 + 1;
        
        return 4 * answer;
    }
    
   
}
