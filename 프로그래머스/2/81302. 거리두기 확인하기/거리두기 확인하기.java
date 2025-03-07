import java.util.*;

class Solution {
    
    private static final char PERSON = 'P';
    private static final char TABLE = 'O';
    private static final char PARTITION = 'X';
    
    private static final int[][] mapper = {{-1,1,0,0}, {0,0,1,-1}};
    
    private static final int n = 5;
    
    private static int[] answer = new int[5];
    
    public int[] solution(String[][] places) {
        boolean[][][] visited = new boolean[5][5][5];
        
        for (int i = 0; i < 5; i++) {
            answer[i] = checkRoom(places[i], visited[i]);
        }
        
        return answer;
    }
    
    private int checkRoom(String[] place, boolean[][] visited) {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (visited[y][x]) continue;
                
                char val = place[y].charAt(x); 

                if (val == PERSON && !isSeprated(place, y, x, visited)) {
                    return 0;
                }
            }       
        }
        
        return 1;
    }
    
    private boolean isSeprated(String[] place, int y, int x, boolean[][] visited) {
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(y, x));
        visited[y][x] = true;
        int depth = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            if (depth == 2) return true;
            for (int s = 0; s < size; s++) {
                Point now = queue.poll();
                
                for (int i = 0; i < 4; i++) {
                    int ny = now.y + mapper[0][i];
                    int nx = now.x + mapper[1][i];

                    if (ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx]) continue;
                    visited[ny][nx] = true;

                    char next = place[ny].charAt(nx);
                    if (next == PARTITION) continue;
                    if (next == PERSON) return false;

                    queue.offer(new Point(ny, nx));
                }
            }
            depth++;
        }
        
        return true;
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


/*
각 사람 위치에서 4방향 bfs탐색
- 파티션일경우 탐색 종료
- 빈 테이블이면 진행. 다른 응시자 발견 시 움직인 거리가 2이하라면 거리두기 위반

맨해튼 거리가 아닌 실제 이동거리 기준으로 계산. 어짜피 파티션인 경우 막힌걸로 판단하기 때문이다.

### 주의 
- 2이하 거리검증 부분을 사용하더라도 정확히 2depth를 탐색하지 않으면 문제가 생긴다.
- 현재 시작점 기준으로 검증하는데, 방문체크를 해버리기때문에 다른 탐색을 방해할 수 있다.
- ex) P O O P P O P -> 첫P에서 시작하면, 거리두기를 하지 않은 다른 P를 방문체크하여 다시 방문하지 않게 만듬.

*/