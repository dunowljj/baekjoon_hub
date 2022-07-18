import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String input ="";

        while (!(input = br.readLine()).equals("0")) {
            int num = Integer.parseInt(input);
            int reverse = Integer.parseInt(new StringBuilder(input).reverse().toString());

            if (num == reverse) {
                sb.append("yes").append("\n");
            } else {
                sb.append("no").append("\n");
            }
        }
        System.out.print(sb.toString());
    }
}
