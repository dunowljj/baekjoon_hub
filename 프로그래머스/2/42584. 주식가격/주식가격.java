import java.util.Stack;

class Solution {
    public int[] solution(int[] prices) {
        Stack<Integer> startIdxes = new Stack<>();
        int len = prices.length;
        int[] answer = new int[len];
        
        for (int nowIdx = 0; nowIdx < len; nowIdx++) {
            int nowPrice = prices[nowIdx];
        
            // 감소가 시작하는 순간을 포착하므로, 이 순간 시작점인 peek한 인덱스는 i-1번째 인덱스까지는 증가/유지한다.
            while (!startIdxes.isEmpty() && prices[startIdxes.peek()] > nowPrice) {
                
                // 정답을 체크하고 해당 수는 스택에서 빼버린다.
                int startIdx = startIdxes.pop();
                answer[startIdx] = nowIdx - startIdx;
            }
            
            startIdxes.push(nowIdx);
        }
        
        // 순회가 끝나면 스택에는 끝까지 증가/유지하는 수만 남아있다.
        int lastIdx = len - 1;
        while (!startIdxes.isEmpty()) {
            int startIdx = startIdxes.pop();
            answer[startIdx] = lastIdx - startIdx;
        }
        
        return answer;
    }
}