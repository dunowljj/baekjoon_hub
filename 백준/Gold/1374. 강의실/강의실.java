import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        Lecture[] lectures = new Lecture[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int no = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            lectures[i] = new Lecture(start, end);
        }

        Arrays.sort(lectures);

        Queue<Integer> ends = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            Lecture now = lectures[i];

            if (ends.isEmpty()) {
                ends.offer(now.end);
            } else {
                if (ends.peek() <= now.start) {
                    ends.poll();
                }
                ends.offer(now.end);
            }
        }

        System.out.print(ends.size());
    }

    static class Lecture implements Comparable<Lecture> {
        int no;
        int start;
        int end;

        public Lecture(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public Lecture(int no, int start, int end) {
            this.no = no;
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        @Override
        public int compareTo(Lecture o) {
            return this.start - o.start;
        }
    }
}