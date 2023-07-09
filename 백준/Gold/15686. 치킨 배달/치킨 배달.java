import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static final int HOUSE = 1;
    private static final int CHICKEN_HOUSE = 2;
    private static List<Point> houses;
    private static List<Point> chickenHouses;
    private static int answer;
    private static Point[] opened;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        opened = new Point[M]; // 조합 저장하는 곳 // 공간 : M
        houses = new ArrayList<>(); // 공간 : N^2 -> 2N (*2)
        chickenHouses = new ArrayList<>(); // 공간 : M

        // 집과 치킨집의 위치 초기화
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int kindOfHouse = Integer.parseInt(st.nextToken());

                if (kindOfHouse == HOUSE) {
                    houses.add(new Point(i, j));
                } else if (kindOfHouse == CHICKEN_HOUSE) {
                    chickenHouses.add(new Point(i, j));
                }
            }
        }


        // M과 현재 치킨집 수가 같다면, 해당 거리만 검사하면 끝이다.
        if (M == chickenHouses.size()) {
            System.out.print(getChickenDistance(chickenHouses.toArray(Point[]::new)));
            System.exit(0);
        }

        answer = Integer.MAX_VALUE;
        dfs(M,0, 0);

        System.out.print(answer);
        br.close();
    }

    private static void dfs(int M, int start, int depth) {
        if (M == depth) {
            answer = Math.min(answer, getChickenDistance(opened));
            return;
        }

        for (int i = start; i < chickenHouses.size(); i++) {
            opened[depth] = chickenHouses.get(i);
            dfs(M, i + 1, depth + 1);
        }
    }

    private static int getChickenDistance(Point[] opened) {
        int chickenDist = 0;

        // 집에서 가장 가까운 치킨 집 찾기
        for (Point house : houses) {
            chickenDist += getCloseDist(opened, house);
        }

        return chickenDist;
    }

    private static int getCloseDist(Point[] opened, Point house) {
        int minDist = Integer.MAX_VALUE;

        for (Point chicken : opened) {
            int dist = chicken.distanceTo(house);
            minDist = Math.min(minDist, dist);
        }

        return minDist;
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int distanceTo(Point point) {
            return Math.abs(this.x - point.x) + Math.abs(this.y - point.y);
        }
    }
}
/*
 * 빈칸, 치킨 집, 집
 * 도시의 치킨거리 : 모든 치킨거리의 합
 *
 * 집의 개수 <= 2N        --> 최대 100
 * M <= 치킨 집 개수 <= 13 --> 최대 13 -> n(n+1) / 2 = 91가지 경우의 수

 * [풀이]
 * 모든 치킨집 조합 생성 -> 각 조합에서 도시의 치킨거리 구하기 -> 최솟값 출력
 *
 * [시간복잡도]
 * 집의 개수 * 각 치킨집까지 거리 계산 * 모든 치킨집 배치 경우의 수
 * 2N * M * mCn -> O(NM mCn)
 * 100 * 13 * 1716
 *
 * 100 * 13 * (2400)
 *
 * [공간복잡도]
 * O(N+M) -> M은 최대 13상수이므로 O(N)
 * 2차원 배열 사용시 O(N^2)
 *
 */