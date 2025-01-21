import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean[] isPrime = new boolean[1_000_001];
        findPrime(isPrime);

        List<Integer> primeList = new ArrayList<>();
        Set<Integer> primeSet = new HashSet<>();
        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                primeSet.add(i);
                primeList.add(i);
            }
        }

        int inputNum = 0;
        StringBuilder sb = new StringBuilder();
        while ((inputNum = Integer.parseInt(br.readLine())) != 0) {

            boolean find = false;
            for (Integer a : primeList) {
                int b = inputNum - a;

                if (primeSet.contains(b)) {
                    sb.append(inputNum).append(" = ").append(a).append(" + ").append(b)
                            .append(System.lineSeparator());
                    find = true;
                    break;
                }
            }
            
            if (!find) sb.append("Goldbach's conjecture is wrong.")
                    .append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }

    private static void findPrime(boolean[] isPrime) {
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        isPrime[2] = true;

        for (int i = 2; i <= 1_000; i++) {
            if (!isPrime[i]) continue;

            for (int j = 2; i * j <= 1_000_000; j++) {
                isPrime[i * j] = false;
            }
        }
    }
}

/**
 * n는 6~100만
 * 케이스 10만개 이하
 *
 * 1. 체 사용해서 100만까지 소수 구하기
 * 2.
 *
 *
 * 10만개에 대해 100만짜리를 계속 탐색하면 초과아닌가?
 * 1000만 내에 소수가 몇개있지?
 *
 *  b-a가 가장 큰 것 -> 두 개의 차가 가장 커야한다.
 * Goldbach's conjecture is wrong.
 */