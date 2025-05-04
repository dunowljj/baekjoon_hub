class Solution {
    
    final int WALL = 5;
    
    boolean[][] redVisited;
    boolean[][] blueVisited;
    
    Cell red, blue, redDest, blueDest;
    int n,m;
    int minMove = Integer.MAX_VALUE;
    private final int[][] mapper = {{0,0,-1,1},{1,-1,0,0}};
    
    public int solution(int[][] maze) {
        int answer = 0;
        n = maze.length;
        m = maze[0].length;
        
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i][j] == 1) red = new Cell(i, j);
                if (maze[i][j] == 2) blue = new Cell(i, j);
                if (maze[i][j] == 3) redDest = new Cell(i, j);
                if (maze[i][j] == 4) blueDest = new Cell(i, j);
            }
        }
        
        redVisited = new boolean[n][m];
        blueVisited = new boolean[n][m];
        
        redVisited[red.y][red.x] = true;
        blueVisited[blue.y][blue.x] = true;
        search(0, maze, red, red, blue, true, false, false);
        
        if (minMove == Integer.MAX_VALUE) return 0;
        else return minMove;
    }
    

    public void search(int move, int[][] maze, Cell red, Cell beforeRed, Cell blue, boolean isRed, boolean isRedFixed, boolean isBlueFixed) {
        // 둘 다 도착한 경우
        if (isRedFixed && isBlueFixed) {
            minMove = Math.min(minMove, move);
            return;
        }
        
        if (isRed) move++;
        
        // 현재 탐색 위치가 고정됨 -> 다른 수레 이동으로 넘어간다.
        if ((isRed && isRedFixed) || (!isRed && isBlueFixed)) {
            search(move, maze, red, beforeRed, blue, !isRed, isRedFixed, isBlueFixed);
            return;
        }
        
        Cell now;
        if (isRed) now = new Cell(red);
        else now = new Cell(blue);
          
        for (int dir = 0; dir < 4; dir++) {
            int ny = now.y + mapper[0][dir];
            int nx = now.x + mapper[1][dir];

            if (ny < 0 || ny >= n || nx < 0 || nx >= m) continue;
            if (maze[ny][nx] == WALL) continue;
            
            Cell next = new Cell(ny,nx);

            if (isRed) {
                if (redVisited[ny][nx]) continue;  
                // 빨간수래가 먼저 움직이기때문에, 파란수래의 위치로 이동가능하다.
                // 단, 다음위치에 파란수래가 도착점에 고정된 상태라면, 해당 위치로 이동 불가능하다.
                if (isInSame(next, blue, blueDest)) continue;
                
                redVisited[ny][nx] = true;
                if (isInSame(next, redDest)) search(move, maze, next, red, blue, !isRed, true, isBlueFixed);
                else search(move, maze, next, red, blue, !isRed, isRedFixed, isBlueFixed);
                
                redVisited[ny][nx] = false;
            } else {
                if (blueVisited[ny][nx]) continue;
                // blue가 이동할 위치에 red가 있으면 안됨
                if (isInSame(red, next)) continue;
                
                // 자리를 스위칭 하는 경우면 불가능
                // Blue는 이전 Red 위치로 이동 && 이동되어 있는 Red위치와 이동전 Blue위치가 같음
                if (isInSame(beforeRed, next) && isInSame(red, blue)) continue;
                
                blueVisited[ny][nx] = true;
                if (isInSame(next, blueDest)) search(move, maze, red, beforeRed, next, !isRed, isRedFixed, true);
                else search(move, maze, red, beforeRed, next, !isRed, isRedFixed, isBlueFixed);
                blueVisited[ny][nx] = false;
            }
        }
    }
    
    private boolean isInSame(Cell c1, Cell c2, Cell c3) {
        return isInSame(c1, c2) && isInSame(c2,c3);
    }
    
    private boolean isInSame(Cell c1, Cell c2) {
        return c1.y == c2.y && c1.x == c2.x;
    }
    
   
    
    class Cell {
        int y;
        int x;
        
        public Cell(int y, int x) {
            this.y = y;
            this.x = x;
        }
        
        public Cell(Cell cell) {
            this.y = cell.y;
            this.x = cell.x;
        }
    }
}

/**
자신이 방문한 특정 칸 재방문 불가
두 수레가 같은칸 불가
수레끼리 자리를 바꾸며 움직이기 불가능!

미로는 최대 16칸이다.

빨간색의 한 칸 이동에 대해, 파란색의 모든 이동 구하고-> 그것에 대해 또 빨간색...

수레끼리 인접한 경우, 빨간 수레가 먼저 움직이기때문에 파란수레의 위치에 영향을 받는다.
빨간 수레의 경우 파란 수래의 위치에 상관없이 움직일 수 있어야한다. 대신 도착점에 대해서는 처리가 필요하다.
*/