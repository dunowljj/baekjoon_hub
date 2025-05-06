import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        long before = Long.parseLong(st.nextToken());

        long count = 0;
        long[] counts = new long[N];
        for (int i = 1; i < N; i++) {
            long now = Long.parseLong(st.nextToken());

            // 이전값이 더 커서 보정이 필요함
            double beforePow = before * Math.pow(2, counts[i - 1]);
            if (beforePow > now) {
                counts[i] = counts[i - 1] + (long) Math.ceil(
                        (Math.log((double) before / now) / Math.log(2))
                );

                count += counts[i];
            }
            before = now;
        }

        System.out.print(count);
    }
}
/**
 * n[i-1] * 2^x <= n[i] * 2^y
 * x + log2(n[i-1]) <= y + log2(n[i]);
 * y >= x + log2(n[i-1]/n[i]);
 */