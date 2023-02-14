import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Document implements Comparable<Document> {

        int idx;
        int priority;

        public Document(int idx, int priority) {
            this.idx = idx;
            this.priority = priority;
        }


        @Override
        public int compareTo(Document o) {
            return  o.priority - this.priority;
        }
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(br.readLine());

        PriorityQueue<Document> pq = new PriorityQueue<>();
        Queue<Document> queue = new LinkedList<>();

        for (int i = 0; i < cases; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int target = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < num; j++) {
                int idx = j;
                int priority = Integer.parseInt(st.nextToken());
                queue.offer(new Document(idx, priority));
                pq.offer(new Document(idx, priority));
            }

            int count = 1;
            while (true) {
                // 우선순위 높은 문서나올때까지 순환시키기
                while (pq.peek().priority != queue.peek().priority) {
                    queue.offer(queue.poll());
                }

                if (queue.peek().idx == target) break;
                // 높은 문서가 나오면 인쇄하기
                queue.poll();
                pq.poll();
                count++;
//                System.out.println("Pq: " + pq.peek().priority + ", idx: "+ pq.peek().idx);
//                System.out.println("Qu: " +queue.peek().priority+ ", idx: " + queue.peek().idx);
            }

            // 남은 문서 비우기
            queue.clear();
            pq.clear();

            sb.append(count).append("\n");
        }
        System.out.print(sb);
    }
}
