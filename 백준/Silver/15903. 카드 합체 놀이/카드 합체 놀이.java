import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        PriorityQueue<Long> pq = new PriorityQueue<>();

        StringTokenizer st = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            pq.offer(Long.parseLong(st.nextToken()));
        }

        for (int i = 0; i < m; i++) {
            long x = pq.poll();
            long y = pq.poll();

            long sum = x + y;
            pq.offer(sum);
            pq.offer(sum);
        }

        long answer = 0;
        while (!pq.isEmpty()) {
            answer += pq.poll();
        }

        System.out.print(answer);
    }
}
/**
 *  수 범위가 말이 안되는데?
 */