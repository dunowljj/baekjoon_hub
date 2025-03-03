import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (i1, i2) -> {
                    if (Math.abs(i1) == Math.abs(i2)) {
                        return i1 - i2;
                    }

                    return Math.abs(i1) - Math.abs(i2);
                }
        );
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(br.readLine());
            if (x != 0) {
                pq.offer(x);
            }

            if (x == 0) {
                if (pq.isEmpty()) sb.append(0).append(System.lineSeparator());
                else sb.append(pq.poll()).append(System.lineSeparator());
            }
        }
        System.out.print(sb.toString().trim());
    }
}

/**
 * -2^31 < x < 2^31 이므로, -2^31이 주어질 경우는 없다.
 */