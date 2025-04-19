import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < T; i++) {
            DualQueue dualQueue = new DualQueue();
            int k = Integer.parseInt(br.readLine());

            for (int j = 0; j < k; j++) {
                st = new StringTokenizer(br.readLine());
                String command = st.nextToken();
                int val = Integer.parseInt(st.nextToken());

                switch (command) {
                    case "D":
                        if (val == 1) dualQueue.pollMax();
                        if (val == -1) dualQueue.pollMin();
                        break;
                    case "I":
                        dualQueue.offer(val);
                        break;
                }
            }

            if (dualQueue.isEmpty()) {
                answer.append("EMPTY").append("\n");
            } else {
                answer.append(dualQueue.peekMax()).append(" ")
                        .append(dualQueue.peekMin()).append("\n");
            }
        }

        System.out.print(answer.toString().trim());
    }

    static class DualQueue {
        Queue<Integer> min = new PriorityQueue<>();
        Queue<Integer> max = new PriorityQueue<>(Comparator.comparingInt(Integer::intValue).reversed());
        Map<Integer, Integer> count = new HashMap<>();

        public void offer(int val) {
            count.merge(val, 1, Integer::sum);
            min.offer(val);
            max.offer(val);
        }

        public void pollMin() {
            // 유효하지 않은 요소들 제거
            while (!min.isEmpty() && count.get(min.peek()) == 0) {
                min.poll();
            }

            if (min.isEmpty()) return;

            int poll = min.poll();
            count.put(poll, (count.get(poll) - 1));
        }

        public void pollMax() {
            // 유효하지 않은 요소들 제거
            while (!max.isEmpty() && count.get(max.peek()) == 0) {
                max.poll();
            }

            if (max.isEmpty()) return;

            int poll = max.poll();
            count.put(poll, (count.get(poll) - 1));
        }

        public String peekMin() {
            // 유효하지 않은 요소들 제거
            while (!min.isEmpty() && count.get(min.peek()) == 0) {
                min.poll();
            }

            if (min.isEmpty()) return "EMPTY";

            int peek = min.peek();
            return peek + "";
        }

        public String peekMax() {
            while (!max.isEmpty() && count.get(max.peek()) == 0) {
                max.poll();
            }

            if (max.isEmpty()) return "EMPTY";

            int peek = max.peek();
            return peek + "";
        }

        public boolean isEmpty() {
            while (!max.isEmpty() && count.get(max.peek()) == 0) {
                max.poll();
            }

            return max.isEmpty();
        }
    }
}
