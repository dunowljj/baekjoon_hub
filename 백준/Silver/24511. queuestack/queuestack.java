import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        boolean[] isQueue = new boolean[N];
        for (int i = 0; i < N; i++) {
            isQueue[i] = (Integer.parseInt(st.nextToken()) == 0);
        }

        st = new StringTokenizer(br.readLine());
        Queue<Integer> queue = new LinkedList<>();
        int[] B = new int[N];
        for (int i = 0; i < N; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = N - 1; i >= 0; i--) {
            if (isQueue[i]) queue.offer(B[i]);
        }

        int M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            queue.offer(Integer.parseInt(st.nextToken()));
        }

        while (M-- > 0) {
            sb.append(queue.poll()).append(SPACE);
        }

        System.out.print(sb);
    }
}
