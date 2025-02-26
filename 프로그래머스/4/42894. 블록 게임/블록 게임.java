import java.util.*;

class Solution {
    private static final int[][] mapper = {{0,0,-1,1},{1,-1,0,0}};
   
    private boolean[] isBlockedColumn;
    private boolean[][] visited;
    private int answer = 0;

    private static int N;
    private int[][] gameBoard;
    
    public int solution(int[][] board) {
        N = board.length;
        gameBoard = board;
        visited = new boolean[N][N];
        isBlockedColumn = new boolean[N];
        
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (visited[r][c] || gameBoard[r][c] == 0) continue;
                checkBlock(new Point(r,c));
            }
        }
        
        return answer;
    }
    
    public void checkBlock(Point point) {
        List<Point> points = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();
        
        points.add(point);
        queue.offer(point);
        visited[point.y][point.x] = true;
        
        // 블록의 값
        int val = gameBoard[point.y][point.x];
        // 직사각형 모서리 구하기
        int topY = point.y; // == minY, 첫 위치가 제일 위임
        int bottomY = point.y;
        int leftX = point.x;
        int rightX = point.x;
        
        boolean canRemove = true;
        while (!queue.isEmpty()) {
            Point now = queue.poll();
            
            for (int i = 0; i < 4; i++) {
                int ny = now.y + mapper[0][i];
                int nx = now.x + mapper[1][i];
                
                if (ny < 0 || ny >= N || nx < 0 || nx >= N) continue;                
                if (gameBoard[ny][nx] != val || visited[ny][nx]) continue;
                
                visited[ny][nx] = true;       
                
                points.add(new Point(ny,nx));
                queue.offer(new Point(ny,nx));
                
                topY = Math.min(ny, topY);
                bottomY = Math.max(ny, bottomY);
                leftX = Math.min(nx, leftX);
                rightX = Math.max(nx, rightX);
            }         
        }
        
        int topCount = 0;
        for (Point p : points) if (p.y == topY) topCount++;
        
        // 위에서 덮는 모양일때 || 세워진 모양이며, 중간의 가로길이가 2일때 [ㅏ, ㅓ]
        if (topCount >= 2 ||
           (bottomY - topY == 2 && gameBoard[topY + 1][leftX] == gameBoard[topY + 1][rightX])
        ) {
            canRemove = false;
        }
        
        // 삭제 가능한 모양이라면, 구한 직사각형을 채워야할 위치 구하기
        if (canRemove) {

            List<Point> emptyCandidates = new ArrayList<>();
            for (int y = topY; y <= bottomY; y++) {
                for (int x = leftX; x <= rightX; x++) {
                    if (gameBoard[y][x] != val) {
                        emptyCandidates.add(new Point(y,x));
                    }
                }
            }
            
            for (Point ec : emptyCandidates) {
                
                // 위에서 막혀서 넣을 수 없는 곳
                if (isBlockedColumn[ec.x]) {
                    block(points);
                    return;
                }
                
                // 다른 블록이 채워야할 부분에 끼어들어 있는 경우 "ㄴㄴ, ㄴㄱ"
                if (gameBoard[ec.y][ec.x] > 0) {
                    checkBlock(new Point(ec.y,ec.x));
                    // 여기가 문제. 여기는 시작점이 아닌. 해당 블록의 랜덤지점이다. 
                }
                
                // 탐색 후 재확인
                if (isBlockedColumn[ec.x]) {
                    block(points);
                    return;
                }
            }
            
            // 블록 삭제 가능 -> 0으로 지워버리기, 카운트
            for (int y = topY; y <= bottomY; y++) {
                for (int x = leftX; x <= rightX; x++) {
                    gameBoard[y][x] = 0;
                }
            }
            answer++;
            
        // 삭제 불가능이라면 다 막아버리기
        } else {
            block(points);
        }
    }
    
    private void block(List<Point> points) {
        for (Point p : points) {
            isBlockedColumn[p.x] = true;
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

윗 블록이 없어져야 쌓을 수 있는 경우가 있다.
직사각형을 만들기 위한 빈자리에 넣을 수 없는 경우 윗블록 상관없이 불가능이다.
검은 블록을 일직선으로만 떨어뜨릴 수 있다. -> 가능 경로가 막혔는지 확인하는 bool 배열 만들기
없앤 블록 덩어리당 1 카운트

0) 순회를 하며 블록을 찾는다. 가장 위에 있는 블록부터 찾아야 한다. (왼 위부터 순회하면 됨)
1) 순회를 하며 특정 블록을 만났을때, bfs로 같은 숫자를 탐색. 
  - y,x좌표의 최대,최소값을 이용해서 직사각형 공간을 찾을 수 있다.
  - 좌표 정보로 처리불가능 블록을 구분 가능하다. 왼위부터 순회시, 처음 마주친 블록 위치는 가장 높은 곳이다. 해당 y높이에 같은 숫자가 여러개 있다면, 아래가 같힌 모양의 블록이다.

2) 처리 불가능 블록을 구분한다. 
->> 처리 가능 블록이고, 위가 막히지 않았다면 제거하고 카운트한다.
->> 처리 불가능이면 해당 열을 block 처리한다.

- 최대 y의 차가 3이면 세워진 블록. 아니면 눕힌 블록 || y종류가 3개면 세워진 블록, 더 높은y에 있는 x좌표가 2개 이상인경우 불가능

- 불가능 : ㅜ ㅏ ㅓ ㄱ 대칭ㄱ

ㄴㄱ이 맞물린 경우 퇴치가 불가능

3) 

ㅜ,ㅏ, ㅓ 모양의 경우 무조건 치울수 없다.
ㄱ, ㄱ대칭도 마찬가지.

ㄴ,ㅓ 모양이 겹쳐있는 경우, 불가능하다. ㄴ,ㅜ 도 마찬가지. ㄴㅗ가 겹치면 위에서부터 처리하면 된다. 
ㄴ 위에 ㄴ이 얹였다면, 위에부터 제거하면 된다.

처리 가능 블록의 경우, 어떤 모양이 되었든 어떻게 위에 얹더라도 하나씩 처리하면된다.

<왼위부터 탐색 시,>
ㄴ위에 ㄴ인 경우, 높이가 같다면 선 처리 안됨.
ㄴㄱ
ㅗㄱ 포개어진 경우 이전 탐색에서 안걸리고 들어온다. ㄱ은 불가능 모양이므로 알아서 block된다.

*/