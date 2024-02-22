import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int minLen = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        int sum = 0;
        while (right < N && left <= right) {

            while (right < N && sum < S) {
                sum += arr[right];
                right++;
            }
            // 합에 더하고 인덱스를 증가시키므로, right는 아직 더해지지 않은 다음 인덱스를 가르키고 있는 상태

            while (left < N && sum >= S) {
                minLen = Math.min(minLen, right - left); // right 인덱스 상태를 고려하여 +1을 하지 않는다.
                sum -= arr[left];
                left++;
            }
        }

        // 불가능
        if (minLen == Integer.MAX_VALUE) {
            System.out.print(0);
        } else {
            System.out.print(minLen);
        }
    }
}

/**
 * S가 0이면 길이도 0인가? 1이겠지?
 * 불가능하면 0출력
 */