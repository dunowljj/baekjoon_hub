class Solution {
    boolean[] isNotPrime = new boolean[1_000_001];
    
    public int solution(int n) {
        setPrime();
        
        return countPrime(n);
    }
    
    //true -> notP, false -> P
    private void setPrime() {
        isNotPrime[1] = true;
        for (int i = 2; i <= (int)Math.sqrt(1_000_000); i++) {
            if (isNotPrime[i]) continue;
            
            for (int j = i * i; j <= 1_000_000; j += i) {
                isNotPrime[j] = true;
            }    
        }
    }
    
    private int countPrime(int n) {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (!isNotPrime[i]) count++;
        }
        
        return count;
    }
}