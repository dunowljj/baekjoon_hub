import java.util.*;

class Solution {
    static final int BOARD_SIZE = 4;
    int[][] mapper = {{0, 0, 1, -1}, {-1, 1, 0, 0}};
    int min = Integer.MAX_VALUE;

    class Card {
        int no;
        Point point;
        Card pair;

        public Card(int no, Point point) {
            this.no = no;
            this.point = point;
        }

        public void setPair(Card pair) {
            this.pair = pair;
        }
    }

    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }
    }

    public int solution(int[][] board, int r, int c) {

        /**
         * CardList 초기화
         */
        List<Card> cards = new ArrayList<>();
        Map<Integer, Card> noMap = new HashMap();
        int enterCount = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int cardNo = board[i][j];
                if (cardNo != 0) {
                    enterCount++;

                    Card card = new Card(cardNo, new Point(i, j));

                    if (!noMap.containsKey(cardNo)) {
                        noMap.put(cardNo, card);

                    // 같은 그림의 카드가 두 번째 등장
                    } else {
                        Card pair = noMap.get(cardNo);

                        // pair 맺어주기
                        card.setPair(pair);
                        pair.setPair(card);
                    }

                    cards.add(card);
                }
            }
        }

        boolean[] visited = new boolean[cards.size()];
        findMinShortCut(visited, new Point(r, c), cards, 0, board); // dfs

        return min + enterCount;
    }

    private void findMinShortCut(boolean[] visited, Point start, List<Card> cards, int distance, int[][] board) {
        if (allVisited(visited)) {
            min = Math.min(min, distance);
            return;
        }

        for (int i = 0; i < cards.size(); i++) {
            if (!visited[i]) {
                Card card = cards.get(i);

                // 한 번에 카드 한쌍 탐색 : 모든 카드중 하나 탐색 -> pair인 카드 탐색
                Point now = card.point;
                Point pair = card.pair.point;

                // 한 번에 한 쌍을 탐색. List의 카드와 그 카드의 pair카드
                int findDistance = getDistance(start, now, board) + getDistance(now, pair, board);

                // 짝 맞은 카드 치우기 && 방문체크
                int idx = cards.indexOf(card.pair);
                int temp1 = board[now.x][now.y];
                int temp2 = board[pair.x][pair.y];

                visited[i] = true;
                visited[idx] = true;
                board[now.x][now.y] = 0;
                board[pair.x][pair.y] = 0;
                findMinShortCut(visited, new Point(pair.x, pair.y), cards, distance + findDistance, board);

                // 원복
                visited[i] = false;
                visited[idx] = false;
                board[now.x][now.y] = temp1;
                board[pair.x][pair.y] = temp2;
            }
        }
    }

    private boolean allVisited(boolean[] visited) {
        for (boolean visit : visited) {
            if (!visit) return false;
        }
        return true;
    }

    private int getDistance(Point start, Point dest, int[][] board) {
        Queue<Point> queue = new LinkedList<>();
        int[][] visited = new int[BOARD_SIZE][BOARD_SIZE];

        queue.offer(start);
        while (!queue.isEmpty()) {
            Point now = queue.poll();

            int nowDist = visited[now.x][now.y];
            if (now.equals(dest)) return nowDist;

            // ctrl + 이동
            for (int dir = 0; dir < 4; dir++) {
                int nx = now.x + mapper[0][dir];
                int ny = now.y + mapper[1][dir];

                if (outOfIndex(nx, ny)) continue;

                // 카드가 없는 경우 계속 이동
                while (board[nx][ny] == 0) {
                    nx += mapper[0][dir];
                    ny += mapper[1][dir];

                    // 맨 끝이면 이동 종료 : 인덱스 벗어나면 뒤로 한칸 돌려서 사용
                    if (outOfIndex(nx, ny)) {
                        nx -= mapper[0][dir];
                        ny -= mapper[1][dir];
                        break;
                    }
                }

                // visited 배열 갱신하며 최소거리 구하기
                if (visited[nx][ny] != 0 && visited[nx][ny] <= nowDist + 1) continue;

                visited[nx][ny] = nowDist + 1;
                queue.offer(new Point(nx, ny));
            }

            // 한 칸 이동
            for (int dir = 0; dir < 4; dir++) {
                int nx = now.x + mapper[0][dir];
                int ny = now.y + mapper[1][dir];

                // visited 배열 갱신하며 최소거리 구하기
                if (outOfIndex(nx, ny) || visited[nx][ny] != 0 && visited[nx][ny] <= nowDist + 1) continue;

                visited[nx][ny] = nowDist + 1;
                queue.offer(new Point(nx, ny));
            }
        }

        return visited[dest.x][dest.y];
    }

    private boolean outOfIndex(int nx, int ny) {
        return nx < 0 || nx >= BOARD_SIZE || ny < 0 || ny >= BOARD_SIZE;
    }
}

/*
어느 카드를 먼저 뒤집거나 영향을 줄 수 있다.
커서의 위치에 있는 그림을 바로 뒤집으면 안될수도 있다.

1 0 0 0
0 0 0 2
0 0 2 1
3 3 0 0

enter 키는 그냥 숫자수만 나중에 더해주면된다.
(x) 각 숫자간의 거리를 미리 재버려서 거리를 캐싱한다. -> 한 쌍의 카드를 발견하면 제거해야한다. 캐싱 불가

### 이동
그냥 이동은 화살표방향을 누르면 1칸 이동이다.
ctrl + 이동은 해당 방향에서 가장 가까운 카드로 한 번에 이동이다.
ctrl 이동을 통한 이동을 구현하기 위해서는 탐색법을 써야한다? bfs로 4방향 + ctrl 4방향을 해야한다.

## 전체 풀이
r,c부터 시작해서 card가 있는 위치에 대해 dfs를 한다. -> 12 * 10 * 8 * 6 * 4 * 2가지(45000이상) 탐색의 경우의 수가 있다. 한 카드를 오픈하면 다음 목적지는 정해져있기 때문이다.
O(45000 * bfs 가짓수)?
- pair를 찾은 카드는 없는 취급을 하기 위해서 거리는 그때그때 bfs로 구한다. -> 8방향(한칸 이동4 + ctrl 이동4) 탐색
- pair인 Card의 위치를 미리 저장
- List<Card> cards의 각 index로 방문여부를 체크
- enter를 제외한 조작횟수를 체크하고 따로 더해준다.
*/
