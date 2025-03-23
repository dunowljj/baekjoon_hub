import java.util.*;

class Solution {
    
    private static final char EMPTY = '.';
    private static final char WALL = '*';
    // private final int[][] mapper = {{0,0,1,-1},{1,-1,0,0}};
    
    public String solution(int m, int n, String[] board) {
        String answer = "";
        int[][] mapper = {{0,0,1,-1},{1,-1,0,0}};
        char[][] gameBoard = new char[m][n];
        
        Map<Character, Point[]> tiles = new HashMap<>();
        Point[][] points = new Point[26][2];

        int tileCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = board[i].charAt(j);
                gameBoard[i][j] = ch;
                if ('A' <= ch && ch <= 'Z') {
                    
                    int idx = ch -'A';
                    if (points[idx][0] == null) {
                        tileCount++;
                        points[idx][0] = new Point(i,j);
                    } else {
                        points[idx][1] = new Point(i,j);
                    }
                }
            }
        }
        
        while (tileCount > 0) {
            // 정확성을 위해 순회마다 가장 순서가 빠른 타일만 하나 지워야한다.
            // 지워진 타일이 없다면 종료해야 한다.
            boolean removed = false;
            for (int a = 0; a < 26; a++) {
                if (points[a][0] == null) continue;
                
                Point[] point = points[a];

                Point start = point[0];
                Point end = point[1];
               
                removed |= canAccess(start, end, gameBoard, m, n);
                
                if (removed) {
                    points[a][0] = null;
                    points[a][1] = null;
                    tileCount--;
                    gameBoard[start.y][start.x] = '.';
                    gameBoard[end.y][end.x] = '.';
                    
                    answer += (char) (a + 'A');
                    
//                     for (int i = 0; i < m; i++) {
//                         for (int j = 0; j < n; j++) {
//                             System.out.print(gameBoard[i][j] + " ");
//                         }
//                         System.out.println();
//                     }
//                     System.out.println();
                    
                    break;
                }
            }
            
            // 삭제된 타일이 없고, 아직 타일이 남아있다.
            if (!removed && tileCount > 0) {
                answer = "IMPOSSIBLE";
                break;
            }
        }
        
        return answer;
    }
    
    // 좌측위부터 start, end를 찾기 때문에, 적어도 높이는 start가 더 높다.
    private boolean canAccess(Point start, Point end, char[][] gameBoard, int m, int n) {        
        boolean canAccess = false;
        
        if (start.x == end.x) {
            return down(start.x, start.y, end.y, false, gameBoard);
        }
        
        // 왼 --> 오
        if (start.x < end.x) {
            if (start.y == end.y) return right(start.y, start.x, end.x, false, gameBoard);
            return canGoRightDown(start, end, gameBoard) || canGoDownRight(start, end, gameBoard);
        }
        
        // 왼 <-- 오
        if (start.x > end.x) {
            if (start.y == end.y) return left(start.y, start.x, end.x, false, gameBoard);
            return canGoLeftDown(start, end, gameBoard) || canGoDownLeft(start, end, gameBoard);
        }
        
        return canAccess;
    }
    
    public boolean canGoRightDown(Point start, Point end, char[][] gameBoard) {
        return right(start.y, start.x, end.x, false, gameBoard) &&
            down(end.x, start.y, end.y, true, gameBoard);
    }
    
    public boolean canGoDownRight(Point start, Point end, char[][] gameBoard) {
        return down(start.x, start.y, end.y, false, gameBoard) &&
            right(end.y, start.x, end.x, true, gameBoard);       
    }
    
    public boolean canGoLeftDown(Point start, Point end, char[][] gameBoard) {
        return left(start.y, start.x, end.x, false, gameBoard) &&
            down(end.x, start.y, end.y, true, gameBoard);
    }
    
    public boolean canGoDownLeft(Point start, Point end, char[][] gameBoard) {
        return down(start.x, start.y, end.y, false, gameBoard) &&
            left(end.y, start.x, end.x, true, gameBoard);       
    }
    
    // 자기자신을 체크하지 않기 위해 시작지점을 일부 검사하지 않는다.
    // 꺾은 후에 접히는 부분을 탐색할때는 시작점을 탐색해야한다.
    private boolean right(int fixY, int startX, int endX, boolean includeStart, char[][] gameBoard) {
        if (!includeStart) startX++;
        for (int x = startX; x < endX; x++) {
            if (gameBoard[fixY][x] != EMPTY) {
                return false;
            }
        }  
        return true;
    }
    
    private boolean left(int fixY, int startX, int endX, boolean includeStart, char[][] gameBoard) {
        if (!includeStart) startX--;
        for (int x = startX; x > endX; x--) {
            if (gameBoard[fixY][x] != EMPTY) {
                return false;
            }
        }  
        return true;
    }
    
    private boolean down(int fixX, int startY, int endY, boolean includeStart, char[][] gameBoard) {
        if (!includeStart) startY++;
        for (int y = startY; y < endY; y++) {
            if (gameBoard[y][fixX] != EMPTY) {
                return false;
            }
        }
        
        return true;
    }

    class Point {
        int y;
        int x;
        
        Point (int y, int x) {
            this.y=y;
            this.x=x;
        }
    }
}
/**
m,n

m,n 최대 100 -> 최대 맨해튼거리 -> 198
최대 26개 타일 페어
26 * 198 * 26

**/