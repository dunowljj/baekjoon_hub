import java.util.*;

import java.util.*;

class Solution {
    
    int numberOfArea = 0;
    int maxSizeOfArea = 0;
    
    int maxY, maxX;
    
    private static final int[][] mapper = {{0,0,1,-1},{1,-1,0,0}};
    private static final int VISITED = 0;
    
    public int[] solution(int m, int n, int[][] picture) {
        maxY = m;
        maxX = n;

        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if (picture[i][j] != VISITED) {
                    numberOfArea++;
                    bfs(new Point(i,j), picture);
                }
            }
        }

        return new int[]{numberOfArea, maxSizeOfArea};
    }
    
    public void bfs(Point start, int[][] picture) {
        Queue<Point> q = new LinkedList<>();
        q.offer(start);
        
        int color = picture[start.y][start.x];
        picture[start.y][start.x] = VISITED;
        int count = 1;
        while (!q.isEmpty()) {
            Point now = q.poll();
            
            for (int i = 0; i < 4; i++) {
                int ny = now.y+mapper[0][i];
                int nx = now.x+mapper[1][i];
                
                if (ny < 0 || ny >= maxY || nx < 0 || nx >= maxX) continue;
                
                if (picture[ny][nx] != VISITED && picture[ny][nx] == color) {
                    picture[ny][nx] = VISITED;
                    q.offer(new Point(ny,nx));
                    count++;
                }
            }
        }
        maxSizeOfArea = Math.max(maxSizeOfArea, count);
    }
    
    class Point {
        int y;
        int x;
        
        Point(int y, int x) {
            this.y=y;
            this.x=x;
        }
    }
}