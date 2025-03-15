import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static long MOD;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        MOD = C;
        System.out.print(pow(A, B));
    }

    private static long pow(long a, long b) {
        if (b == 1) return a % MOD;

        long sqrt = pow(a, (b / 2));

        if (b % 2 == 1) return (sqrt * sqrt) % MOD * a % MOD;
        else return (sqrt * sqrt) % MOD;
    }
}

/**
 * 10 * 10 * 10 * 10...
 *
 *
 * 단순히 재귀로 21억번 제곱을 하다보면 메모리가 초과한다.
 * 이를 방지하기 위해 제곱을 반으로 나눠서 log로 메모리 사용을 줄인다.
 */