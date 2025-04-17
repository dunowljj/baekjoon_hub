import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            lines.add(new Line(x, y));
        }

        Collections.sort(lines);


        long answer = 0;
        int start = lines.get(0).start;
        int end = lines.get(0).end;
        for (int i = 1; i < N; i++) {
            Line now = lines.get(i);

            if (now.start <= end) {
                end = Math.max(end, now.end);
            } else {
                answer += end - start;
                start = now.start;
                end = now.end;
            }
        }

        // 처리안된 마지막
        answer += end - start;

        System.out.print(answer);
    }

    static class Line implements Comparable<Line> {
        int start;
        int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Line o) {
            if (this.start == o.start) {
                return this.end - o.end;
            }

            return this.start - o.start;
        }

        public int length() {
            return end - start;
        }
    }
}
