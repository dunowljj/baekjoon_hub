import java.util.Stack;

class Solution {
    
    private static final char OPEN_BRACKET = '(';
    private static final char CLOSE_BRACKET = ')';
    
    boolean solution(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char now : s.toCharArray()) {
            if (now == OPEN_BRACKET) {
                stack.push(OPEN_BRACKET);
                continue;
            }
            
            if (now == CLOSE_BRACKET) {
                if (stack.isEmpty()) return false;
                stack.pop();
            }
        }
        
        return stack.isEmpty();
    }
}