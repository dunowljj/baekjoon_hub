import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<Character, Integer> map = new HashMap<>();

        String word = br.readLine();

        for (int i = 0; i < word.length(); i++) {
            char now = Character.toUpperCase(word.charAt(i));

            if (map.containsKey(now)) {
                int count = map.get(now);
                map.put(now, count + 1);
            } else {
                map.put(now, 1);
            }
        }

        /**
         * A : 1
         * B : 1
         * C : 2
         *
         * C
         *
         * max = 1
         * answer= A
         *
         * map.get(Z)
         */

        int max = -1;
        Character answer = null;
        for (Map.Entry<Character, Integer> e : map.entrySet()) {

             if (max < e.getValue()) {
                max = e.getValue();
                answer = e.getKey();
            }
        }

        int count = 0;
        for (Map.Entry<Character, Integer> e : map.entrySet()) {
            if (max == e.getValue()) {
                count++;
            }
        }

        if (count >= 2) {
            System.out.println("?");
        } else {
             System.out.print(answer);
        }
    }
}
