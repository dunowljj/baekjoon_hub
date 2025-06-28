import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            int K = Integer.parseInt(br.readLine());
            Queue<Long> queue = new PriorityQueue<>();

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < K; i++) {
                queue.offer(Long.parseLong(st.nextToken()));
            }
            
            long total = 0;
            long temp = 0;
            while (queue.size() >= 2) {
                temp = queue.poll() + queue.poll();
                queue.offer(temp);

                total += temp;
            }

            sb.append(total).append(System.lineSeparator());
        }
        
        System.out.print(sb.toString().trim());
    }
}
