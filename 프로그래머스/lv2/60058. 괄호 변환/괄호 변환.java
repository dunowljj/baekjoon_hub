import java.util.*;

class Solution {
    public String solution(String p) {
        String answer = "";
        
        if (p.equals("") || isRight(p)) return p;
        
        return solve(p);
    }
    
    private boolean isRight(String p) {
        Stack<Character> checker = new Stack<>();
        
        for (char bracket : p.toCharArray()) {
            if (bracket == ')') {
                if (!checker.isEmpty()) {
                    checker.pop();   
                } else {
                    return false;
                }
            }
            
            else if (bracket == '(') {
                checker.push(bracket);
            }
        }
        
        return checker.isEmpty();
    }
    
    private String solve(String p) {
        if (p.equals("")) return p;
        // u,v로 나누기
        String[] seperated = seperate(p);
        String u = seperated[0];
        String v = seperated[1];
        
        if (isRight(u)) return u + solve(v);
        else return convert(u, v);
    }
    
    private String[] seperate(String p) {
        int open = 0;
        int close = 0;
        int idx = 0;
        char[] arr = p.toCharArray();
        for (int i = 0; i < p.length(); i++) {
            if (arr[i] == '(') open++;
            else close++;
            
            if (open == close) {
                idx = i;
                break;
            }
        }
        
        return new String[]{p.substring(0, idx + 1), p.substring(idx + 1, p.length())};
    }
    
    private String convert(String u, String v) {
        StringBuilder result = new StringBuilder();
        result.append('(')
            .append(solve(v))
            .append(')');
        
        // 처음, 마지막 문자 제거
        if (u.length() != 0) {
            u = u.substring(1, u.length() - 1);
        }
        
        // 괄호 방향 뒤집기
        for (char ch : u.toCharArray()) {
            if (ch == '(') result.append(')');
            if (ch == ')') result.append('(');
        }
        
        return result.toString();
        
    }
}