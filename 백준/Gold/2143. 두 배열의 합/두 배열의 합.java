import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());

        int[] A = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        int m = Integer.parseInt(br.readLine());
        int[] B = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }

        Map<Integer, Integer> aSums = new HashMap<>();
        Map<Integer, Integer> bSums = new HashMap<>();

        createSums(n, A, aSums);
        createSums(m, B, bSums);

        long count = 0;
        for (int aSum : aSums.keySet()) {
            if (bSums.containsKey(T - aSum)) {
                count += (long) aSums.get(aSum) * bSums.get(T - aSum);
            }
        }

        System.out.print(count);
    }

    private static void createSums(int len, int[] arr, Map<Integer, Integer> sums) {
        for (int i = 0; i < len; i++) {
            int sum = 0;
            for (int j = i; j < len; j++) {
                sum += arr[j];
                sums.put(sum, sums.getOrDefault(sum, 0) + 1);
            }
        }
    }
}
/**
 * 1000길이의 배열 모든 합의 개수 -> 51만개
 */