import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final char CHAR_I = 'I';
    public static final char CHAR_O = 'O';

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());
        String S = br.readLine();

        List<Integer> pattern = new ArrayList<>();

        int accumulation = 0;
        for (int i = 0; i < M - 2;) {
            char now = S.charAt(i);
            char next1 = S.charAt(i + 1);
            char next2 = S.charAt(i + 2);

            if (now == CHAR_I && next1 == CHAR_O && next2 == CHAR_I) {
                accumulation++;
                if (accumulation == 1) pattern.add(accumulation);
                else pattern.set(pattern.size() - 1, accumulation);
                i+=2;
            } else {
                accumulation = 0;
                i++;
            }
        }

        int count = 0;
        for (int n : pattern) {
            if (n >= N) {
                count += n - N + 1;
            }
        }

        System.out.print(count);
    }
}