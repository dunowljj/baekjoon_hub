import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            
            sb.append(comb(N, M)).append(System.lineSeparator());
        }

        System.out.print(sb);
    }

    private static BigInteger comb(int n, int m) {
        if (m - n < n) {
            n = m - n;
        }

        BigInteger num = BigInteger.valueOf(1L);
        BigInteger divider = BigInteger.valueOf(1L);
        for (int i = 1; i <= n; i++) {
            num = num.multiply(BigInteger.valueOf(m--));
            divider = divider.multiply(BigInteger.valueOf(i));
        }

        return num.divide(divider);
    }
}
