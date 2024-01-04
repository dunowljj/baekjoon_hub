import java.util.Stack;

class Solution {
    public String solution(String number, int k) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < number.length(); i++) {
            int now = number.charAt(i) - '0';

            while (!stack.isEmpty() && k != 0 && stack.peek() < now) {
                stack.pop();
                k--;
            }

            stack.push(now);
        }

        while (k != 0) {
            stack.pop();
            k--;
        }


        StringBuilder sb = new StringBuilder();
        stack.stream()
                .forEach((e) -> sb.append(e));

        return sb.toString();
    }
}

/**
 * 맨 앞에 수부터 탐색해서 k개 중 가장 큰게 앞자리 오도록 지우기
 * 다시 남은 개수로 가장 큰수가 앞에 오도록 지우기
 */