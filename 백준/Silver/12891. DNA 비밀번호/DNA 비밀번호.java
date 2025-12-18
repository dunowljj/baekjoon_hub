import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int[] count = new int[4];
    private static int a,c,g,t;
    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int S = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());

        String dna = br.readLine();

        st = new StringTokenizer(br.readLine());
        a = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        g = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        for (int i = 0; i < P; i++) {
            char ch = dna.charAt(i);
            add(ch);
        }

        check();

        int left = 0;
        int right = P;

        while (left < right && right < S) {
            char lch = dna.charAt(left++);
            char rch = dna.charAt(right++);

            subtract(lch);
            add(rch);

            check();
        }

        System.out.print(answer);
    }

    private static void add(char ch) {
        if (ch == 'A') count[0]++;
        else if (ch == 'C') count[1]++;
        else if (ch == 'G') count[2]++;
        else if (ch == 'T') count[3]++;
    }

    private static void subtract(char ch) {
        if (ch == 'A') count[0]--;
        else if (ch == 'C') count[1]--;
        else if (ch == 'G') count[2]--;
        else if (ch == 'T') count[3]--;
    }

    private static void check() {
        if ((count[0] >= a) && (count[1] >= c) && (count[2] >= g) && (count[3] >= t)) {
            answer++;
        }
    }
}
