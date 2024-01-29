import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import static java.util.Comparator.*;

public class Main {

    public static final String NO_PRESENT_TO_GIVE = "-1";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> presents = new PriorityQueue<>(reverseOrder());
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int presentCount = Integer.parseInt(st.nextToken());

            if (presentCount == 0) {
                givePresent(presents);
            } else {
                takePresentsInBase(presentCount, st, presents);
            }
        }
    }

    private static void givePresent(PriorityQueue<Integer> presents) {
        if (presents.isEmpty()) {
            System.out.println(NO_PRESENT_TO_GIVE);
        } else {
            System.out.println(presents.poll());
        }
    }

    private static void takePresentsInBase(int presentCount, StringTokenizer st, PriorityQueue<Integer> presents) {
        for (int i = 0; i < presentCount; i++) {
            presents.offer(Integer.parseInt(st.nextToken()));
        }
    }
}

/**
 * 0일때마다 출력해야한다.
 */