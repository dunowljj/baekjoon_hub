import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static Cell start = new Cell(0, 0);
    static int[] diceResults;
    static User[] users;
    static int max = 0;
    static Cell END = null;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        Cell now = start;
        Cell c25 = null;

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

        END = now;
        END.val = 0;

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
        if (depth == 10) {
            max = Math.max(max, total);
            return;
        }

        boolean[] tried = new boolean[50];

        for (int i = 0; i < 4; i++) {
            if (users[i].isEnd()) continue;
            int pos = users[i].now.idx;
            if (tried[pos]) continue;
            tried[pos] = true;

            Cell temp = users[i].now;
            Cell dest = users[i].move(diceResults[depth]);

            // 도착끼리는 충돌x
            if (isConflict(i , dest)) continue;

            users[i].now = dest;
            backtracking(depth + 1, total + dest.val);
            users[i].now = temp;
        }
    }

    private static boolean isConflict(int idx, Cell dest) {
        if (dest == END) return false;

        for (int i = 0; i < 4; i++) {
            if (idx == i) continue;
            User another = users[i];

            if (another.isEnd()) continue;
            if (dest == another.now) return true;
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

        public Cell move(int count) {
            Cell next;
            if (now.nextBlue != null) {
                next = now.nextBlue;
            } else {
                next = now.nextRed;
            }
            count--;

            if (next == END) return next;

            while (count-- > 0) {
                next = next.nextRed;
                if (next == END) return next;
            }

            return next;
        }

        public boolean isEnd() {
            return now == END;
        }
    }
}
