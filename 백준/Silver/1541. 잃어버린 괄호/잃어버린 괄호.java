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

//        solution1(expression);
//        solution2(expression);
        solution3(expression);
    }

    private static void solution3(String expression) {
        int answer = 0;
        String[] splited = expression.split("-");

        for (String s : splited[0].split("\\+")) {
            answer += Integer.parseInt(s);
        }

        for (int i = 1; i < splited.length; i++) {
            for (String s : splited[i].split("\\+")) {
                answer -= Integer.parseInt(s);
            }
        }

        System.out.print(answer);
    }

    private static void solution1(String expression) {
        int answer = 0;
        final Pattern PATTERN_OF_NUMBER = Pattern.compile("[0-9]+");
        final Pattern PATTERN_OF_OPERATOR = Pattern.compile("[\\+\\-]");

        final Matcher MATCHER_OF_NUMBER = PATTERN_OF_NUMBER.matcher(expression);
        final Matcher MATCHER_OF_OPERATOR = PATTERN_OF_OPERATOR.matcher(expression);

        MATCHER_OF_NUMBER.find();
        int start = Integer.parseInt(MATCHER_OF_NUMBER.group());
        answer += start;

        while (MATCHER_OF_OPERATOR.find()) {
            String operator = MATCHER_OF_OPERATOR.group();
            MATCHER_OF_NUMBER.find();
            int num = Integer.parseInt(MATCHER_OF_NUMBER.group());


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

    private static void solution2(String expression) {
        int answer = 0;

        int sum = 0;
        int idx = expression.indexOf("-");
        String front;
        String back;
        if (idx == -1) {
            front = expression;

        } else {
            front = expression.substring(0, idx);
            back = expression.substring(idx);

            String[] split = back.split("[\\-\\+]");
            for (int i = 1; i < split.length; i++) {
                sum -= Integer.parseInt(split[i]);
            }
        }

        for (String num : front.split("[\\+\\-]")) {
            sum += Integer.parseInt(num);
        }

        System.out.println(sum);
    }
}

/*
'-'에 괄호를 칠때가 문제이다.
해당 시점부터 괄호가 쳐지는 모든 구간의 부호가 바뀌어야 한다.

즉, - 부호가 나온 시점부터 뒤에 있는 부호들은 바뀔 수 있다.


최소로 만들려면 어떻게 해야하나?
+가 있는 부분을 - 로 묶어야 한다.

ex)
10 - 10 + 50 + 30 ==> 10 - (10 + 50 + 30)
10 - 20 - 30 + 50 ==> 10 - 20 - (30 + 50)

1) - 부호 찾기
2) 찾는다면, 다음 부호 확인해서 + 인 경우 -로 변경

--> 결국 처음 -를 찾은 이후 모든 + 부호는 - 로 변경가능하다.

 */