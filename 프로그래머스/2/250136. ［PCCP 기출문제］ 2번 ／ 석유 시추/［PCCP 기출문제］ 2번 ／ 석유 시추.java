import java.util.*;

class Solution {
    
    private static boolean[][] visited;
    private static final int[][] mapper = {{-1,1,0,0},{0,0,1,-1}};
    private static int n,m;
    private static int seq = 1;
    private static int[] colSum;
    
    public int solution(int[][] land) {
        int answer = 0;
            
        n = land.length;
        m = land[0].length;
        colSum = new int[m];
        visited = new boolean[n][m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j< m; j++) {
                if (!visited[i][j] && land[i][j] == 1) {
                    visited[i][j] = true;
                    bfs(i, j, land);
                }
            }
        }
        
        int max = 0;
        for (int oil : colSum) {
            max = Math.max(max, oil);
        }
        return max;
    }
    
    public void bfs(int y, int x, int[][] land) {
        Queue<Point> queue = new LinkedList<>();
        Set<Integer> columns = new HashSet<>();
        
        queue.offer(new Point(y, x));
        columns.add(x);
        int oilAmount = 1;
        
        while (!queue.isEmpty()) {
            Point now = queue.poll();
            
            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];
                
                if (ny < 0 || ny >= n || nx < 0 || nx >= m) continue;
                if (visited[ny][nx] || land[ny][nx] == 0) continue;
     
                visited[ny][nx] = true;
                oilAmount++;
                
                queue.offer(new Point(ny, nx));
                columns.add(nx);
            }
        }
        
        for (int col : columns) {
            colSum[col] += oilAmount;
        }
    }
    
    static class Point {
        int y;
        int x;
        
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
/**
그룹핑하고, 그룹seq를 각 배열 위치에 저장
seq - 석유량 map 생성

각 열에 모든 값을 set에 체크하면서, 시추가능 석유 양 종합
[
[0, 0, 0, 1, 1, 1, 0, 0]
[0, 0, 0, 0, 1, 1, 0, 0]
[1, 1, 0, 0, 0, 1, 1, 0]
[1, 1, 1, 0, 0, 0, 0, 0]
[1, 1, 1, 0, 0, 0, 1, 1]
]

*/