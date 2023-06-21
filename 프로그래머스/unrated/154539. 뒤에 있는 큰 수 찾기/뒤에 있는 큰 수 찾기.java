import java.util.*;
import java.util.stream.*;

class Solution {
    
    static class Number {
        int num;
        int index;
        
        public Number(int num, int index) {
            this.num = num;
            this.index = index;
        }
    }
    
    public int[] solution(int[] numbers) {
        int[] answer = new int[numbers.length];
        Arrays.fill(answer, -1); // 처리 안된 값은 -1
        
        List<Number> list = IntStream.range(0, numbers.length)
            .mapToObj((index) -> new Number(numbers[index], index))
            .collect(Collectors.toList());
        
        Stack<Number> stack = new Stack<>();
        
        int idx = 0;
        for (Number now : list) {
            // 첫 값 넣기
            if (stack.isEmpty()) {
                stack.push(now);
                continue;
            }
            
            // 뒷큰수 찾기
            while (!stack.isEmpty() && stack.peek().num < now.num) {
                Number pop = stack.pop();
                answer[pop.index] = now.num;
            }
            
            // 찾은 후 현재 값 넣기
            stack.push(now);
        }
        
        return answer;
    }
}