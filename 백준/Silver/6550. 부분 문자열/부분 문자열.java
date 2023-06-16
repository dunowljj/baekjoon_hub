import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String line;

        while ((line = br.readLine()) != null) {
            st = new StringTokenizer(line);
            String s = st.nextToken();
            String t = st.nextToken();

            int sIdx = 0;
            int tIdx = 0;

            while (tIdx < t.length() && sIdx < s.length()) {
                if (s.charAt(sIdx) == t.charAt(tIdx)) sIdx++;
                tIdx++;
            }

            if (sIdx == s.length()) System.out.println("Yes");
            else System.out.println("No");
        }
    }
}
