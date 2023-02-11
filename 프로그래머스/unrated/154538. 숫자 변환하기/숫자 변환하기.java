import java.util.*;

class Solution {
    public int solution(int x, int y, int n) {
        Set<Integer> visit = new HashSet();
        int answer = -1;
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, 0});
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            
            int num = curr[0];
            int depth = curr[1];
            if (num == y) return depth;
            if (num > y) continue;
            
            if (!visit.contains(num + n)) {
                 visit.add(num + n);                
                 queue.offer(new int[]{num + n, depth + 1});
            }
           
            if (!visit.contains(2 * num)) {
                visit.add(2 * num);
                queue.offer(new int[]{2 * num, depth + 1});
            }
           
            if (!visit.contains(3 * num)) {
                visit.add(3 * num);
                queue.offer(new int[]{3 * num, depth + 1});
            }
            
            
        }
        
        return answer;
    }
}