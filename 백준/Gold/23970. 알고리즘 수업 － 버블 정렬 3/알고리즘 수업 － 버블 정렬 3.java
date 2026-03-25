import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] A = new int[N];
        int[] B = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }

        int correctCount = 0;
        for (int i = 0; i < N; i++) {
            if (A[i] == B[i]) correctCount++;
        }

        if (correctCount == N) {
            System.out.print(1);
            return;
        }

        for (int phase = 0; phase < N; phase++) {
            int change = 0;

            for (int i = 0; i < N - 1; i++) {
                if (A[i] > A[i + 1]) {
                    if (A[i] == B[i]) correctCount--;
                    if (A[i+1] == B[i+1]) correctCount--;

                    swap(A, i, i + 1);

                    if (A[i] == B[i]) correctCount++;
                    if (A[i+1] == B[i+1]) correctCount++;

                    if (correctCount == N) {
                        System.out.print(1);
                        return;
                    }

                    change++;
                }
            }

            if (change == 0) break;
        }

        System.out.print(0);
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
}
