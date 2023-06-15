import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String written = br.readLine();
        String keyword = br.readLine();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < written.length(); i++) {
            char now = written.charAt(i);
            if ('0' <= now && now <= '9') continue;
            sb.append(now);
        }

        System.out.println((sb.toString()).contains(keyword) ? 1 : 0);
    }
}