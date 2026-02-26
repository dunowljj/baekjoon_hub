import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine();

        Set<String> set = new HashSet<>();

        for (int size = 1; size <= S.length(); size++) {
            for (int i = 0; i + size <= S.length(); i++) {
                set.add(S.substring(i, i + size));
            }
        }

        System.out.print(set.size());
    }
}
/**
 * 1 + 2 + ... 1000개
 * 1001 * 1000 / 2 -> 500만
 */