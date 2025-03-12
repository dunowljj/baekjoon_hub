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

        Queue<Integer> left = new PriorityQueue<>((i1,i2) -> i2 - i1);
        Queue<Integer> right = new PriorityQueue<>();

        StringBuilder answer = new StringBuilder();
        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(br.readLine());

            if (left.size() > right.size()) right.offer(num);
            else left.offer(num);

            right.offer(left.poll());
            left.offer(right.poll());
            answer.append(left.peek()).append(System.lineSeparator());
        }

        System.out.println(answer.toString().trim());
    }
}

/**
 * [1 2 5 10]
 */