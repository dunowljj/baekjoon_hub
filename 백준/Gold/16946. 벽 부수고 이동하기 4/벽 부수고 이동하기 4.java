import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class Main {

    private static final int[][] mapper = {{0, 0, -1, 1}, {-1, 1, 0, 0}};
    public static final int WALL = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Space[][] spaces = new Space[N][M];

        for (int y = 0; y < N; y++) {
            String row = br.readLine();
            for (int x = 0; x < M; x++) {
                if (row.charAt(x) == '1') {
                    spaces[y][x] = new Space(true, -1);
                } else {
                    spaces[y][x] = new Space(false, -1);
                }
            }
        }

        // 근접한 0을 모두 센 count값으로 해당 0들이 있는 모든 칸을 채워준다.
        // 같은 그룹인지도 체크해야 한다...
        int groupNo = 0;
        List<Integer> groupSize = new ArrayList<>();
        boolean[][] visited = new boolean[spaces.length][spaces[0].length];
        for (int y = 0; y < spaces.length; y++) {
            for (int x = 0; x < spaces[y].length; x++) {
                if (!spaces[y][x].isWall && !visited[y][x]) {
                    spaces[y][x] = countZeroByBFS(y, x, visited, spaces, groupNo, groupSize);
                    groupNo++;
                }
            }
        }
//        printCount(spaces, groupSize);
//        printGroupNo(spaces);

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < spaces.length; y++) {
            for (int x = 0; x < spaces[y].length; x++) {
                if (spaces[y][x].isWall) {
                    sb.append(getCount(y, x, spaces, groupSize) % 10);
                } else {
                    sb.append(0);
                }
            }
            sb.append(System.lineSeparator());
        }

        System.out.println(sb.toString().trim());
    }

    private static Space countZeroByBFS(int i, int j, boolean[][] visited, Space[][] spaces, int groupNo, List<Integer> groupSize) {
        visited[i][j] = true;

        Queue<Point> queue = new LinkedList<>();
        Point start = new Point(i, j);

        queue.offer(start);
        spaces[i][j].groupNo = groupNo;

        int count = 1;
        while (!queue.isEmpty()) {
            Point now = queue.poll();

            for (int k = 0; k < 4; k++) {
                int ny = now.y + mapper[0][k];
                int nx = now.x + mapper[1][k];

                if (ny < 0 || ny >= spaces.length || nx < 0 || nx >= spaces[0].length) continue;
                if (spaces[ny][nx].isWall || visited[ny][nx]) continue; // 벽

                visited[ny][nx] = true;
                spaces[ny][nx].groupNo = groupNo;
                count++;

                queue.offer(new Point(ny, nx));
            }
        }

        count %= 10;
        groupSize.add(count);

        return spaces[i][j];
    }

    private static int getCount(int y, int x, Space[][] spaces, List<Integer> groupSize) {
        int count = 1;
        Set<Integer> set = new HashSet<>();

        for (int k = 0; k < 4; k++) {
            int ny = y + mapper[0][k];
            int nx = x + mapper[1][k];

            if (ny < 0 || ny >= spaces.length || nx < 0 || nx >= spaces[0].length) continue;
            if (spaces[ny][nx].isWall) continue;
            if (set.contains(spaces[ny][nx].groupNo)) continue;

            set.add(spaces[ny][nx].groupNo);
            count += groupSize.get(spaces[ny][nx].groupNo);
        }

        return count;
    }

    static class Point {
        int y;
        int x;
        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

    }
    static class Space {

        boolean isWall;
        int groupNo;
        public Space(boolean isWall, int groupNo) {
            this.isWall = isWall;
            this.groupNo = groupNo;
        }

    }
    private static void printGroupNo(Space[][] spaces) {
        System.out.println("---GroupNo---");
        for (int y = 0; y < spaces.length; y++) {
            for (int x = 0; x < spaces[y].length; x++) {
                if (spaces[y][x].isWall) {
                    System.out.print("0");
                } else {
                    System.out.print(spaces[y][x].groupNo);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printCount(Space[][] spaces, List<Integer> groupSize) {
        System.out.println("---Count---");
        for (int y = 0; y < spaces.length; y++) {
            for (int x = 0; x < spaces[y].length; x++) {
                if (spaces[y][x].groupNo == -1) {
                    System.out.print("0");
                } else {
                    System.out.print(groupSize.get(spaces[y][x].groupNo));
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
/**
 * 순회하며 벽 찾기, 탐색시 1이상이면 벽
 * 해당 벽 위치에서 bfs로 개수 세고 갱신, 1이상이면 방문 불가
 *
 *
 */