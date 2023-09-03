import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final int LENGTH_OF_SEQUENCE = 5;
    public static final int MAX_VALUE = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            nums.add(Integer.parseInt(br.readLine()));
        }

        Collections.sort(nums);

        int max = 0, count = 0, r = 0, l = 0;
        int len = nums.size();
        while (r < len) {
            if (nums.get(r) - nums.get(l) < LENGTH_OF_SEQUENCE) {
                count ++;
                r++;
            } else {
                count --;
                l++;
            }

            max = Math.max(max, count);
        }

        System.out.print(LENGTH_OF_SEQUENCE - max);
    }
}
/**
 * 중복 없음
 */
