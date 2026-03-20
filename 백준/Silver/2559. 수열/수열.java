import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] nums = new int[N];
        int window = 0;
        for (int i = 0; i < N; i++) {
             nums[i] = Integer.parseInt(st.nextToken());
             if (i < K) window += nums[i];
        }

        int max = window;

        int l = 0;
        int r = K;

        while (r < N) {
            window -= nums[l++];
            window += nums[r++];

            max = Math.max(max, window);
        }

        System.out.print(max);
    }
}
