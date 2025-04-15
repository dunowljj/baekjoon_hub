import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static int answer = 0;
    public static final int INITIAL_NUTRIENT = 5;

    public static final int[][] mapper = {{1, 1, 1, 0, 0, -1, -1, -1}, {-1, 0, 1, -1, 1, -1, 0, 1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        answer = M;
        Cell[][] A = new Cell[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                A[i][j] = new Cell(Integer.parseInt(st.nextToken()));
            }
        }


        // 나무정보
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            A[x][y].add(z);
        }

        while (K-- > 0) {
            // spring, summer
            for (int x = 1; x <= N; x++) {
                for (int y = 1; y <= N; y++) {
                    Cell cell = A[x][y];
                    if (cell.hasAnyTree()) {
                        cell.eatNutrient();
                    }
                }
            }

            // fall
            for (int x = 1; x <= N; x++) {
                for (int y = 1; y <= N; y++) {
                    Cell cell = A[x][y];
                    if (cell.hasAnyTree()) {
                        propagate(N, A, x, y, cell);
                    }
                }
            }

            // winter
            for (int x = 1; x <= N; x++) {
                for (int y = 1; y <= N; y++) {
                    Cell cell = A[x][y];
                    cell.increase();
                }
            }
        }

        System.out.print(answer);
    }

    private static void propagate(int N, Cell[][] A, int x, int y, Cell cell) {
        int count = cell.countProducible();

        for (int i = 0; i < 8; i++) {
            int nx = x + mapper[0][i];
            int ny = y + mapper[1][i];

            if (nx < 1 || nx > N || ny < 1 || ny > N) continue;

            for (int c = 0; c < count; c++) A[nx][ny].add(1);
            answer += count;
        }
    }

    static class Cell {
        int nutrient;
        int increase;
        Queue<Integer> ages;

        public Cell(int increase) {
            this.increase = increase;
            this.nutrient = INITIAL_NUTRIENT;
            ages = new PriorityQueue<>();
        }

        public void add(int age) {
            ages.offer(age);
        }

        public boolean hasAnyTree() {
            return ages.size() > 0;
        }

        public void eatNutrient() {
            List<Integer> temp = new ArrayList<>();
            int remainNutrient = 0; // 죽은 나무로부터의 양분

            // 어린 나무부터 양분 흡수
            while (!ages.isEmpty()) {

                int age = ages.poll();

                // 흡수
                if (age <= nutrient) {
                    temp.add(age + 1);
                    nutrient -= age;

                // 나무 죽음
                } else {
                    remainNutrient += age / 2;
                    answer--;
                }
            }

            ages.addAll(temp);

            // 여름에 양분 추가
            nutrient += remainNutrient;
        }


        public int countProducible() {
            int count = 0;
            Queue<Integer> temp = new LinkedList<>();

            while (!ages.isEmpty()) {
                if (ages.peek() % 5 == 0) count++;
                temp.offer(ages.poll());
            }

            ages.addAll(temp);

            return count;
        }

        public void increase() {
            this.nutrient += increase;
        }
    }
}

/**
 * [봄]
 * 한 칸에 여러 나무 -> 어린 나무가 양분을 먼저 먹는다.
 * 양분이 부족하여 나이만큼 양분을 못먹으면 즉시 죽는다.
 * [여름]
 * 죽은 나무가 양분이 됨. 나이/2 만큼 양분으로 추가. 소수점 아래 버림
 *
 * [가을]
 * 번식 -> 나이가 5의 배수라면, 인접한 8개칸에 나이가 1인 나무가 생김
 *
 * [겨울]
 * 로봇이 양분 추가
 *
 * N 1~10
 * M 1~100
 * 양분 1~100
 * K 1~1000
 */