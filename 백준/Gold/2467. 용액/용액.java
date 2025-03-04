import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static int[] answer =  new int[2];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 2~10만

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] liquid = new int[N];
        for (int i = 0; i < N; i++) {
            liquid[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(liquid);

        // 모두 음수 혹은 0
        if (liquid[N - 1] <= 0) {
            answer[0] = liquid[N - 1];
            answer[1] = liquid[N - 2];
            Arrays.sort(answer);
            System.out.print(answer[0]+" "+answer[1]);
            return;
        }

        // 모두 양수
        if (liquid[0] >= 0) {
            answer[0] = liquid[0];
            answer[1] = liquid[1];
            Arrays.sort(answer);
            System.out.print(answer[0]+" "+answer[1]);
            return;
        }

        // 섞인 경우
        int left = 0;
        int right = N - 1;
        long nearZero = Long.MAX_VALUE;  // 합의 절댓값이 가장 작은 경우를 찾기
        while (left < right) {

            long result = (long) liquid[left] + (long) liquid[right];
            long abs = Math.abs(result);

//            System.out.println("result = " + result);
//            System.out.println("nearZero = " + nearZero);
//            System.out.println();
            if (nearZero > abs) {
                nearZero = abs;
                answer[0] = liquid[left];
                answer[1] = liquid[right];

//                System.out.println("--abs = " + abs);
//                System.out.println("--answer[0] = " + answer[0]);
//                System.out.println("--answer[1] = " + answer[1]);
            }

            // 결과가 음수인 경우, left 이동.
            // ex) -10 -9 1 2 3 15 -> 1,3에 위치하게 되고, 1,2가 최솟값이 된다.
            if (result < 0) {
                left++;
            }

            else if (result > 0) {
                right--;
            }

            else {
                break;
            }
        }

        Arrays.sort(answer);
        System.out.print(answer[0]+" "+answer[1]);
    }
}

/**
 * 1 ~ 10억
 * -1 ~ -10억
 *
 *
 * N -> 2~10만
 */