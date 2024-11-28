import java.util.*;

class Solution {
    
    private static final char SEA = 'X';
    private static final int[][] mapper = {{0,0,-1,1},{-1,1,0,0}};
    
    private int n;
    private int m;
    
    public int[] solution(String[] maps) {
        n = maps.length;
        m = maps[0].length();
        
        List<Integer> answer = new ArrayList<>();
        
        int islandNo = 0;
        List<Integer> foodInIsland = new ArrayList<>();
        boolean[][] visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maps[i].charAt(j) != SEA && !visited[i][j]) {
                    countFoodByBFS(i, j, maps, foodInIsland, visited, answer);
                    islandNo++;
                }     
            }
        }
        if (answer.isEmpty()) return new int[]{-1};
        
        return answer.stream()
            .sorted()
            .mapToInt(Integer::intValue)
            .toArray();
    }
    
    private void countFoodByBFS(int y, int x, String[] maps, List<Integer> foodInIsland, boolean[][] visited, List<Integer>  answer) {
        visited[y][x] = true;
        
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(y, x));
        
        // 현재 위치 값 추가
        int totalFood = maps[y].charAt(x) - '0';
        
        while (!queue.isEmpty()) {
            Point now = queue.poll();
            
            for (int i = 0; i < 4; i ++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];
                
                if (ny < 0 || ny >= n || nx < 0 || nx >= m
                   || visited[ny][nx] || maps[ny].charAt(nx) == SEA) continue;
                
                
                visited[ny][nx] = true;
                totalFood += maps[ny].charAt(nx) - '0';
                
                queue.offer(new Point(ny,nx));
            }
        }
        
        if (totalFood != 0) answer.add(totalFood);
    }
    
    static class Point {
        int y;
        int x;
        
        public Point (int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}