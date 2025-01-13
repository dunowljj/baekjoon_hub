import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] water = new int[N]; // e <= |10억|
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            water[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(water);

        long answerAbsSum = Long.MAX_VALUE;
        int[] answer = new int[3];
        for (int i1 = 0; i1 < N - 2; i1++) {
            int i2 = i1 + 1;
            int i3 = N - 1;

            while (i2 < i3) {
                long sum = (long) water[i1] + (long) water[i2] + water[i3];

                if (answerAbsSum > Math.abs(sum)) {
                    answerAbsSum = Math.abs(sum);
                    answer[0] = water[i1];
                    answer[1] = water[i2];
                    answer[2] = water[i3];
                }

                if (sum == 0) {
                    Arrays.sort(answer);
                    System.out.print(answer[0] + " " + answer[1] + " " + answer[2]);
                    System.exit(0);

                } else if (sum < 0) {
                    i2++;
                } else if (sum > 0) {
                    i3--;
                }
            }
        }

        Arrays.sort(answer);
        System.out.print(answer[0] + " " + answer[1] + " " + answer[2]);

    }
}
/**
 * ### 처음 구현
 * - 2개를 합친 조합을 모두 구하고 정렬하여 이분탐색에 활용
 * 문제
 * - 2개를 합치는 모든 조합 구하기 -> 5000*4999/2 -> 약 1250만
 * - 2진 탐색을 위해 해당 조합을 정렬 -> 1250만 * 약 27 -> 3억에 가까운 연산 -> 시간초과
 *
 * ### 개선
 * - 주어진 용액들의 값을 정렬하고, 포인터를 추가하여 조합을 찾는다.
 */