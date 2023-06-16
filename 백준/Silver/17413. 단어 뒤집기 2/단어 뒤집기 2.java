import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        StringBuilder answer = new StringBuilder();
        StringBuilder turner = new StringBuilder();
        boolean inTag = false;
        for (int i = 0; i < input.length(); i++) {
            char now = input.charAt(i);



            if (now == '<') {
                inTag = true;
                if (now == '<') {
                    turner.reverse();
                    answer.append(turner).append(now);
                    turner.delete(0, turner.length());
                    continue;
                }
                answer.append('<');
                continue;
            }
            if (now == '>') {
                inTag = false;
                answer.append('>');
                continue;
            }

            // 태그 안이 아닌 경우 공백을 보면 뒤집는다.
            if (!inTag) {
                if (now == ' ') {
                    turner.reverse();
                    answer.append(turner).append(now);
                    turner.delete(0, turner.length());
                    continue;
                }
                turner.append(now);
            }
            // 태그 안이면 그냥 추가한다.
            else {
                answer.append(now);
            }

        }

        // 마지막 남은 단어 있다면 뱉어내기
        answer.append(turner.reverse());

        System.out.print(answer);
    }
}
