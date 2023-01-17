import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        int answer = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String expression = br.readLine();

        final Pattern PATTERN_OF_NUMBER = Pattern.compile("[0-9]+");
        final Pattern PATTERN_OF_OPERATOR = Pattern.compile("[\\+\\-]");

        final Matcher MATCHER_OF_NUMBER = PATTERN_OF_NUMBER.matcher(expression);
        final Matcher MATCHER_OF_OPERATOR = PATTERN_OF_OPERATOR.matcher(expression);

        MATCHER_OF_NUMBER.find();
        int start = Integer.parseInt(MATCHER_OF_NUMBER.group());
        answer += start;

        while (MATCHER_OF_OPERATOR.find()) {
            MATCHER_OF_NUMBER.find();
            int num = Integer.parseInt(MATCHER_OF_NUMBER.group());

            String operator = MATCHER_OF_OPERATOR.group();

            if (operator.equals("+")) {
                answer += num;

            } else {
                answer -= num;
                break;
            }
        }

        while (MATCHER_OF_NUMBER.find()) {
            int num = Integer.parseInt(MATCHER_OF_NUMBER.group());
            answer -= num;
        }

        System.out.print(answer);
    }
}
