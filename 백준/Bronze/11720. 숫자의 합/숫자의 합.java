import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int answer = 0;
        
        int N = Integer.parseInt(br.readLine());
        String nums = br.readLine();

        for (int i = 0; i < nums.length(); i++) {
            int num = nums.charAt(i) - '0';
            answer += num;
        }

        System.out.println(answer);
    }
}
