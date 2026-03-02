import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        MinSegmentTree root = new MinSegmentTree();
        build(arr, root, 0, arr.length - 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            sb.append(query(root, 0, arr.length - 1, a, b))
                    .append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }

    private static int build(int[] arr, MinSegmentTree node, int start, int end) {
        if (start == end) {
            return node.min = arr[start];
        }

        int mid = (start + end) / 2;
        return node.min = Math.min(
                build(arr, node.left = new MinSegmentTree(), start, mid),
                build(arr, node.right = new MinSegmentTree(), mid + 1, end)
        );
    }

    private static int query(MinSegmentTree node, int start, int end, int l, int r) {
        if (r < start || end < l) return Integer.MAX_VALUE;
        if (l <= start && end <= r) return node.min;

        int mid = (start + end) / 2;
        return Math.min(
                query(node.left, start, mid, l, r),
                query(node.right, mid + 1, end, l, r)
        );
    }

    public static class MinSegmentTree {
        int min;
        MinSegmentTree left;
        MinSegmentTree right;

        public MinSegmentTree() {}
    }
}
