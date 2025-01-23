import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long min = Long.parseLong(st.nextToken());
        long max = Long.parseLong(st.nextToken());

        int maxSqrt = (int) (Math.sqrt(max));
        long answer = max - min + 1;
        boolean[] canDivided = new boolean[(int) (max - min + 1)];

        for (long i = 2; i <= maxSqrt; i++) {

            long pow = i * i;

            long start = min % pow == 0 ?
                    min / pow :
                    min / pow + 1;

            long end = max / pow;

            for (long j = start; j <= end; j++) {
                int idx = (int) ((j * pow) - min);

                if (!canDivided[idx]) {
                    canDivided[idx] = true;
                    answer--;
                }
            }
        }

        System.out.print(answer);
    }
}