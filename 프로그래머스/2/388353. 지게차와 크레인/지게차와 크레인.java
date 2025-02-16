import java.util.*;

class Solution {
    
    private static int n,m, answer;
    
    private static char[][] containers;
    
    private static final int EMPTY = 'x';
    private static final int[][] mapper = {{-1,1,0,0},{0,0,1,-1}};
    
    public int solution(String[] storage, String[] requests) {
        n = storage.length;
        m = storage[0].length();
        containers = new char[n][m];
        for (int i = 0; i < n; i++) containers[i] = storage[i].toCharArray();
        
        answer = n * m;
        
        for (String request : requests) {
            
            if (request.length() == 1) {
                fork(request.charAt(0));
                continue;
            } 
            
            if (request.length() == 2) {
                crane(request.charAt(0));
                continue;
            }
        }
        
        
        return answer;
    }
    
    private void fork(char target) {
        List<Point> removed = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (containers[i][j] == target && canFork(i, j)) {
                    removed.add(new Point(i, j));
                    answer--;
                }
            }
        }
        
        removed.forEach((p) -> containers[p.y][p.x] = EMPTY);
    }
    
    private boolean canFork(int y, int x) {
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][m];
        
        queue.offer(new Point(y, x));
        visited[y][x] = true;
        
        while (!queue.isEmpty()) {
            Point now = queue.poll();
            
            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];

                // 이동시 경계 벗어나는 경우
                if (ny < 0 || ny >= n || nx < 0 || nx >= m) return true;
                
                // 재탐색 방지
                if (visited[ny][nx]) continue;
                visited[ny][nx] = true;
                
                // 컨테이너 주변이 비어있는 경우 탐색 이어가기
                if (containers[ny][nx] == EMPTY) {
                    queue.offer(new Point(ny, nx));
                }
            }
        }
            
        
        return false;
    }
    
    private void crane(char target) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
               if (containers[i][j] == target) {
                   containers[i][j] = EMPTY;
                   answer--;
               }
            }
        }
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
/**
알파벳 하나 -> "해당 순간" 접근 가능 해당 알파벳 컨테이너 모두 꺼내기
알파벳 2번 반복 -> 크레인으로 모든 종류 꺼내기


100번의 요청
100번마다 50*50 모두 탐색 -> 250_000

*/