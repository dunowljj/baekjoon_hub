import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long min = Long.parseLong(st.nextToken());
        long max = Long.parseLong(st.nextToken());

        int maxSqrt = (int)(Math.sqrt(max));
        boolean[] isPrime = findPrimeUntil(maxSqrt);
        List<Long> primePows = compressAndPow(isPrime);

        long answer = max - min + 1;
        boolean[] canDivided = new boolean[(int) (max - min + 1)];

        for (Long primePow : primePows) {

            // 적절한 첫 시작값을 찾아야함
            long start = min % primePow == 0 ?
                    min / primePow :
                    min / primePow + 1;

            long end = max / primePow;

            for (long i = start; i <= end; i++) {
                long result = i * primePow;
                int idx = (int) (result - min);

                if (!canDivided[idx]) {
                    canDivided[idx] = true;
                    answer--;
                }
            }
        }

        System.out.print(answer);
    }

    private static boolean[] findPrimeUntil(int maxSqrt) {
        boolean[] isPrime = new boolean[maxSqrt + 1];
        Arrays.fill(isPrime, true);

        isPrime[0] = false;
        isPrime[1] = false;

        for (int i = 2; i < isPrime.length; i++) {
            if (!isPrime[i]) continue;

            for (long j = (long) i * i; j < isPrime.length; j += i) {
                isPrime[(int) j] = false;
            }
        }
        return isPrime;
    }

    private static List<Long> compressAndPow(boolean[] isPrime) {
        List<Long> primes = new ArrayList<>();
        for (int i = 2; i < isPrime.length; i++) {
            if (isPrime[i]) primes.add(((long) i * i));
        }

        return primes;
    }

    private static boolean isAnswer(long num, List<Long> primePows) {
        for (Long primePow : primePows) {
            if (num < primePow) return false;
            if (num % primePow == 0) return false;
        }

        return true;
    }
}
/**
 * 100만개의 수 중
 *
 * 1조의 루트는 100만
 * min - max는 최대 100만이다.
 *
 * 1) 1~100만까지의 범위에 체를 이용해 소수를 찾는다.
 * 2) 해당 소수들의 제곱한 수를 저장
 * 3) 주어진 범위의 수들에 대해 나눠지는지 검사한다.
 *
 *
 1000000000000 1000001000000
 607940
 * O((100만) * (100만까지 소수의 개수))
 * 100만 * 78498  -> 784억... 시간초과!!
 *
 *
 * 역으로
 * - 제곱수가 일단 min~max 사이에 있다면 체크한다.
 * - 제곱수를 곱해서 Set으로 중복체크를 하며, 제거하고 여부를 체크할 수 있지 않을까?
 *
 * 8만개의 소수의 곱을 해당 최대 100만인 범위 내에서만 찾는다.
 */