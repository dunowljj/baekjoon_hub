class Solution
{
    public int solution(int n, int a, int b) {
        int round = 0;
        
        while (a != b) {            
            a++;            
            b++;
            
            a /= 2;
            b /= 2;
            
            round++;
        }
        return round;
    }
    
    /**
     * 짝수인 경우 (1더하고)2로 나누면 다음 번호 -> 더하든 말든 결과가 같다.
     * 홀수인 경우 1더하고 2로 나누면 다음 번호 -> 더해야 한다.
     * 결국 그냥 둘다 더하고 나누면 다음 번호이다.
     */
    
    /*
    실패코드 : 반례가 뭔지 모르겠다.
    
    while (a + 1 != b && b + 1 != a) {            
            a++;            
            b++;
            
            a /= 2;
            b /= 2;
            
            round++;
        }

        // 둘 중 큰 값이 홀수이면, 서로 다른 상대와 경기를 치룬다. (승부까지 한 라운드 남음)
        if ((int)(Math.max(a,b) % 2) == 1) round++;
    */
}