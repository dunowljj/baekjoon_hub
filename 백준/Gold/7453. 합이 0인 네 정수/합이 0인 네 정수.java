import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        int[] A = new int[n];
        int[] B = new int[n];
        int[] C = new int[n];
        int[] D = new int[n];

        int[] AB = new int[n*n];
        int[] CD = new int[n*n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            C[i] = Integer.parseInt(st.nextToken());
            D[i] = Integer.parseInt(st.nextToken());
        }

        int idx = 0;
        for (int i = 0; i < n; i++) {
            int a = A[i];
            int c = C[i];

            for (int j = 0; j < n; j++) {
                AB[idx] = a + B[j];
                CD[idx] = c + D[j];
                idx++;
            }
        }

        Arrays.sort(CD);

        long count = countPair(AB, CD);
        System.out.print(count);
    }

    private static long countPair(int[] AB, int[] CD) {
        long count = 0;
        for (int sum : AB) {

            int upperBound = findUpperBound(-sum, CD);
            int lowerBound = findLowerBound(-sum, CD);

            count += upperBound - lowerBound;
        }

        return count;
    }

    private static int findUpperBound(int val, int[] sums) {
        int lo = 0;
        int hi = sums.length;

        // F F F T T T -> 첫 T는 val보다 큰 첫수
        // F F F -> size 반환
        // T T T -> 첫T 반환
        while (lo < hi) {
            int mid = (lo + hi) / 2;

            if (val < sums[mid]) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }


    private static int findLowerBound(int val, int[] sums) {
        int lo = 0;
        int hi = sums.length;

        // F F F T T T -> 첫 T는 val과 같은 처음 수
        // F F F -> size 반환
        // T T T -> 첫T 반환
        while (lo < hi) {
            int mid = (lo + hi) / 2;

            if (val <= sums[mid]) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }
}
/**
 * 결과가 중복되는 쌍은 어떻게 되는건가?
 * 인덱스 기준으로 abcd의 쌍의 개수를 찾는것이므로, 중복도 고려해야한다.
 *
 * 1600만
 *
 * HashMap 사용 시, 해시충돌때문에 성능이 나오지 않는 것으로 추정
 */