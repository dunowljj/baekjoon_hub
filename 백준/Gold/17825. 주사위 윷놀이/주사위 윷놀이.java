import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static Cell start = new Cell(0, 0);
    static int[] diceResults;
    static User[] users;
    static int max = 0;
    static int endIdx = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        Cell now = start;
        Cell c25 = null;

        // ^
        // | 22,24에 2를 곱하자
        int idx = 1;
        for (int i = 2; i <= 42; i+=2) {
            now.nextRed = new Cell(idx++, i);
            now = now.nextRed;

            if (now.val == 10) {
                Cell next = now.nextBlue = new Cell(idx++, 13);
                next = next.nextRed = new Cell(idx++, 16);
                next = next.nextRed = new Cell(idx++, 19);
                c25 = next.nextRed = new Cell(idx++ , 25);
            }

            if (now.val == 20) {
                Cell next = now.nextBlue = new Cell(idx++, 22);
                next = next.nextRed = new Cell(idx++, 24);
                next.nextRed = c25;
            }

            if (now.val == 30) {
                Cell next = now.nextBlue = new Cell(idx++, 28);
                next = next.nextRed = new Cell(idx++, 27);
                next = next.nextRed = new Cell(idx++, 26);
                next.nextRed = c25;
            }

            if (now.val == 40) {
                Cell next = c25.nextRed = new Cell(idx++, 30);

                next = next.nextRed = new Cell(idx++, 35);
                next.nextRed = now;
            }
        }

        endIdx = idx - 1;

        diceResults = new int[10];
        for (int i = 0; i < 10; i++) {
            diceResults[i] = Integer.parseInt(st.nextToken());
        }

        users = new User[4];
        for (int i = 0; i < 4; i++) {
            users[i] = new User(start);
        }

        backtracking(0, 0);
        System.out.print(max);
    }

    private static void backtracking(int depth, int total) {
        max = Math.max(max, total);

        if (depth == 10) return;

        for (int i = 0; i < 4; i++) {
            if (users[i].isEnd()) continue;

            Cell temp = users[i].now;

            int count = diceResults[depth];
            int score = users[i].move(count);

            if (!isConflict(i)) {
                backtracking(depth + 1, total + score);
            }

            users[i].now = temp;
        }
    }

    private static boolean isConflict(int idx) {
        User user = users[idx];
        if (user.isEnd()) return false;

        for (int i = 0; i < 4; i++) {
            if (idx == i) continue;
            User another = users[i];

            if (another.isEnd()) continue;
            if (user.now.idx == another.now.idx) return true;
        }

        return false;
    }

    static class Cell {
        int idx;
        int val;
        Cell nextRed;
        Cell nextBlue;

        public Cell(int idx, int val) {
            this.idx = idx;
            this.val = val;
        }
    }

    static class User {
        Cell now;

        public User(Cell now) {
            this.now = now;
        }

        public boolean isEnd() {
            return now.idx == endIdx;
        }

        public int move(int count) {
            if (now.nextBlue != null) {
                now = now.nextBlue;
            } else {
                now = now.nextRed;
            }
            count--;

            if (now.idx == endIdx) return 0;

            while (count-- > 0) {
                now = now.nextRed;
                if (now.idx == endIdx) return 0;
            }

            return now.val;
        }
    }
}
