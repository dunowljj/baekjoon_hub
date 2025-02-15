class Solution {
    
    private static int[][] queries;
    private static int[] answers;
    
    private static int answer = 0;
    
    private static int m;
    
    public int solution(int n, int[][] q, int[] ans) {
        queries = q;
        answers = ans;
        m = q.length;
        
        findSecretCode(0, 0, n, new int[5]);
        
        return answer;
    }
    
    private void findSecretCode(int depth, int before, int n, int[] comb) {
        if (depth == 5) {
            if (verifyQueries(comb, queries, answers)) {
                answer++;
            }
            return;
        }
        
        // 1~n
        for (int i = before + 1; i <= n; i++) {
            comb[depth] = i;
            findSecretCode(depth + 1, i, n, comb);
        }
    }
    
    private boolean verifyQueries(int[] comb, int[][] q, int[] ans) {
        int count = 0;
        for (int i = 0; i < m; i++) {
            if (verify(comb, q[i], ans[i])) count++;
        }
        
        return count == m;
    }
    
    private boolean verify(int[] comb, int[] query, int answer) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (query[i] == comb[j]) {
                    count++;
                    break;
                }
            }
        }
        
        return count == answer;
    }
}
/**

m번의 시도를 할 수 있다.
서로 다른 수 5개!
5개의 정수.

30개의 정수에서 5개를 뽑는다면?
30*29*28*27*26 / 5*4*3*2*1
29*7*27*26 -> 203 * 722 -> 146166

30C5 한 후 5개 검증(*25)


*/