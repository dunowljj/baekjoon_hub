import java.util.*;

class Solution {
    
    class Point {
        int x; 
        int y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        // i,j만큼 이동한 새 포인트 반환
        public Point move(int i, int j) {
            return new Point(x + i, y + j);
        }
        
        public boolean outOfIndex(int n) {
            return x < 0 || x >= n || y < 0 || y >= n;
        }
        
        public void rotate90(int m) {
            int row = this.x;
            int col = this.y;
            
            this.x = col;
            this.y = (m - 1) - row;
        }
    }
    
    private static final int EMPTY = 0;
    private static final int FULL = 1;

    
    public boolean solution(int[][] key, int[][] lock) {
        int m = key.length;
        int n = lock.length;
        
        // key가 튀어나온 위치 추출
        List<Point> keyPoints = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (key[i][j] == FULL) keyPoints.add(new Point(i,j));
            }
        }
        
         // lock에서 채워야할 빈 부분 개수 카운트
        int empty = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (lock[i][j] == EMPTY) empty ++;
            }
        }
        
        return searchAllCases(m ,n ,empty, keyPoints, lock);
    }
    
    // [일치 확인 -> 90도 회전] 반복하며 모든 경우의 수 확인
    private boolean searchAllCases(int m, int n, int empty, List<Point> keyPoints, int[][] lock) {   
        for (int i = 0; i < 4; i++) {
            if (searchMovedCases(m ,n ,empty, keyPoints, lock)) return true;
            
            rotate90(keyPoints, m);
        }
        return false;
    }
    
    // key 블럭을 이동시켰을때 lock과 일치하는지 완전탐색
    private boolean searchMovedCases(int m, int n, int empty, List<Point> keyPoints, int[][] lock) {
        for (int i = -m + 1; i < n; i++) {
            for (int j = -m + 1; j < n; j++) {   
                int correct = 0;
                correct = countCorrect(i, j, n, keyPoints, lock);
                
                // 정확히 맞물림
                if (correct == empty) return true;
            }
        }
        return false;
    }
    
    /**
     * key가 돌출된 부분의 좌표들이 keyPoints List안에 들어있다.
     * 이동해야할 값은 i,j로 주어진다.
     * 좌표들을 주어진 값만큼 이동시킨 다음 모양의 일치를 확인한다.
     * key의 돌출된 부분의 좌표와 lock의 움푹 들어간 부분이 만난다면 일치하므로 카운팅한다. 돌출된 부분끼리 만나면 열쇠가 맞지 않으므로 false 처리된다.
     */
    private int countCorrect(int i, int j, int n, List<Point> keyPoints, int[][] lock) {
        int correctCount = 0;
        for (Point point : keyPoints) {
            Point moved = point.move(i,j);
            
            if (moved.outOfIndex(n)) continue;
            
            if (lock[moved.x][moved.y] == FULL) return 0; // 튀어나온곳 끼리 부딪힘
            if (lock[moved.x][moved.y] == EMPTY) correctCount ++; // 잘 맞물림
        }
        
        return correctCount;
    }
    
    private void rotate90(List<Point> keyPoints, int m) {
        for (int i = 0; i < keyPoints.size(); i++) {
            Point now = keyPoints.get(i);
            now.rotate90(m);
        }
    }
}

/*
1) key의 돌출부 부분의 좌표를 딴 후, 이동시키면서 모두 검사
2) 검사 -> 회전 -> 검사 -> 회전
3) 4방향 모두 검사

key의 높낮이만큼 더 진행하게 된다. 열당 m만큼, 행당 2(m - 1)만큼 검사하게 된다.

(0,1) -> (1,3) -> (3,2) -> (2,0)
        
(0,0) (0,1) (0,2) (0,3)
(1,0) (1,1) (1,2) (1,3)
(2,0) (2,1) (2,2) (2,3)
(3,0) (3,1) (3,2) (3,3)
*/