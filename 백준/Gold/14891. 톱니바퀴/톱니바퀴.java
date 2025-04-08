import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Wheels wheels = new Wheels();
        for (int i = 1; i <= 4; i++) {
            int bit = Integer.parseInt(br.readLine(), 2);
            wheels.add(i, bit);
        }

        int K = Integer.parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int wheelNo = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());

            wheels.rotate(wheelNo, direction);
        }

        System.out.print(wheels.getScore());
    }

    static class Wheels {
        private static int START_NO = 1;
        private static int END_NO = 4;

        Wheel[] wheels = new Wheel[5];
        int[] directions = new int[5];

        public void add(int idx, int bit) {
            wheels[idx] = new Wheel(bit);
        }

        public void rotate(int idx, int direction) {
            Wheel now = wheels[idx];
            findDirections(idx, direction, now);
            rotateWheels();
        }

        private void findDirections(int idx, int direction, Wheel wheel) {
            directions[idx] = direction;

            int nowDirection = direction;
            Wheel now = wheel;

            int nextIdx = idx + 1;
            // 우측에 있는 톱니바퀴 방향 확인
            while (nextIdx <= END_NO) {

                // 극이 같다면 회전x
                if (now.rightSaw() == wheels[nextIdx].leftSaw()) {
                    directions[nextIdx] = 0;
                // 다르다면 반대 방향
                } else {
                    directions[nextIdx] = nowDirection * -1;
                }

                nowDirection = directions[nextIdx];
                now = wheels[nextIdx];

                nextIdx++;
            }

            // 좌측에 있는 톱니바퀴 방향 확인
            nowDirection = direction;
            now = wheel;
            nextIdx = idx - 1;
            while (START_NO <= nextIdx) {
                if (wheels[nextIdx].rightSaw() == now.leftSaw()) {
                    directions[nextIdx] = 0;
                } else {
                    directions[nextIdx] = nowDirection * -1;
                }

                nowDirection = directions[nextIdx];
                now = wheels[nextIdx];

                nextIdx--;
            }
        }

        private void rotateWheels() {
            for (int i = START_NO; i <= END_NO; i++) {
//                System.out.println("i = " + i);
                wheels[i].rotate(directions[i]);
            }
        }

        public int getScore() {
            int score = 0;
            if (wheels[1].topSaw() == 1) score += 1;
            if (wheels[2].topSaw() == 1) score += 2;
            if (wheels[3].topSaw() == 1) score += 4;
            if (wheels[4].topSaw() == 1) score += 8;

            return score;
        }
    }
    static class Wheel {
        int bit;

        public Wheel(int bit) {
            this.bit = bit;
        }

        public void rotate(int direction) {

            // 시계방향
            if (direction == 1) {
                int temp = bit & 1;
                bit = (bit >> 1);
                bit += (temp > 0) ? (1 << 7) : 0;
            }

            // 반시계 방향
            if (direction == -1) {
                int temp = bit & (1 << 7);
                if (temp != 0) bit ^= temp;
                bit = (bit << 1);
                bit += temp > 0 ? 1 : 0;
            }
        }

        // 00[0]00000
        public int rightSaw() {
            if ((bit & (1 << 5)) != 0) {
                return 1;
            }

            return 0;
        }

        // 000000[0]0
        public int leftSaw() {
            if ((bit & (1 << 1)) != 0) {
                return 1;
            }

            return 0;
        }

        // [0]0000000
        public int topSaw() {
            if ((bit & (1 << 7)) != 0) return 1;
            else return 0;
        }
    }
}



/**
 * 비트로 구현
 * 혹은 deque 2개로 구현
 *
 *
 */