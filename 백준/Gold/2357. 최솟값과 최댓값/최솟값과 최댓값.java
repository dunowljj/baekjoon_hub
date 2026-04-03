import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(br.readLine());
        }

        SegmentTree tree = new SegmentTree(nums);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int[] result = tree.query(a - 1, b - 1);
            sb.append(result[0]).append(SPACE).append(result[1]).append(System.lineSeparator());
        }

        System.out.print(sb);
    }

    static class SegmentTree {
        int[] minTree;
        int[] maxTree;
        int n;

        public SegmentTree(int[] nums) {
            n = nums.length;
            this.minTree = new int[n * 4];
            this.maxTree = new int[n * 4];

            build(1, 0, n - 1, nums);
        }

        private void build(int node, int start, int end, int[] nums) {
            if (start == end) {
                minTree[node] = nums[start];
                maxTree[node] = nums[start];
                return;
            }

            int mid = (start + end) / 2;
            build(node * 2, start, mid, nums);
            build(node * 2 + 1, mid + 1, end, nums);

            minTree[node] = Math.min(minTree[node * 2], minTree[node * 2 + 1]);
            maxTree[node] = Math.max(maxTree[node * 2], maxTree[node * 2 + 1]);
        }

        public int[] query(int a, int b) {
            return query(1, 0, n - 1, a, b);
        }

        private int[] query(int node, int start, int end, int a, int b) {
            // 완전 포함
            if (a <= start && end <= b) {
                return new int[]{minTree[node], maxTree[node]};
            }

            // 전혀 안겹침 -> 의미없는 INF값 넣기
            if (end < a || b < start) {
                return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
            }

            // 걸친 경우
            int mid = (start + end) / 2;
            int[] left = query(node * 2, start, mid, a, b);
            int[] right = query(node * 2 + 1, mid + 1, end, a, b);

            return new int[]{Math.min(left[0], right[0]), Math.max(left[1], right[1])};
        }
    }
}
/**
 *
 */