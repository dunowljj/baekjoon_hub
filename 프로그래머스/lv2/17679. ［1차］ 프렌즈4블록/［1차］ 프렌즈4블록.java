import java.util.*;

class Solution {

    private static final char EMPTY_SPACE = '-';
    Set<Integer> updatedCols = new HashSet<>();
    Set<Point> targets = new HashSet<>();
    int answer = 0;

    public int solution(int m, int n, String[] board) {
        // 2차원 배열로 변환
        char[][] grid = new char[m][n];
        for (int i = 0; i < board.length; i++) {
            grid[i] = board[i].toCharArray();
        }

        remove4Blocks(m, n, grid);
        return answer;
    }

    private void remove4Blocks(int m, int n, char[][] grid) {
        // 전체 순회하며 2*2 Block 위치 찾기
        while (find4Blocks(m, n, grid)) {
            remove4Blocks(grid); // 제거하고 정답 count
            dropBlocks(grid); // 빈칸 떨어뜨려서 채우기
        }
    }

    // 찾은 4Blocks 제거하고, 정답 개수 세기
    private void remove4Blocks(char[][] grid) {
        for (Point target : targets) {
            grid[target.x][target.y] = EMPTY_SPACE;
            answer++;
        }
        targets.clear();
    }

    private void dropBlocks(char[][] grid) {
        for (int updatedCol : updatedCols) {
            dropOneCol(updatedCol, grid);
        }
        updatedCols.clear();
    }

    private boolean find4Blocks(int m, int n, char[][] grid) {
        boolean removed = false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == EMPTY_SPACE) continue;

                if (is4Block(m, n, i, j, grid)) {
                    targets.add(new Point(i, j));
                    targets.add(new Point(i + 1, j));
                    targets.add(new Point(i, j + 1));
                    targets.add(new Point(i + 1, j+ 1));

                    updatedCols.add(j);
                    updatedCols.add(j + 1);
                    removed = true;
                }
            }
        }
        return removed;
    }

    private boolean is4Block(int m, int n, int i, int j, char[][] grid) {
        if (i + 1 == m || j + 1 == n) return false; // 가장자리인 경우 Block일 수 없다.

        char now = grid[i][j];
        return now == grid[i + 1][j] &&
                now == grid[i][j + 1] &&
                now == grid[i + 1][j + 1];
    }

    private void dropOneCol(int updatedCol, char[][] grid) {
        // 각 줄 검사해서 채우기
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < grid.length; i++) {
            char now = grid[i][updatedCol];
            if (now == EMPTY_SPACE) continue;
            stack.push(now);
        }

        for (int i = grid.length - 1; i >= 0; i--) {
            if (!stack.isEmpty()) grid[i][updatedCol] = stack.pop();
            else grid[i][updatedCol] = EMPTY_SPACE;
        }
    }

    private void testPrint(char[][] grid) {
        for (char[] chars : grid) {
            for (char ch : chars) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
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

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
