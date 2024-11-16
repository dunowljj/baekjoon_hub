import java.util.*;

class Solution {
    
    private static final int WALL = 0;
    private static final int EMPTY = 1;
    
    private static final int[][] mapper = {{1,-1,0,0},{0,0,1,-1}};
    
    public int solution(int[][] maps) {
        // n,m이 모두 1인경우는 존재x, 첫칸도 세야한다.
        // maps에 타일수를 기록하고 작은것으로 갱신하기
        int n = maps.length;
        int m = maps[0].length;
        
        Point start = new Point(0, 0);
        Point end = new Point(n - 1, m - 1);
        
        Queue<Point> queue = new LinkedList<>();
        queue.offer(start);
        boolean[][] visited = new boolean[n][m];
        
        while (!queue.isEmpty()) {
            
            Point now = queue.poll();
            
            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];
                
                // outofrange
                if (ny < 0 || ny >= n || nx < 0 || nx >= m) continue;
                
                // wall
                if (maps[ny][nx] == WALL) continue;
                
                // 다음칸이 처음 방문인 경우 1이므로 갱신 || 더 작은 방문 경우의수라면 갱신
                if (maps[ny][nx] == 1 || maps[ny][nx] > maps[now.y][now.x] + 1) {
                    maps[ny][nx] = maps[now.y][now.x] + 1;
                    
                    if (end.y == ny && end.x == nx) continue;
                    queue.offer(new Point(ny, nx));
                }
            }
            
        }
        
        int answer = maps[n - 1][m - 1];
        return answer == 1 ? -1 : answer;
    }
    
    static class Point {
        int y;
        int x;
        
        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}