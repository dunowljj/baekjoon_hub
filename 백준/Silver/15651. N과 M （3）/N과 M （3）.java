import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final String LINE_BREAK = System.lineSeparator();
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 1~N 초기화
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = i + 1;
        }

        StringBuilder sb = new StringBuilder();
        dfs(0, N, M, new int[M], sb);

        System.out.print(sb.toString().trim());
    }

    private static void dfs(int depth, int n, int m, int[] seq, StringBuilder sb) {
        if (depth == m) {
            append(seq, sb);
            return;
        }

        for (int i = 1; i <= n; i++) {
                seq[depth] = i;
                dfs(depth + 1, n, m, seq, sb);
        }
    }

    private static void append(int[] seq, StringBuilder sb) {
        for (int num : seq) {
            sb.append(num).append(SPACE);
        }
        sb.append(LINE_BREAK);
    }
}
