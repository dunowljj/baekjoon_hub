import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        List<Integer> answers = new ArrayList<>();

        int N = Integer.parseInt(br.readLine());

        List<Integer> nums = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            nums.add(num);
        }

        Collections.sort(nums);

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            int num = Integer.parseInt(st.nextToken());
            int result = hasNum(num, nums) ? 1 : 0;
            answers.add(result);
        }

        StringBuilder sb = new StringBuilder();
        for (Integer answer : answers) {
            sb.append(answer).append(" ");
        }
        System.out.print(sb.toString().trim());
    }

    /**
     * binarySearch
     * TTTTFFF
     */
    private static boolean hasNum(int num, List<Integer> nums) {
        int low = 0;
        int high = nums.size();
        while (low + 1 < high) {
            int mid = (low + high) / 2;

            if (nums.get(mid) <= num) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return nums.get(low) == num;
    }
}
/**
 * 해시 대신 이분탐색 써보기
 */