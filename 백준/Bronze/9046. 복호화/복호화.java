import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            int[] counts = new int[26];
            String line = br.readLine();

            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == ' ') continue;
                counts[line.charAt(j) - 'a']++;
            }

            int max = 0;
            for (int j = 0; j < counts.length; j++) {
                max = Math.max(max, counts[j]);
            }

            List<Integer> maxList = new ArrayList<>();
            for (int j = 0; j < counts.length; j++) {
                if (counts[j] == max) maxList.add(j);
            }

            if (maxList.size() >= 2) System.out.println("?");
            else System.out.println((char)(maxList.get(0) + 97));
        }
    }
}
