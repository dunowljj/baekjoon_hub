import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    static int N;
    static List<Long> decreases = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i <= 9; i++) {
            dfs(i, i);
        }

        Collections.sort(decreases);
        if (N >= decreases.size()) System.out.print(-1);
        else System.out.print(decreases.get(N));
    }

    private static void dfs(long num, int lastDigit) {
        decreases.add(num);

        for (int digit = 0; digit < lastDigit; digit++) {
            dfs(num * 10 + digit, digit);
        }
    }
}
