import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static int[] counts = new int[26];
    static StringBuilder sb = new StringBuilder();
    static int N;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String S = br.readLine();
        N = S.length();

        for (char ch : S.toCharArray()) {
            counts[ch - 'a']++;
        }

        dfs(0, -1);
        System.out.print(count);
    }

    private static void dfs(int depth, int before) {
        if (depth == N) {
            count++;
            return;
        }

        for (int i = 0; i < 26; i++) {
            if (counts[i] == 0 || i == before) continue;

            counts[i]--;
            dfs(depth + 1, i);
            counts[i]++;
        }
    }
}
