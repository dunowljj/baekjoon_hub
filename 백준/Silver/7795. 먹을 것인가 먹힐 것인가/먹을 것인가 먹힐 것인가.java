import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();
        
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            int[] A = new int[N];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(A);

            int count = 0;
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                int life = Integer.parseInt(st.nextToken());
                count += binarySearch(life, A, N);
            }

            answer.append(count).append(System.lineSeparator());
        }

        System.out.print(answer.toString().trim());
    }

    private static int binarySearch(int life, int[] A, int N) {
        int lo = 0;
        int hi = N - 1;

        // F F T
        // F F F n
        while (lo <= hi) {
            int mid = (lo + hi) / 2;

            // T
            if (life < A[mid]) {
                hi = mid - 1;

            // F
            } else {
                lo = mid + 1;
            }
        }

        // lo가 최대로 N이 나올 수 있다. 하지만 개수를 세는거라 그대로 써도 상관 없다.
        return N - lo;
    }
}

// 이분탐색 혹은 트리맵
