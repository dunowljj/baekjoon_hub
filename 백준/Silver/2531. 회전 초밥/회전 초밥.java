import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int[] belt = new int[N];

        for (int i = 0; i < N; i++) {
            belt[i] = Integer.parseInt(br.readLine());
        }

        int count = 0;

        int[] eat = new int[d + 1];
        // 0~k-1 범위 window를 카운트
        for (int i = 0; i < k; i++) {
            eat[belt[i]]++;
            if (eat[belt[i]] == 1) count++;
        }

        int maxCount = count;

        // window 유지, 한칸씩 이동
        for (int i = 0; i < N; i++) {
            int removed = belt[i];
            int added = belt[(i + k) % N];

            eat[removed]--;
            if (eat[removed] == 0) count--;

            eat[added]++;
            if (eat[added] == 1) count++;

            maxCount = Math.max(maxCount, count + ((eat[c] == 0) ? 1 : 0));
        }

        System.out.print(maxCount);
    }
}
