import java.util.*;

class Solution {
    
    int[][] move_mapper = {{0,0,-1,1},{1,-1,0,0}};
    int[][] rotate_mapper;
    // 가로로 놓여진 경우 -> 축이 오른쪽 : 오른위, 오른아래{{-1,1},{1,1}} , 축이 왼쪽 : 왼아래, 왼위
    // 세로로 놓여진 경우 -> 축이 위 :
    
    static int n;
    
    public int solution(int[][] board) {
        n= board.length;
        
        Set<TwoPoints> visited = new HashSet<>();
        Queue<TwoPoints> q = new LinkedList<>();
        q.offer(new TwoPoints(
            new Point(0,0), new Point(0,1)
        ));
        
        int second = 0;
        while (!q.isEmpty()) {
            
            int size = q.size();
            for (int i = 0; i < size; i++) {
                
                TwoPoints now = q.poll();
                if (visited.contains(now)) continue;
                visited.add(now);

                if (now.isArrive()) {
                    return second;
                }

                // 4방향 이동
                for (int dir = 0; dir < 4; dir++) {
                    int ny1 = now.p1.y + move_mapper[0][dir];
                    int nx1 = now.p1.x + move_mapper[1][dir];

                    int ny2 = now.p2.y + move_mapper[0][dir];
                    int nx2 = now.p2.x + move_mapper[1][dir];

                    if (outOfIndex(ny1, nx1) || outOfIndex(ny2, nx2)) continue;
                    if (board[ny1][nx1] == 1 || board[ny2][nx2] == 1) continue;

                    q.offer(new TwoPoints(
                        new Point(ny1,nx1), new Point(ny2,nx2)
                    ));
                }
                
                // 회전
                // 가로방향일때
                if (now.p1.y == now.p2.y) {
                    // 축이 p1일때
                    int ny1 = now.p1.y;
                    int nx1 = now.p1.x;
                    
                    int ny2 = now.p1.y + 1;
                    int nx2 = now.p1.x;
                    
                    // 인덱스 범위 내 && 회전 경로가 벽이 아님 && 회전 도착지가 벽이 아님
                    // 회전 경로가 벽이 아님 -> 도착지의 y좌표, 축이 아닌 곳의 x좌표
                    if (!outOfIndex(ny2, nx2) && board[ny2][now.p2.x] == 0 && board[ny2][nx2] == 0) {
                        q.offer(new TwoPoints(
                            new Point(ny1,nx1), new Point(ny2,nx2)
                        ));
                    }
                    
                    ny2 = now.p1.y - 1;
                    if (!outOfIndex(ny2, nx2) && board[ny2][now.p2.x] == 0 && board[ny2][nx2] == 0) {
                        q.offer(new TwoPoints(
                            new Point(ny1,nx1), new Point(ny2,nx2)
                        ));
                    }
                    
                    // 축이 p2일때
                    ny1 = now.p2.y + 1;
                    nx1 = now.p2.x;
                    
                    ny2 = now.p2.y;
                    nx2 = now.p2.x;
                    
                    if (!outOfIndex(ny1, nx1) && board[ny1][now.p1.x] == 0 && board[ny1][nx1] == 0) {
                        q.offer(new TwoPoints(
                            new Point(ny1,nx1), new Point(ny2,nx2)
                        ));
                    }
                    
                    ny1 = now.p2.y - 1;
                    if (!outOfIndex(ny1, nx1) && board[ny1][now.p1.x] == 0 && board[ny1][nx1] == 0) {
                        q.offer(new TwoPoints(
                            new Point(ny1,nx1), new Point(ny2,nx2)
                        ));
                    }
                }
                                // 세로방향일때
                if (now.p1.x == now.p2.x) {
                    // 축이 p1일때
                    int ny1 = now.p1.y;
                    int nx1 = now.p1.x;
                    
                    int ny2 = now.p1.y;
                    int nx2 = now.p1.x + 1;
                    
                    // 인덱스 범위 내 && 회전 경로가 벽이 아님 && 회전 도착지가 벽이 아님
                    // 회전 경로가 벽이 아님 -> 축이 아닌 곳의 y좌표, 도착지의 x좌표
                    if (!outOfIndex(ny2, nx2) && board[now.p2.y][nx2] == 0 && board[ny2][nx2] == 0) {
                        q.offer(new TwoPoints(
                            new Point(ny1,nx1), new Point(ny2,nx2)
                        ));
                    }
                    
                    nx2 = now.p1.x - 1;
                    if (!outOfIndex(ny2, nx2) && board[now.p2.y][nx2] == 0 && board[ny2][nx2] == 0) {
                        q.offer(new TwoPoints(
                            new Point(ny1,nx1), new Point(ny2,nx2)
                        ));
                    }
                    
                    // 축이 p2일때
                     ny1 = now.p2.y;
                     nx1 = now.p2.x + 1;
                                         
                     ny2 = now.p2.y;
                     nx2 = now.p2.x;
                    
                    if (!outOfIndex(ny1, nx1) && board[now.p1.y][nx1] == 0 && board[ny1][nx1] == 0) {
                        q.offer(new TwoPoints(
                            new Point(ny1,nx1), new Point(ny2,nx2)
                        ));
                    }
                    
                    nx1 = now.p2.x - 1;
                    if (!outOfIndex(ny1, nx1) && board[now.p1.y][nx1] == 0 && board[ny1][nx1] == 0) {
                        q.offer(new TwoPoints(
                            new Point(ny1,nx1), new Point(ny2,nx2)
                        ));
                    }  
                }
            }
            
            second++;
        }
        
        // 항상 도착 가능하게 주어짐
        return second;
    }
    
    public boolean outOfIndex(int y, int x) {
        return y < 0 || y >= n || x < 0 || x >= n;
    }
    
    static class Point {
        int y;
        int x;
        
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
        
        @Override
        public boolean equals(Object o) {
            Point target = ((Point) o);
            return this.y == target.y && this.x == target.x;
        }
        
         @Override
        public int hashCode() {
            int result = y;
            result = 31 * result + x;
            return result;
        }
        
        public boolean isArrive() {
            return y == n - 1 && x == n - 1;
        }
    }
    
    static class TwoPoints {
        Point p1;
        Point p2;
        
        TwoPoints(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
        
        @Override
        public boolean equals(Object o) {
            TwoPoints target = ((TwoPoints) o);
            return ((this.p1).equals(target.p1) && (this.p2).equals(target.p2))
                || ((this.p1).equals(target.p2) && (this.p2).equals(target.p1));
        }
        
        // y,x -> 1~100 : 000_000
        @Override
        public int hashCode() {
            int result = p1.hashCode();
            result = 31 * result + p2.hashCode();
            return result;
        }
        
        public boolean isArrive() {
            return p1.isArrive() || p2.isArrive();
        }
    }
}

/**
이동과 회전이 가능함

이동은 그대로 유지하면서 이동
회전 시에는 사이에 벽이 있으면 안됨

방문체크를 어떻게 할지? 4차원 배열 -> 1억
이동 -> 4방향 1칸 이동
회전 -> 시계/반시계 * 축 2개 -> 90도 회전하는 4가지 경우가 존재
- 
*/