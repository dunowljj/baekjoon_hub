import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        int[] val = {3, 2, 1, 2, 3, 3, 3, 3, 1, 1, 3, 1, 3, 3, 1, 2, 2, 2, 1, 2, 1, 1, 2, 2, 2, 1};

        int answer = 0;
        for (int i = 0; i < input.length(); i++) {
            answer += val[input.charAt(i) - 65];
        }

        String result = (answer % 10) % 2 == 1 ?  "I'm a winner!" : "You're the winner?";
        
        System.out.println(result);
    }
}
