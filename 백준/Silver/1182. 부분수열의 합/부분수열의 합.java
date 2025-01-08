import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    private static int answer = 0;
    private static int N;
    private static int S;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());


        int[] nums = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        recur(nums, 0, 0, false);

        System.out.print(answer);
    }

    private static void recur(int[] nums, int idx, long sum, boolean isAnswer) {
        if (sum == S && isAnswer) {
            answer++;
        }

        if (idx == N) {
            return;
        }

        recur(nums, idx + 1, sum + nums[idx], true);
        recur(nums, idx + 1, sum, false);
    }
}
/**
 * 이미 S와 같지만 다른 수를 추가해서 또 S가 되는 경우도 있을 수 있다.
 */
