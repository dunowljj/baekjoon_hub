import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static final char NON = '@';
    public static final char BACK_SPACE = '-';

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        while (T-- > 0) {
            String input = br.readLine();

            int len = input.length();
            List<Character> pwd = new LinkedList<>();

            int cursor = 0;
            for (int i = 0; i < len; i++) {
                char ch = input.charAt(i);

                if (ch == '<') {
                    if (cursor != 0) cursor--;
                } else if (ch == '>') {
                    if (cursor + 1 <= pwd.size()) cursor++;
                } else if (ch == BACK_SPACE) {
                    if (cursor != 0) {
                        pwd.remove(cursor - 1);
                        cursor--;
                    }
                } else {
                    if (cursor == pwd.size()) pwd.add(ch);
                    else pwd.add(cursor, ch);
                    cursor++;
                }
            }

            for (char ch : pwd) {
                answer.append(ch);
            }

            answer.append(System.lineSeparator());
        }

        System.out.print(answer.toString().trim());
    }
}
