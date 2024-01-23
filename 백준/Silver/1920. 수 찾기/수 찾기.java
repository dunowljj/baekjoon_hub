import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int N = Integer.parseInt(br.readLine());

        int[] nums = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(nums);

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            int searchingNum = Integer.parseInt(st.nextToken());

            boolean isExist = binarySearch(N, nums, searchingNum);
            answer.append(isExist ? 1 : 0).append(System.lineSeparator());
        }

        System.out.println(answer.toString().trim());
    }

    private static boolean binarySearch(int n, int[] nums, int searchingNum) {
        int lo = 0;
        int hi = n;

        // F F F F T T(n)
        // F F T T T  T(n)
        while (lo < hi) {
            int mid = (lo + hi) / 2;

            if (nums[mid] >= searchingNum) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo != n && searchingNum == nums[lo];
    }
}
