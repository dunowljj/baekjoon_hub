class Solution {
    
    public String solution(int n, int t, int m, int p) {
        StringBuilder result = new StringBuilder();
        StringBuilder answer = new StringBuilder();
        
        result.append(0);
        for (int i = 1; i <= t * m; i++) {
            result.append(convertNumber(i, n));
        }
        
        int idx = p - 1;
        while (t-- > 0) {
            answer.append(result.charAt(idx));
            idx += m;
        }
        
        return answer.toString();
    }
    
    private StringBuilder convertNumber(int num, int n) {
        StringBuilder sb = new StringBuilder();
        
        while (num > 0) {
            int digit = num % n;
                
            sb.append(getChar(digit));
            num /= n;
        }
        return sb.reverse();
    }
    
    private char getChar(int num) {
        if (num >= 10) return (char) ('A' + (num - 10));
        else return (char) (num + '0');
    }
}
/**
0 1 2 3 4 5
10 11 12 13 14 15'

m명중 p번째거만 구해야함

100명이 참가하면, 1000개의 숫자를 구한다면 10만번 순회

그냥 n진법으로 바꾸고 하는게 더 편할수도 있다..
각 자릿수를 한자리씩 끊어서 말해야한다.
*/