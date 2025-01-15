import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String N = br.readLine();
        int M = Integer.parseInt(br.readLine());

        List<Integer> broken = new ArrayList<>();

        if (M != 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                broken.add(Integer.parseInt(st.nextToken()));
            }
        }

        int destChannel = Integer.parseInt(N);
        int minCount = Math.abs(100 - destChannel);

        for (int i = 0; i <= 999_999; i++) {

            if (hasBrokenButton(i, broken)) {
                continue;
            }

            int len = String.valueOf(i).length();
            
            int count = Math.abs(destChannel - i);
            minCount = Math.min(minCount, count + len);
        }


        System.out.print(minCount);
    }

    private static boolean hasBrokenButton(int i, List<Integer> broken) {

        if (i == 0) {
            return broken.contains(0);
        }

        while (i > 0) {
            if (broken.contains(i % 10)) {
                return true;
            }

            i /= 10;
        }

        return false;
    }
}


/**
 * 채널 50만 이하
 *
 *
 * ### 깡구현
 * 1. 첫자리 가장 가까운 수 고르기
 * 2. 첫자리가 더 큰가 작은가에 대해 다음자리 결정. 첫자리가 같다면 둘째자리도 가까운 수 고르기
 * 3. 반복 후 N과 차 구하기
 * -> 가진 숫자에 따라 분기가 많아질듯
 *
 * ### brute force
 * - 채널 값이 최대 50만이므로, 모든 경우의 수를 구해도 10^6이다.
 * - 모든 케이스 중 가장 가까운 수를 구하고, 일치하는 수의 개수 + 두 수의 차
 *
 * 현재 채널은 100번임 -> 100에서 직접 이동하는 경우추가
 * 100에서 숫자 추가도 가능해야하나?
 *
 *
 * m=0이면 입력이 주어지지 않음
 *
 * 더 낮은 자릿수에서 이동하는게 빠를 수 있다.
 * N = 1000의 경우
 * 주어진 숫자에 따라 999에서 +1 하는게 더 빠르다.
 */