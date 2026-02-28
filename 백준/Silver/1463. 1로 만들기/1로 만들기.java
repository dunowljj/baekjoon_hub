import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N + 1];
        Arrays.fill(arr, Integer.MAX_VALUE);
        arr[N] = 0;

        for (int i = N; i > 0; i--) {
            int count = arr[i] + 1;

            if (i % 3 == 0) arr[i / 3] = Math.min(arr[i / 3], count);
            if (i % 2 == 0) arr[i / 2] = Math.min(arr[i / 2], count);
            arr[i - 1] = Math.min(arr[i - 1], count);
        }

        System.out.print(arr[1]);
    }
}
