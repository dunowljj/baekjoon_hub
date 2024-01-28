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

        int lenMax = 0;
        int sumOfPlayTime = 0;
        int[] playTimes = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int playTime = Integer.parseInt(st.nextToken());

            sumOfPlayTime += playTime;
            lenMax = Math.max(lenMax, playTime);

            playTimes[i] = playTime;
        }

        int lo = lenMax - 1;
        int hi = sumOfPlayTime + 1;

        // 크키가 작을수록 여러개로 분할 가능
        // 블루레이 크기의 증가 -> 블루레이 개수 감소
        // 처음에 m개 초과로 분할 가능하다가 어느시점부터 불가능해진다.
        // 처음 불가능해진 시점이 m과 같다면 해당 부분이 정답이다.
        // T T T T F F F
        while (lo + 1 < hi) {
            int mid = (lo + hi) / 2;

            if (canBeSplittedMoreThan(M, lenMax, mid, playTimes)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        // 불가능한 경우는 주어지지 않는가?

        System.out.print(hi);
    }

    private static boolean canBeSplittedMoreThan(int m, int lenMax, int lenLimit, int[] playTimes) {
//        if (lenLimit < lenMax) return false;

        int splitCount = 1; //처리가 안되는 마지막 부분의 분할 미리 추가
        int len = 0;
        for (int playTime : playTimes) {
            if (lenLimit >= len + playTime) {
                len += playTime;
            } else {
                len = playTime;
                splitCount++;
            }
        }

        return splitCount > m;
    }
}
/**
 * 강의 수 1~10만
 * 강의 길이 1~1만 -> 총합 10억이하
 */