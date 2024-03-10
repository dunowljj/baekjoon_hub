import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {

    private static Set<Character> checker;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int answer = 0;

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            String word = br.readLine();

            if (isGroupWord(word)) answer++;
        }

        System.out.print(answer);
    }

    private static boolean isGroupWord(String word) {
        checker = new HashSet<>();

        int i = 0;
        while (i < word.length()) {
            char now = word.charAt(i);

            // 이전에 나온적이 있다
            if (checker.contains(now)) {
                return false;

            // 처음 나오는 알파벳 -> 중복 체크 후 연속된 알파벳 지나치기
            } else {
                checker.add(now);

                while (++i < word.length() && word.charAt(i) == now){}
            }
        }

        return true;
    }
}

/**
 * 알파벳 소문자만 들어온다.
 */