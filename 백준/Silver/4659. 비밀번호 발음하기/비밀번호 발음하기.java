import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final char[] vowel = {'a', 'i', 'o', 'u', 'e'};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String vowel = "aioue";
        String input;
        while (!(input = br.readLine()).equals("end")) {

            // 모음 하나는 반드시 포함
            if (!hasVowel(input))  {
                System.out.println("<"+input+"> is not acceptable.");
                continue;
            }

            int vowCount = 0; // 연속 모음 카운트
            int conCount = 0; // 연속 자음 카운트
            char before = '0';
            boolean finished = false;
            for (int i = 0; i < input.length(); i++) {
                char now = input.charAt(i);

                if (isVowel(now)) {
                    conCount = 0;
                    vowCount++;
                } else {
                    vowCount = 0;
                    conCount++;
                }

                // 같은 글자가 연속적으로 오는 경우 (ee, oo는 허용)
                if ((now != 'e' && now != 'o') && now == before) {
                    System.out.println("<"+input+"> is not acceptable.");
                    finished = true;
                    break;
                }

                // 모음 혹은 자음이 3개 연속
                if (vowCount == 3 || conCount == 3) {
                    System.out.println("<"+input+"> is not acceptable.");
                    finished = true;
                    break;
                }

                before = now;
            }

            if (!finished) System.out.println("<"+input+"> is acceptable.");
        }
    }

    private static boolean hasVowel(String input) {
        for (int i = 0; i < vowel.length; i++) {
            if (input.contains(vowel[i]+ "")) return true;
        }
        return false;
    }

    private static boolean isVowel(char now) {
        for (int i = 0; i < vowel.length; i++) {
            if (vowel[i] == now) {
                return true;
            }
        }
        return false;
    }

}
