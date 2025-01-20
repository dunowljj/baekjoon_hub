import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int answer = -1;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] nums = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        int[] count = new int[100_001];

        int answer = 0;

        int l = 0;
        for (int r = 0; r < nums.length; r++) {
            count[nums[r]]++;

            while (count[nums[r]] > K) {
                count[nums[l]]--;
                l++; // 빼고 지나감, r+1까지 도달 가능
            }

            answer = Math.max(answer, r - l + 1);
        }

        System.out.print(answer);
    }
}
/**
 * 최장연속부분수열
 */
