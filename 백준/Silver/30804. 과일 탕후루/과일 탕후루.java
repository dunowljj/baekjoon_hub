import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] line = new int[N];
        int[] count = new int[10];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            line[i] = Integer.parseInt(st.nextToken());
        }

        int kind = 0;
        int left = 0;
        int right = 0;
        int maxLen = 0;

        while (right < N) {

            if (kind <= 2) {
                int fruit = line[right++];
                count[fruit]++;

                if (count[fruit] == 1) kind++;

            } else {
                int fruit = line[left++];
                count[fruit]--;

                if (count[fruit] == 0) kind--;
            }

            if (kind <= 2) maxLen = Math.max(maxLen, right - left);
        }

        System.out.print(maxLen);
    }
}
