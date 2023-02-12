import java.util.*;
import java.util.stream.*;

class Solution {
    
    static class Point {
        int x;
        int y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static final int[][] mapper = {{1,-1,0,0},{0,0,-1,1}};
    
    public int[] solution(String[] maps) {
        List<Integer> answer = new ArrayList<>();
        int[][] grid = new int[maps.length][maps[0].length()];
            
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length(); j++) {
                char curr = maps[i].charAt(j);
                if (curr != 'X') grid[i][j] = curr - '0'; // X인 부분은 기본값 0으로 방치. 나머지는 숫자로 바꾸어 초기화
            }
        }
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int curr = grid[i][j];
                if (curr != 0) {
                    int sum = curr;
                    grid[i][j] = 0;
                    sum += search(new Point(i,j), grid);
                    
                    answer.add(sum);
                }
            }
        }
        
        if (answer.isEmpty()) answer.add(-1);
        
        return answer.stream()
            .sorted()
            .mapToInt(Integer::intValue)
            .toArray();
    }
    
    private int search(Point point, int[][] grid) {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            int nx = point.x + mapper[0][i];
            int ny = point.y + mapper[1][i];

            if (nx < 0 || ny < 0 || nx >= grid.length || ny >= grid[0].length || grid[nx][ny] == 0) continue;
            
            sum += grid[nx][ny];
            grid[nx][ny] = 0;
            
            sum += search(new Point(nx,ny), grid);
        }
        
        return sum;
    }
}