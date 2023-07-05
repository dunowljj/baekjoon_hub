import java.io.*;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Stack<Character> checker = new Stack<>();


        String line;
        while (!(line = br.readLine()).equals(".")) {
            boolean isBalanced = checkOneLine(checker, line);

            if (isBalanced) bw.write("yes\n");
            else bw.write("no\n");

            checker.clear();
        }

        bw.flush();
        bw.close();
    }

    private static boolean checkOneLine(Stack<Character> checker, String line) {
        int idx = 0;
        for (char now : line.toCharArray()) {
            // () 체크
            if (now == '(') {
                checker.push(now);
            } else if (now == ')') {
                if (checker.isEmpty() || checker.peek() == '[') {
                    return false;
                }
                checker.pop();

            // [] 체크
            } else if (now == '[') {
                checker.push(now);
            } else if (now == ']') {
                if (checker.isEmpty() || checker.peek() == '(') {
                    return false;
                }
                checker.pop();
            }

            idx ++;
        }

        // 체크용 스택이 비어있다. --> 균형을 이룬다.
        return checker.isEmpty();
    }
}
