import java.util.*;

class Solution {
    int[][] direction = {{1, 0, 0, -1},{0, -1, 1, 0}}; // dlru 순서
    char[] dir = {'d', 'l', 'r', 'u'};
    boolean isFinished = false;
    
    StringBuilder sb = new StringBuilder(2500);
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        // 홀짝 검사
        int distance = Math.abs(x - r) + Math.abs(y - c);
        if (distance % 2 != k % 2 || distance > k) return "impossible";
        
        dfs (n, m, x, y, r, c, k);
        
        return sb.toString();
    }
    
    private void dfs(int n, int m, int x, int y, int r, int c, int k) {
        if (isFinished) return;
        if (getDistance(x, y, r, c) > k) return; // 불가능 루트 : 거리보다 남은 이동수가 큼
        
        if (k == 0) {
            if (x == r && y == c) isFinished = true;
            return;
        }
        
        // dlru 순 탐색
        for (int i = 0; i < 4; i++) {
            int nx = x + direction[0][i];
            int ny = y + direction[1][i];
            
            // 인덱스 범위 체크
            if (nx < 1 || nx > n || ny < 1 || ny > m) continue;
            
            sb.append(dir[i]);

            dfs(n, m, nx, ny, r, c, k - 1);
            if (isFinished) return;

            sb.deleteCharAt(sb.length() - 1);
        }
    }
    
    private int getDistance(int x, int y, int r, int c) {
        return Math.abs(x - r) + Math.abs(y - c);
    }
}
/*
- 사전 순 d -> l -> r -> u
- 탈출까지 이동거리가 정해져있다.
- 모든 격자를 2번 이상 방문 가능하다.

- 처음에 거리의 홀짝 계산해서 불가능 여부 판독하기


- 사전순 방향으로 먼저 이동한 경우가 우선된다.
이동거리가 지나치게 많으면 남은 횟수에서 끝까지 내려가고 끝까지 왼쪽, 끝까지 오른쪽으로 간다음 e를 찾아가는게 가장 빠르다.
남은 만큼 dlrudlrudl.. 제자리 순회 -> E로 도착

### 시간
k가 최대 2500이다. 50*50에서 2500번의 이동을 한다면, bfs로는 불가능 하다. 매 번 4가지 선택지가 있으므로, 무수히 많은 경우의 수가 생긴다.


### 풀이
1) dlru 우선 순위를 가지고 이동하도록 dfs를 한다.
2) 매번 정답까지의 거리를 계산해서 정답에 도달할 수 없는 상태이면 탐색을 포기한다.
3) stringbuilder로 정답을 만든다.


*/