import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static int N;
    static boolean[] isPrime;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        isPrime = new boolean[N + 1];
        Arrays.fill(isPrime, true);

        List<Integer> primes = new ArrayList<>();

        for (int i = 2; (i * i) <= N; i++) {
            if (!isPrime[i]) continue;

            for (int j = 2 * i; j <= N; j+=i) {
                isPrime[j] = false;
            }
        }

        for (int i = 2; i <= N; i++) {
            if (isPrime[i]) primes.add(i);
        }

        int size = primes.size();

        if (size == 1) {
            if (primes.get(0) == N) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }
            return;
        }

        /*for (Integer prime : primes) {
            System.out.print(prime + " ");
        }
        System.out.println();*/

        int answer = 0;
        int left = size - 1;
        int right = size - 1;
        long sum = 0;

        while (0 <= left) {

            if (sum >= N) {
                sum -= primes.get(right);
                right--;
            } else if (sum < N) {
                sum += primes.get(left);
                left--;
            }

            if (sum == N) {
                answer++;
            }
        }

        System.out.print(answer);
    }
}
/**
 * N까지 소수를 체로 구하고, List에 넣는다.
 * 2개의 포인터를 사용해, 연속된 수의 합을 모두 체크한다.
 */