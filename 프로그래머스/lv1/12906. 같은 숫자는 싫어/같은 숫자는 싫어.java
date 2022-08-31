import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        Stack<Integer> stack = new Stack();
        Queue<Integer> queue = new LinkedList();
        
        stack.push(arr[0]);
        queue.offer(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (stack.peek() != arr[i]) {
                queue.offer(arr[i]);
            } 
            
            stack.push(arr[i]);
        }
        
        int[] answer = new int[queue.size()];
        
        int count = 0;
        while (!queue.isEmpty()) {
            answer[count++] = queue.poll();
        }

        return answer;
    }
}