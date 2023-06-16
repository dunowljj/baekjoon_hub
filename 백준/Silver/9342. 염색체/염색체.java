import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern pattern = Pattern.compile("^[A-F]?A+F+C+[A-F]?$");

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            String input = br.readLine();
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) sb.append("Infected!\n");
            else sb.append("Good\n");
        }

        System.out.print(sb);
    }
}
