import java.util.*;

class Solution {
    
    private static final int[][] mapper = {{0,0,1,-1},{1,-1,0,0}};
    private static final int TABLE = -1;
    
    
    public int[] solution(int m, int n, int s, int[][] time_map) {
        int[] answer = {};
        
        Queue<Point> q = new LinkedList<>();
        q.offer(new Point(0,0));
        long[][] dp = new long[m][n];
        for (int i = 0; i < dp.length; i++) Arrays.fill(dp[i], Long.MAX_VALUE);
        dp[0][0] = 0;
        
        int time = 0;
        boolean find = false;
        while (!q.isEmpty()) {
            int size = q.size();
            
            for (int count = 0; count < size; count++) {
                Point now = q.poll();

                if (dp[now.y][now.x] > s) continue;
                
                if (now.y == m - 1 && now.x == n - 1) {
                    answer = new int[]{time, (int) dp[now.y][now.x]};
                    find = true;
                    continue;
                }

                // 해당 time에서 정답이 찾아진 경우, 해당 time에 도착한 모든 수다시간을 체크한다.
                if (find) continue;
                
                for (int i = 0; i < 4; i++) {
                    int ny = now.y + mapper[0][i];
                    int nx = now.x + mapper[1][i];

                    if (ny < 0 || ny >= m || nx < 0 || nx >= n) continue;
                    if (time_map[ny][nx] == TABLE) continue;
                    
                    if (dp[ny][nx] > dp[now.y][now.x] + time_map[ny][nx]) {
                        dp[ny][nx] = dp[now.y][now.x] + time_map[ny][nx];
                        q.offer(new Point(ny, nx));
                    }
                }
            }
            time++;
        }    
      
        return answer;
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
/**
길이 짧은게 우선
같다면 대화 시간의 합이 적은 곳 우선
대화 시간이 s를 초과한다면 불가능

대화시간의 합이 가장 적은 곳이 최우선이 아닌 짧은게 우선이다. s를 넘지 않으면서 가장 짧은 길을 찾아야 한다.

bfs로 가장 짧은 거리를 우선해서 구한다.
dp를 활용해 똑같은 곳을 방문할 경우, 이전보다 더 낮은 시간인 경우만 탐색한다.
t를 넘을 경우 종료한다.

만족하는 경로 하나 이상 존재
**/