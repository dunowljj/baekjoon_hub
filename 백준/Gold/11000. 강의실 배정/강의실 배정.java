import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PriorityQueue<Lecture> pq = new PriorityQueue<>(comparingInt(Lecture::getStart));
        PriorityQueue<Integer> endHeap = new PriorityQueue<>();

        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            pq.offer(new Lecture(s, t));
        }

        // 마지막 값을 기억하는 pq를 따로 만든다?
        Lecture first = pq.poll();
        endHeap.offer(first.end);
        int maxDuplication = 1;
        int duplicationCount = 1;

        while (!pq.isEmpty()) {
            Lecture now = pq.poll();
            duplicationCount++;

            // 이전 강의 중 끝난 강의가 존재할때
            while (!endHeap.isEmpty() && endHeap.peek() <= now.start) {
                endHeap.poll();
                duplicationCount--;
            }

            endHeap.offer(now.end);
            maxDuplication = Math.max(maxDuplication, duplicationCount);
        }

        System.out.print(maxDuplication);
    }

    static class Lecture {
        int start;
        int end;

        public Lecture(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }
    }
}

/**
 * 최대 겹치는 수를 구하면 된다.
 */