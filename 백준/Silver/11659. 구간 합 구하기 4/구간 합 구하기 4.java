import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final String LINE_BREAK = "\n";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] sumArr = new int[N + 1];
        st = new StringTokenizer(br.readLine());

        // 0번째는 사용하지 않는다.
        for (int i = 1; i < N + 1; i++) {
            sumArr[i] = sumArr[i - 1] + Integer.parseInt(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            if (start == 0) {
                sb.append(sumArr[end]).append(LINE_BREAK);
            } else {
                sb.append(sumArr[end] - sumArr[start - 1]).append(LINE_BREAK);
            }
        }

        System.out.print(sb.toString());
    }
}
