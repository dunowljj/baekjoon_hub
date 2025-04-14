import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());


        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                int val = Integer.parseInt(st.nextToken());
                map[i][j] = val;
            }
        }

        Dice dice = new Dice(0, 0, 0, 0, 0, 0);
        dice.bottom = map[x][y];
        map[x][y] = 0;

        final int[][] mapper = {{0,0,0,-1,1}, {0,1,-1,0,0}}; // 0원위치,1동,2서,3북,4남
        StringBuilder answer = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            int dir = Integer.parseInt(st.nextToken());

            int nx = x + mapper[0][dir];
            int ny = y + mapper[1][dir];

            if (nx < 0 || nx >= N || ny < 0 || ny >= M) continue;
            dice = dice.move(dir);
            answer.append(dice.top).append("\n");

            // 면 복사
            if (map[nx][ny] == 0) {
                map[nx][ny] = dice.bottom;
            } else {
                dice.bottom = map[nx][ny];
                map[nx][ny] = 0;
            }

            x = nx;
            y = ny;
        }

        System.out.print(answer);
    }

    // 예시의 전개도 기준 1이 윗면, 6이 아랫면.
    // 주사위를 올려놓고, 1이 있는 면 방향으로 바라볼때 기준 동서남북
    static class Dice {
        int top;
        int bottom;
        int north;
        int south;
        int west;
        int east;

        public Dice(int top, int bottom, int north, int south, int west, int east) {
            this.top = top;
            this.bottom = bottom;
            this.north = north;
            this.south = south;
            this.west = west;
            this.east = east;
        }

        // Dice(top, bottom, north, south, west, east);
        public Dice move(int dir) {
            // 동쪽으로 굴리기
            if (dir == 1) {
                return new DiceBuilder()
                        .top(west)
                        .bottom(east)
                        .north(north)
                        .south(south)
                        .west(bottom)
                        .east(top)
                        .build();
            }

            // 서쪽으로
            if (dir == 2) {
                return new DiceBuilder()
                        .top(east)
                        .bottom(west)
                        .north(north)
                        .south(south)
                        .west(top)
                        .east(bottom)
                        .build();
            }

            // 북쪽으로
            if (dir == 3) {
                return new DiceBuilder()
                        .top(south)
                        .bottom(north)
                        .north(top)
                        .south(bottom)
                        .west(west)
                        .east(east)
                        .build();
            }

            // 남쪽으로
            // Dice(top, bottom, north, south, west, east);
            if (dir == 4) {
                return new DiceBuilder()
                        .top(north)
                        .bottom(south)
                        .north(bottom)
                        .south(top)
                        .west(west)
                        .east(east)
                        .build();
            }

            return null;
        }
    }

    static class DiceBuilder {
        int top;
        int bottom;
        int north;
        int south;
        int west;
        int east;

        public DiceBuilder top(int top) {
            this.top = top;
            return this;
        }

        public DiceBuilder bottom(int bottom) {
            this.bottom = bottom;
            return this;
        }

        public DiceBuilder north(int north) {
            this.north = north;
            return this;
        }

        public DiceBuilder south(int south) {
            this.south = south;
            return this;
        }

        public DiceBuilder west(int west) {
            this.west = west;
            return this;
        }

        public DiceBuilder east(int east) {
            this.east = east;
            return this;
        }

        public Dice build() {
            return new Dice(top, bottom, north, south, west, east);
        }
    }
}
