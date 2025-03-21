import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine();
        String T = br.readLine();

        StringBuilder sb = new StringBuilder(T);
        for (int i = sb.length() - 1; i >= 0; i--) {
            if (sb.charAt(i) == 'A') {
                sb.deleteCharAt(i);
            }

            else if (sb.charAt(i) == 'B') {
                sb.deleteCharAt(i);
                sb.reverse();
            }

            if (sb.toString().equals(S)) {
                System.out.print(1);
                return;
            }
        }

        System.out.print(0);
    }
}