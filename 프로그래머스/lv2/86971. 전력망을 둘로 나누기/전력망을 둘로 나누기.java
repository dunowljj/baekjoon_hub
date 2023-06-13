import java.util.*;

class Solution {
    public int solution(int n, int[][] wires) {
        if (n == 2) return 0;
        
        int answer = Integer.MAX_VALUE;    
        
        boolean[][] isLinked = new boolean[n + 1][n + 1];
        
        // 그래프 만들기
        for (int[] wire : wires) {
            isLinked[wire[0]][wire[1]] = true;
            isLinked[wire[1]][wire[0]] = true;
        }
        
        // 하나씩 지웠다 추가하기
        for (int i = 0; i < n - 1; i++) {
            
            int a = wires[i][0];
            int b = wires[i][1];
            
            if (isLinked[a][b]) {
                // 끊기
                isLinked[a][b] = false;
                isLinked[b][a] = false;

                // 순회
                answer = Math.min(findDiff(isLinked, a, n), answer); 

                // 복구
                isLinked[a][b] = true;
                isLinked[b][a] = true;
             }
        }
        
        return answer;
    }
    
    public int findDiff(boolean[][] isLinked, int start, int n) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        int count = 1;
        
        visited[start] = true;
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            start = queue.poll();
            
            for (int i = 1; i < n + 1; i++) {
                if (isLinked[start][i] && !visited[i]) {
                    visited[i] = true;
                    queue.offer(i);
                    count++;
                }
            }
        }
        
        return Math.abs(n - count - count); // (n - count) - count
    }
}