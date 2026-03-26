import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        Date[] dates = new Date[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int sm = Integer.parseInt(st.nextToken());
            int sd = Integer.parseInt(st.nextToken());
            int em = Integer.parseInt(st.nextToken());
            int ed = Integer.parseInt(st.nextToken());

            int start = sm * 100 + sd;
            int end = em * 100 + ed;

            dates[i] = new Date(start, end);
        }

        Arrays.sort(dates); // 시작날짜순 오름차순 정렬

        int count = 0;
        int beforeEnd = 301;
        int newEnd = 301;
        int i = 0;
        while (beforeEnd < 1201) {
            newEnd = beforeEnd;

            while (i < N && dates[i].start <= beforeEnd) {
                newEnd = Math.max(newEnd, dates[i].end);
                i++;
            }

            if (newEnd == beforeEnd) {
                System.out.print(0);
                return;
            }

            beforeEnd = newEnd;
            count++;
        }

        if (newEnd >= 1201) {
            System.out.print(count);
        } else {
            System.out.print(0);
        }
    }

    static class Date implements Comparable<Date> {
        int start;
        int end;

        public Date(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Date d) {
            return this.start - d.start;
        }
    }
}
