import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N, C;
    static int[] weights;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        weights = new int[N];
        for (int i = 0; i < N; i++) {
            weights[i] = Integer.parseInt(st.nextToken());
        }

        List<Long> weights1 = new ArrayList<>();
        List<Long> weights2 = new ArrayList<>();

        dfs(0    , N / 2, 0, weights1);
        dfs(N / 2, N    , 0, weights2);

        Collections.sort(weights2);
        int total = 0;
        for (Long sum : weights1) {
            total += binarySearch(sum, weights2);
        }

        System.out.print(total);
    }

    private static void dfs(int depth, int maxDepth, long sum, List<Long> sumOfWeights) {
        if (sum > C) return;
        if (depth == maxDepth) {
            sumOfWeights.add(sum);
            return;
        }

        dfs(depth + 1, maxDepth, sum + weights[depth], sumOfWeights);
        dfs(depth + 1, maxDepth, sum, sumOfWeights);
    }

    private static int binarySearch(Long sum, List<Long> weights) {
        int len = weights.size();

        // 0 1 2 3 4 5
        // T T T F F F
        // T T T T T T
        // F F F F F F
        // 첫 F의 인덱스 찾기
        int lo = 0;
        int hi = len;
        while (lo < hi) {

            int mid = (lo + hi) / 2;

            if (sum + weights.get(mid) <= C) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo;
    }
}
/**
 * 무게를 배열 인덱스 값으로 두기에는 10^9로 너무 크다.
 * Set을 사용해도 개수가 너무 많다.
 *
 * 애초에 30개 1씩하면, 10억개 경우의 수가 발생한다.
 *
 * 예시를 보면,
 * - 넣지 않는 경우 1개를 센다.
 * - 무게가 같은 물건도 전부 다르게 처리하며, 순서는 무관함
 *
 * meet in the middle + 이분탐색을 활용한다.
 */