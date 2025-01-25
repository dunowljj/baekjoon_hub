import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static long[] sum;

    private static final StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        initSumArr();

        int T = Integer.parseInt(br.readLine());
        List<Integer> distances = new ArrayList<>();
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            findMoveCount(end - start);
        }

        System.out.print(answer.toString().trim());
    }

    private static void initSumArr() {
        sum = new long[70_001];
        for (int i = 1; i < sum.length; i++) {
            sum[i] = i;
            sum[i] += sum[i -1];
        }
    }

    private static void findMoveCount(int dist) {
        int half = dist / 2;

        int i = findFirstOverHalf(half);

        int move = 0;
        if (sum[i] + sum[i - 1] <= dist) {
            move = i + (i - 1);

            // 남는 거리가 있다면 한번더 움직여야함
            if (sum[i] + sum[i - 1] < dist) move ++;
        }

        // sum[i]가 half의 첫 초과 시점이므로 (sum[i-2] * 2) <= dist
        else {
            move = (i - 1) * 2;

            // 남는 거리가 있다면 한번더 움직여야함
            if (sum[i - 1] * 2 < dist) move++;
        }

        answer.append(move).append(System.lineSeparator());
    }

    private static int findFirstOverHalf(int half) {
        int lo = 0;
        int hi = sum.length - 1;

        // F F T T T
        while (lo < hi) {
            int mid = (lo + hi) / 2;

            if (sum[mid] > half) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }
}
/**
 * 도착하기 직전 이동거리는 반드시 1광년 이어야 한다.
 * 그렇다면 직전-1 번째 이동은 2광년 혹은 1광년이 된다.
 *
 * x, y는 int의 최댓값까지 범위로 21억정도 될듯.
 * 배열만들면 0이 9개 -> mb 2gb
 *
 *
 * 가정. 가장 큰수까지 찍고 오는게 가장 빠르게 가는 방법일까?
 *
 * n(n-1) / 2 -> 68000 제곱정도 되면 int의 max에 도달한다.
 *
 *  ### 고민
 *  여러개 케이스가 많은데 이거 감당이 되는가? 케이스가 몇갠지 설명이 없다.
 *  -->> 누적합 배열을 만들자!!
 *
 *  ### 예시
 *
 * 8 -> 1, 2, + 3?
 * 1 + 2+ 3 + 2 + 1 -> 불가
 * 특정 누적합이 half를 넘긴다면, 이전 누적합을 더했을때 완료가 가능한지 확인해보면 된다.
 * 불가능하다면 이전 누적합으로 처리한다.
 *
 *
 * 7 -> 1, 2, + 3??
 * 1,2만 더해도 하프인 3
 * 홀수에 대해서 -> 특정 누적합이 half와 같다면, 해당 합을 *2하고 1더하면 끝. 하나 더 올라가는 순간 불가능이 된다.
 * 짝수의 경우 -> 특정 누적합이 half와 같다면 *2하면 끝
 *
 *
 * 결국 특정 누적합이 half 보다 크기 시작했을때 기점으로 검사
 * 1) 값이 half보다 클때 sum[n] + sum[n-1]이 
 * 2) sum[n-1] * 2로 이동 구하기 (sum[n-1]은 half보다 같거나 작은 상태)
 * --> 둘다 거리 초과하면 move + 1 해주기
 * 
 * 합 배열에서 half보다 처음으로 큰 위치 찾을때 이분탐색으로 시간 줄이기 쓰기
 */