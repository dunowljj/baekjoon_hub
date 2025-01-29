import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int N, K, beltLen, zeroCount;
    

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        beltLen = N * 2;

        st = new StringTokenizer(br.readLine());
        Space[] belt = new Space[beltLen]; // 0~(2n-1) 올리는 위치=0, 내리는 위치= n-1

        zeroCount = 0;
        for (int i = 0; i < beltLen; i++) {
            int A = Integer.parseInt(st.nextToken());
            belt[i] = new Space(A, false);

            if (A == 0) zeroCount++;
        }
    
        int repeat = 0;
        while (true) {
            if (zeroCount >= K) {
                break;
            }
//            printBelt(belt);

            repeat++;
            rotate(belt);
            moveRobot(belt);
            placeRobotAtStart(belt);
        }

        System.out.print(repeat);
    }

    private static void printBelt(Space[] belt) {
        for (int i = 0; i < belt.length; i++) {
            System.out.print(belt[i].durability+" ");
        }
        System.out.println();
    }

    // 직접 회전하지말고 상수값으로 이동 수를 그냥 더하는건 어떨까?

    private static void rotate(Space[] belt) {
        Space last = belt[beltLen - 1];
        System.arraycopy(belt, 0, belt, 1, beltLen - 1);
        belt[0] = last;

        belt[N - 1].hasRobot = false;
    }

    // 내리는 위치 바로 이전부터 탐색
    private static void moveRobot(Space[] belt) {
        for (int i = N - 2; i > 0; i--) {
            if (belt[i].canMoveTo(belt[i + 1])) {
                belt[i].moveRobot(belt[i + 1]);
            }
        }

        belt[N - 1].hasRobot = false;
    }

    private static void placeRobotAtStart(Space[] belt) {
        if (belt[0].durability != 0) {
            belt[0].hasRobot = true;
            belt[0].durability--;
            if (belt[0].durability == 0) zeroCount++;
        }
    }

    static class Space {
        int durability;
        boolean hasRobot;

        public Space(int durability, boolean hasRobot) {
            this.durability = durability;
            this.hasRobot = hasRobot;
        }

        public boolean canMoveTo(Space dest) {
            return this.hasRobot &&
                    !dest.hasRobot && dest.durability != 0;
        }

        public void moveRobot(Space dest) {
            this.hasRobot = false;
            dest.hasRobot = true;
            dest.durability--;
            if (dest.durability == 0) zeroCount++;
        }
    }
}
/**
 * 올리거나 로봇 이동 -> 내구도 1 감소
 * 로봇이 스스로 이동한단 직후에 얘기한거보니 스스로 이동을 말하는 것 같음
 *
 * 1. 회전
 * 2. 처음 올라간 놈부터 빈칸이 있으면 한칸씩 이동 (가려는 칸에 내구도가 1이상 있어야함)
 * 3. 올리는 칸도 내구도 0이 아니면 로봇을 올린다.
 * 4. 0인 칸의 개수가 K이상이라면 종료한다. 아니면 1번감
 *
 * 총 전체 단계가 몇번째 진행중인지 찾는듯.
 * 
 * N <= 100
 * K <= 2N
 * A <= 1000
 * 
 * 로봇을 한칸에 여러개 올릴 수 있는지? 아마 앞에 로봇있으면 못가니까 안되는 듯
 */