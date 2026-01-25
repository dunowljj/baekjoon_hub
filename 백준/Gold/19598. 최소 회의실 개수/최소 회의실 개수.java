import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        List<int[]> times = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            times.add(new int[]{start, end});
        }

        times.sort(comparingInt(t -> t[0]));

        PriorityQueue<Integer> ends = new PriorityQueue<>();
        int count = 0;
        for (int[] time : times) {
            ends.add(time[1]);

            while (!ends.isEmpty() && ends.peek() <= time[0]) {
                ends.poll();
            }

            count = Math.max(count, ends.size());
        }

        System.out.print(count);
    }
}

/**
 * 1.정렬 후 순회하면서 끝값들을 pq에 넣어놓고, 처리하기.
 *
 * 2.TreeMap에 시작,끝을 +- 카운트하고 최대구간 찾기
 *
 */