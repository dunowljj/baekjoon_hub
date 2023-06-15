import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        char[] alphabets = new char[26];
        for (int i = 0; i < 26; i++) alphabets[i] = (char) (i + 65);
        int[] val = {3, 2, 1, 2, 3, 3, 3, 3, 1, 1, 3, 1, 3, 3, 1, 2, 2, 2, 1, 2, 1, 1, 2, 2, 2, 1};

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < 26; i++) map.put(alphabets[i], val[i]);

        int answer = 0;
        for (int i = 0; i < input.length(); i++) {
            answer += map.get(input.charAt(i));
        }

        String result = (answer % 10) % 2 == 1 ?  "I'm a winner!" : "You're the winner?";
        System.out.println(result);
    }
}
