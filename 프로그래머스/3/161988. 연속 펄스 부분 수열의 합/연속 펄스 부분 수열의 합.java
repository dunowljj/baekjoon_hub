class Solution {
    public long solution(int[] sequence) {
        long p1Sum = 0; // 짝수에 -1 곱하는 펄스 수열일 경우
        long p2Sum = 0; // 홀수에 -1 곱하는 펄스 수열일 경우
        
        long p1Max = 0; 
        long p2Max = 0;
        
        for (int i = 0; i < sequence.length; i++) {
            int now = sequence[i];
            
            if (i % 2 == 0) {
                p1Sum += now * -1;
                p2Sum += now;
            }
            
            if (i % 2 == 1) {
                p1Sum += now;
                p2Sum += now * -1;
            }
            
            p1Max = Math.max(p1Sum, p1Max);
            p2Max = Math.max(p2Sum, p2Max);
            
            if (p1Sum < 0) p1Sum = 0;
            if (p2Sum < 0) p2Sum = 0;
        }
        
        return Math.max(p1Max, p2Max);
    }
}
/**
--> 펼스 수열을 곱한 값을 기준으로 가장 큰 부분수열 구하기

1, 3, 5, -1, 10, -7, 9
**/