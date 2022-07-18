import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String input ="";

        while (!(input = br.readLine()).equals("0")) {
            sb.append(input);

            int num = Integer.parseInt(input);
            int reverse = Integer.parseInt(sb.reverse().toString());

            sb.delete(0, sb.length());

            if (num == reverse) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
        }
    }
}
