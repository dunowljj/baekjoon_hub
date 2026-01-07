import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static List<Village> villages = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st;
        long total = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            villages.add(new Village(x, a));
            total += a;
        }

        Collections.sort(villages, comparingInt(Village::getLoc));

        long mid = (total + 1) / 2;
        long acc = 0;
        for (Village village : villages) {
            acc += village.count;
            if (acc >= mid) {
                System.out.print(village.loc);
                return;
            }
        }
    }

    static class Village {
        int loc;
        int count;

        public Village(int loc, int count) {
            this.loc = loc;
            this.count = count;
        }

        public int getLoc() {
            return loc;
        }
    }
}
