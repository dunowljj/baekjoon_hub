import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int N,M;
    private static boolean[][] shine;
    private static int[][] diagonal;
    private static int[][] shineSum;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        shine = new boolean[N + 1][M + 1];
        shineSum = new int[N + 1][M + 1];
        diagonal = new int[N + 2][M + 2];
        for (int y = 0; y < N; y++) {
            String line = br.readLine();

            for (int x = 0; x < M; x++) {
                if (line.charAt(x) == 'X') {
                    shine[y + 1][x + 1] = true;
                }
            }
        }

        // 'X' 카운팅하는 누적합 배열 생성
        for (int y = 1; y <= N; y++) {
            for (int x = 1; x <= M; x++) {
                shineSum[y][x] = shineSum[y][x - 1] + shineSum[y - 1][x] - shineSum[y - 1][x - 1];
                if (shine[y][x]) shineSum[y][x]++;
            }
        }

        // 연속 대각선 누적
        for (int y = N; y >= 1; y--) {
            for (int x = M; x >= 1; x--) {
                if (shine[y][x]) {
                    diagonal[y][x] = diagonal[y + 1][x + 1] + 1;
                }
            }
        }

//        for (int y = 1; y <= N; y++) {
//            for (int x = 1; x <= M; x++) {
//                System.out.print(diagonal[y][x]+ " ");
//            }
//            System.out.println();
//        }

        StringBuilder answer = new StringBuilder();
        int maxK = Math.min(N, M);
        int[] count = new int[maxK + 1];

        for (int y = 1; y <= N; y++) {
            for (int x = 1; x <= M; x++) {
                if (!shine[y][x]) continue;

                int k = findEnableK(y, x);
//                System.out.println("y = " + y);
//                System.out.println("x = " + x);
//                System.out.println("k = " + k);
//                System.out.println();
                count[k]++;
            }
        }

        for (int i = maxK; i >= 2; i--) {
            count[i - 1] += count[i];
        }

        for (int k = 1; k <= maxK; k++) {
            answer
                    .append(count[k])
                    .append(System.lineSeparator());
        }

        System.out.print(answer.toString().trim());
    }

    private static int findEnableK(int y, int x) {
        int lo = 0;
        int hi = diagonal[y][x] - 1;

        int answer = -1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int sum = getSum(y, x, mid);

            if (sum == mid + 1) {
                answer = mid + 1;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return answer;
    }

    private static int getSum(int y, int x, int mid) {
        int y2 = y + mid;
        int x2 = x + mid;

        return shineSum[y2][x2]
                - shineSum[y2][x - 1]
                - shineSum[y - 1][x2]
                + shineSum[y - 1][x - 1];
    }
}
/**
 * 최대 2000*2000
 * K는 최대 2000!
 *
 * K == 1일때는 X가 영역의 수
 *
 * 1) 2차원 누적합으로 빛이 들어오는 수를 구해놓는다.
 * 2) 좌하 -> 우상 방향으로 연속된 대각선 수를 미리 누적시켜놓는다.
 * - 후에 1,1지점부터 순차 탐색할때, 최대 대각선 수를 바로 알 수 있음
 * 3) 1)2)를 이용해 특정 k*k에 마을을 지을 수 있는지 O(1)으로 확인 가능.
 * 4) K를 모두 탐색하면 K*N*M이라 시간초과 발생
 *    임의의 (y,x)에서 시작되는 K*K 공간을 검토해서 마을을 지을수 있다면, (y,x)에 시작하는 더 작은 K*K공간에도 모두 지을 수 있다.
 *    (y,x)가 시작점일때, 마을이 건설 가능한 최대 대각선 길이를 이분탐색으로 찾는다.
 */
