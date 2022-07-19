import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        StringBuilder answer = new StringBuilder();
        answer.append("<");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Queue<Integer> queue = new LinkedList<>();

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 입력값 모두 큐에 넣기
        for (int i = 1; i <= N; i++) {
            queue.offer(i);
        }

        int count = 1;
        while (!queue.isEmpty()) {

            if (count++ % K == 0) {
                answer.append(queue.poll()).append(", ");
            } else {
                queue.offer(queue.poll());
            }

        }

        answer.deleteCharAt(answer.lastIndexOf(","));
        answer.deleteCharAt(answer.lastIndexOf(" "));
        answer.append(">");

        bw.write(answer.toString());
        bw.flush();
        bw.close();
    }
}