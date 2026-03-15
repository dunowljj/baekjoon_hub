import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {


    public static final int ADD = 1;
    public static final int QUERY = 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        long[] arr = new long[N + 1];
        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        SegmentTree tree = new SegmentTree(arr);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if (command == ADD) {
                long added = Long.parseLong(st.nextToken());
                tree.add(b, c, added);
            }

            if (command == QUERY) {
                long sum = tree.query(b, c);
                sb.append(sum).append(System.lineSeparator());
            }
        }
        System.out.print(sb);
    }

    static class SegmentTree {
        long[] tree;
        long[] lazy;
        int size;

        private static final int START_NODE = 1;
        private static final int START_IDX = 1;


        public SegmentTree(long[] arr) {
            size = arr.length - 1;
            this.tree = new long[arr.length * 4];
            this.lazy = new long[arr.length * 4];

            build(START_NODE, START_IDX, size, arr);
        }

        private long build(int node, int start, int end, long[] arr) {
            if (start == end) {
                return tree[node] = arr[start];
            }

            int mid = (start + end) / 2;

            long left = build(node * 2, start, mid, arr);
            long right = build(node * 2 + 1, mid + 1, end, arr);

            return tree[node] = left + right;
        }

        private long query(int tStart, int tEnd) {
            return query(START_NODE, START_IDX, size, tStart, tEnd);
        }

        private long query(int node, int start, int end, int tStart, int tEnd) {
            propagate(node, start, end);

            // 겹치는 곳 없음
            if (tEnd < start || end < tStart) {
                return 0;
            }

            // 범위내 포함
            if (tStart <= start && end <= tEnd) {
                return tree[node];
            }

            // 범위에 걸쳐 있음
            int mid = (start + end) / 2;

            return query(node * 2, start, mid, tStart, tEnd)
                    + query(node * 2 + 1, mid + 1, end, tStart, tEnd);
        }

        private void add(int tStart, int tEnd, long increase) {
            add(START_NODE, START_IDX, size, tStart, tEnd, increase);
        }

        private void add(int node, int start, int end, int tStart, int tEnd, long increase) {
            propagate(node, start, end); // 이전 lazy 처리를 위해 순서가 우선되어야 함.

            // 겹치는 곳 없음
            if (tEnd < start || end < tStart) {
                return;
            }

            // 범위내 포함
            if (tStart <= start && end <= tEnd) {
                lazy[node] += increase;
                propagate(node, start, end);
                return;
            }

            // 범위에 걸쳐 있음
            int mid = (start + end) / 2;
            add(node * 2, start, mid, tStart, tEnd, increase);
            add(node * 2 + 1, mid + 1, end, tStart, tEnd, increase);

            tree[node] = tree[node * 2] + tree[node * 2 + 1];
        }

        private void propagate(int node, int start, int end) {
            if (lazy[node] == 0) return;

            // 갱신
            tree[node] += (end - start + 1) * lazy[node];

            // 1 depth 전파
            if (start != end) {
                lazy[node * 2] += lazy[node];
                lazy[node * 2 + 1] += lazy[node];
            }

            lazy[node] = 0;
        }
    }
}

/**
 * 수의 개수 N 10^6,
 * 변경 횟수 M 10^4,
 * 구간 합 쿼리 수 K 10^4
 *
 * N*K 10^10이기 때문에 구간 합을 미리 캐싱해야 함.
 * 쿼리마다 O(N)개의 합을 수정하긴 힘드므로, 세그먼트 트리 필요
 *
 * 하지만 업데이트 로직이 구간에 모두 특정값을 더하는 연산이다. 세그먼트 트리의 구간합을 범위 갱신하려면, 구간의 길이 이상의 연관요소를 모두 갱신해야한다.
 * -> 시간복잡도 초과
 *
 * 해결 : lazy propagation을 사용
 * - 특정 노드의 구간합은 즉시 갱신. 하지만 하위 노드의 값은 필요할때만 갱신하는 방법으로 시간복잡도를 줄인다.

 */