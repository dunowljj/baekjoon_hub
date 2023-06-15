import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        String line = "";

        while (!(line = br.readLine()).equals("END")) {
            StringBuilder temp = new StringBuilder();
            temp.append(line).reverse();
            
            answer.append(temp).append("\n");
        }

        System.out.println(answer);
    }
}
