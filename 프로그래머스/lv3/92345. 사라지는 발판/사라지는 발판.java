class Solution {

    class Result {
        boolean winnable;
        int move;

        public Result(boolean winnable, int move) {
            this.winnable = winnable;
            this.move = move;
        }
    }
    class Grid {
        int[][] board;

        public Grid(int[][] board) {
            this.board = board;
        }

        public int get(Location loc) {
            return board[loc.x][loc.y];
        }

        public boolean outOfIndex(Location loc) {
            return loc.x < 0 || loc.x >= board.length || loc.y < 0 || loc.y >= board[0].length;
        }

        public boolean isEmptySpace(Location next) {
            return board[next.x][next.y] == 0;
        }

        public void toggleHold(Location next) {
            board[next.x][next.y] ^= 1;
        }
    }
    class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Location)) return false;

            Location location = (Location) o;

            if (x != location.x) return false;
            return y == location.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
    static int[][] mapper = {{1,-1,0,0},{0,0,1,-1}};


    public int solution(int[][] board, int[] aloc, int[] bloc) {

        Grid grid = new Grid(board);
        Location aLoc = new Location(aloc[0], aloc[1]);
        Location bLoc = new Location(bloc[0], bloc[1]);

        Result result = moveA(grid, aLoc, bLoc, 0);
        /*System.out.println("result.winnable = " + result.winnable);
        System.out.println("result.move = " + result.move);*/

        return result.move;
    }


    private Result moveA(Grid grid, Location aLoc, Location bLoc, int depth) {
        boolean winnable = false; // 움직이지 못하면 자동 false 처리 된다. (패배)
        int win_move = 5*5;
        int lose_move = depth;

//            testPrint(aLoc, bLoc, depth, winnable);

        // 상대의 움직임으로 있던 자리의 발판이 사라졌을 때 패배
        if (grid.isEmptySpace(aLoc)) return new Result(winnable, lose_move);

        for (int i = 0; i < 4; i++) {
            int nx = aLoc.x + mapper[0][i];
            int ny = aLoc.y + mapper[1][i];

            Location next = new Location(nx, ny);

            // 인덱스 초과 혹은 발판 없는 빈곳일때
            if (grid.outOfIndex(next) || grid.isEmptySpace(next)) continue;

            grid.toggleHold(aLoc);
            Result result = moveB(grid, next, bLoc, depth + 1);
            grid.toggleHold(aLoc);

            winnable |= !result.winnable;
            if (!result.winnable) win_move = Math.min(result.move, win_move);
            else lose_move = Math.max(result.move, lose_move);
        }

        int move = winnable ? win_move : lose_move;
        return new Result(winnable, move);
    }

    private Result moveB(Grid grid, Location aLoc, Location bLoc, int depth) {
        boolean winnable = false;
        int win_move = 5*5;
        int lose_move = depth;

//            testPrint(aLoc, bLoc, depth, winnable);

        if (grid.isEmptySpace(bLoc)) return new Result(winnable, lose_move);

        for (int i = 0; i < 4; i++) {
            int nx = bLoc.x + mapper[0][i];
            int ny = bLoc.y + mapper[1][i];

            Location next = new Location(nx, ny);

            if (grid.outOfIndex(next) || grid.isEmptySpace(next)) continue;

            grid.toggleHold(bLoc);
            Result result = moveA(grid, aLoc, next, depth + 1);
            grid.toggleHold(bLoc);

            winnable |= !result.winnable;
            if (!result.winnable) win_move = Math.min(result.move, win_move);
            else lose_move = Math.max(result.move, lose_move);
        }

        int move = winnable ? win_move : lose_move;
        return new Result(winnable, move);
    }

    private static void testPrint(Location aLoc, Location bLoc, int depth, boolean winnable) {
        System.out.println("B move; depth :" + depth);
        System.out.println("aLoc = " + aLoc);
        System.out.println("bLoc = " + bLoc);
        System.out.println("winnable = " + winnable);
    }
}
/*
[시작 조건]
무조건 A 먼저 이동
처음 올려놓는 곳엔 발판이 무조건 있음.
초기 발판이 같을수도 있다.

[움직임 규칙]
승자 -> 최대한 빨리 이기기 / 패자 -> 최대한 오래 버티기
인접 4칸으로 이동 가능

[종료 조건]
1) 같은 발판에 위치해서 다음에 사라짐 -> 다음턴 패배
2) 움직일 곳이 더 이상 없음 -> 바로 패배

# 문제
최선의 수로 움직이는 경우를 어떻게 구하는가? 꼭 구할 필요가 있는가? 단순히 최대, 최솟값을 구하면 원하는 값이 나오지 않는다.
- 상대방이 고의로 일찍 패배할 경우 최솟값이 더 작아진다.
- 상대방이 봐줘서 턴을 더 끌 경우 최댓값이 더 커진다.

# 해결
!!! 무조건 이길 방법이 있는 곳인지 어떻게 알까? -> 무조건 이긴다는 것 == 상대방이 어디로 이동하든 대처할 방법이 있다.
--> 내가 어떤 이동을 했을 때, "최선을 다하면 무조건 이기는 경우"가 하나이상 존재한다. -> 이런 경우를 어떻게 프로그래밍 할 수 있을까?

예제 1에서 처음에 A가 이동할 수 있는 위치는 (0,0), (1,1), (1,0)이다.

A(0,1) -> AU(0,0)
       -> AR(1,1)
       -> AD(2,0)

여기서 오른쪽으로 이동해서 (1,1)로 이동한 경우만 살펴보자.

## [예제 1] A가 처음에 (1,1)로 이동하는 경우
A가 (1,1)로 이동하면, 최선을 다하는 경우 무조건 승리한다.
4방향 탐색을 하고, 인덱스에서 벗어난 곳은 방문하지 않는다.

해당 경우에 B가 움직일 수 있는 방향은 위와 아래 둘뿐이다. 참고로 B의 움직임에 대해 A가 같은 방향으로 따라가면 무조건 승리할 수 있다.
도식으로 나타내면

AR(1,1) -> BU(0,2)
        -> BD(1,2)

이것을 dfs로 탐색하고 리프노드까지 따라가보자. BU(B가 위로 이동한)의 경우를 살펴보면,

BU(0,2) -> AU(0,1)
        -> AD(2,1)

### A가 이기려고 하는 경우
AR(1,1) -> BU(0,2) -> AU(0,1) -> BL(0,1) -> AL(0,0) -> [B 떨어짐, A 승리]

### A가 지는 경로로 이동하는 경우
AR(1,1) -> BU(0,2) -> AD(2,1) -> BL(0,1) -> AR or AL -> BL(0,0) -> A 이동불가, B 승리

## 결론
결론적으로 내 이동에 대해 발생한 상대방의 경우의 수에 모두 대처할 수 있는지를 구해야 한다.
dfs 탐색 후 리프노드를 따라가다 보면 내 이동에 따라 상대방이 무조건 지는 경우가 발생한다. 그런 경우의 존재는 최선을 다했을때 승리할 수 있다는 것을 의미한다.
이전 depth에 최선을 다했을때 승리할 수 있는지 여부를 계속 계산하면서 올라가면 된다.
*/
