import java.io.*;
import java.util.PriorityQueue;

public class Main {

    public static final String LINE_BREAK = System.lineSeparator();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(reader.readLine());

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(reader.readLine());

            if (x == 0) {
                print(writer, pq);
            } else {
                pq.offer(x);
            }
        }

        writer.flush();
        writer.close();
    }

    private static void print(BufferedWriter writer, PriorityQueue<Integer> pq) throws IOException {
        if (pq.isEmpty()) writer.write(0 + LINE_BREAK);
        else writer.write(pq.poll() + LINE_BREAK);
    }
}
