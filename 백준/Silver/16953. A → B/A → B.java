import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int answer = Integer.MAX_VALUE;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        findMin(A, B, 0);

        if (answer == Integer.MAX_VALUE) System.out.print(-1);
        else System.out.print(answer + 1);
    }

    private static void findMin(long a, long b, int depth) {
        if (a > b) {
            return;
        }

        if (a == b) {
            answer = Math.min(answer, depth);
            return;
        }

        findMin(a * 2, b, depth + 1);
        findMin(Long.parseLong(a + "1"), b, depth + 1);
    }
}
