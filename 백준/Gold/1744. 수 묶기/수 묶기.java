import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] nums = new int[N];

        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(br.readLine());
        }

        if (nums.length == 1) {
            System.out.print(nums[0]);
            return;
        }

        Arrays.sort(nums);

        int result = 0;
        for (int i = 0; i < N - 1; i += 2) {
            if (nums[i] <= 0 && nums[i + 1] <= 0) {
                result += nums[i] * nums[i + 1];
            } else {
                // 마지막 음수 하나 혹은 0이 혼자 있는 경우
                if (nums[i] <= 0) result += nums[i];
                break;
            }
        }

        for (int i = N - 1; i >= 0; i -= 2) {
            if (i == 0) {
                result += nums[i];

            } else if (nums[i] > 0 && nums[i - 1] > 0) {
                if (nums[i] == 1 || nums[i - 1] == 1) {
                    result += nums[i] + nums[i - 1];
                } else {
                    result += nums[i] * nums[i - 1];
                }
            } else {
                // 마지막 양수 하나 혼자있는 경우
                if (nums[i] > 0) result += nums[i];
                break;
            }
        }

        System.out.print(result);
    }
}
/**
 * (음수 * 음수)로 가장 작은 음수들을 모두 묶는다. 
 * (음수 * 0)이면 묶는다.
 * (양수 * 1)이면 묶지않고 더한다.
 * (양수 * 양수)로 가장 큰 양수끼리 모두 묶는다.
 * 0
 *
 * -3 -2 -1 1 2 3
 * -3 -2  1 1 2 3
 * -1 1 2 3
 * 1 1 2 3 3
 * -1 1 0
 *
 * -5 -4 -3
 * 5 4 3
 */