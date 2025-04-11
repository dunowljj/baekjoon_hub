import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final int[][] dirMapper = {{0,1,0,-1},{1,0,-1,0}}; // 오른 아래 왼 위
    public static final int EMPTY = 0;
    public static final int APPLE = 1;
    public static final int BODY = 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());

        int[][] board = new int[N][N]; // 0=empty,1=apple,2=body

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int appleY = Integer.parseInt(st.nextToken()) - 1;
            int appleX = Integer.parseInt(st.nextToken()) - 1;
            board[appleY][appleX] = APPLE;
        }

        int L = Integer.parseInt(br.readLine());
        Map<Integer, String> dirChanges = new HashMap<>();
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            int second = Integer.parseInt(st.nextToken());
            String dir = st.nextToken(); // D/L

            dirChanges.put(second, dir);
        }

        Queue<Point> trace = new LinkedList<>();

        board[0][0] = BODY;
        trace.offer(new Point(0, 0));

        Point head = new Point(0, 0); // 머리 위치
        int second = 0;
        int dir = 0;
        while (true) {

            // 이전 시간에 방향전환이 있었다면 수행 -> 전환은 X초 끝난 후에 수행됨.
            if (dirChanges.containsKey(second)) {
                if (dirChanges.get(second).equals("L")) {
                    dir = ((dir + 4) - 1) % 4;
                } else {
                    dir = ((dir + 4) + 1) % 4;
                }
            }

            // 이동
            head.move(dir);
            second++;

            // 벽에 부딪쳤다면 종료
            if (head.y < 0 || head.y >= N || head.x < 0 || head.x >= N) break;


            // 자신의 몸통에 부딪힌다면 종료
            if (board[head.y][head.x] == BODY) break;

            // 이동한 칸에 사과가 없다면, 몸길이를 1 줄인다.
            if (board[head.y][head.x] != APPLE) {
                Point tail = trace.poll();
                board[tail.y][tail.x] = EMPTY;
            }

            // 머리가 위치한 곳을 body로 표시, 큐에 넣어서 추적관리
            board[head.y][head.x] = BODY;
            trace.add(new Point(head.y, head.x));
        }

        System.out.print(second);
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public void move(int dir) {
            y += dirMapper[0][dir];
            x += dirMapper[1][dir];
        }
    }
}
/**
 * 뱀은 처음에 오른쪽으로 향한다.
 * 방향 전환 정보는 X증가 순으로 주어진다.
 *
 * 머리가 늘어나는게 먼저이기때문에, 해당부분이 몸의 맨 끝인 꼬리부분이어도 충돌로 설정해야할듯.
 */