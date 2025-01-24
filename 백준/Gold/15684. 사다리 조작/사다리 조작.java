import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    
    private static int N, M, H;
    private static boolean isFinish;
    private static boolean[][] horizon;
    private static int answer = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        horizon = new boolean[H + 1][N];
        // 사용 범위 [1~30][1~9]
        // 최대가 N-1, 세로선의 개수가 아닌 가로선을 놓을 수 있는 세로 공간의 개수임

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // b와 b+1을 a높이에서 연결
            horizon[a][b] = true;
        }

        for (int limitDepth = 0; limitDepth <= 3; limitDepth++) {
            findCombs(limitDepth, 0, 1, 1);
        }


        if (answer == Integer.MAX_VALUE) System.out.print(-1);
        else System.out.print(answer);
    }

    private static void findCombs(int limit, int depth, int h,  int r) {
        if (isFinish) return;

        if (depth == limit) {
            if (checkLadder()) {
                answer = Math.min(answer, depth);
                isFinish = true;
                // dfs라 바로 return 하면 더 작은 경우를 놓칠 수 있다.
            }
            return;
        }

        if (r >= N) {
            findCombs(limit, depth, h + 1, 1);
            return;
        }

        if (h >= H + 1) {
            return;
        }

        // 왼,현재,오른쪽 하나라도 가로가 있으면 가로 추가가 불가능한 위치임
        if (canInsertHorizon(h, r)) {
            horizon[h][r] = true;
            findCombs(limit, depth + 1, h, r + 1);
            horizon[h][r] = false;
        }

        findCombs(limit, depth, h, r + 1);
    }

    private static boolean canInsertHorizon(int h, int r) {
        return !horizon[h][r] &&
                (r - 1 <= 0 || !horizon[h][r - 1]) &&
                (N <= r + 1 || !horizon[h][r + 1]);
    }


    /**
     * 각 위치에서 출발해서 모두 i -> i 인지 확인
     */
    private static boolean checkLadder() {
        for (int start = 1; start <= N; start++) {
            int end = climbDown(1, start);
            
            if (start != end) return false;
        }
        return true;
    }

    private static int climbDown(int depth, int start) {
        // 탐색 종료
        if (depth == H + 1) {
            return start;
        }

        // 중간 라인 - 양쪽 모두 탐색
        int left = start - 1;
        int right = start; // 오른쪽 가로선은 해당 인덱스에 붙어있음

        if (1 <= left && horizon[depth][left]) {
            return climbDown(depth + 1, start - 1);
        }

        if (right <= N - 1 && horizon[depth][right]) {
            return climbDown(depth + 1, start + 1);
        }

        // 어느쪽도 갈곳이 없으면 아래로 한카 내려감
        return climbDown(depth + 1, start);
    }

    static class Point {
        int h;
        int r;

        public Point(int h, int r) {
            this.h = h;
            this.r = r;
        }
    }
}

/**
 * 300 C 3 -> 약 4500만
 *
 *
 * 사다리 전체체크는 약 300으로 120억개 연산
 */
