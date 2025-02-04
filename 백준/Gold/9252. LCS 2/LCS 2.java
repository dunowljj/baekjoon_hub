import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s1 = br.readLine();
        String s2 = br.readLine();

        int n1 = s1.length();
        int n2 = s2.length();
        int[][] dp = new int[n2 + 1][n1 + 1];

        for (int i = 1; i < n2 + 1; i++) {
            for (int j = 1; j < n1 + 1; j++) {
                if (s2.charAt(i - 1) == s1.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        int len = dp[n2][n1];
        sb.append(len).append(System.lineSeparator());

        StringBuilder LCS = new StringBuilder();
        if (len != 0) {
            int y = n2;
            int x = n1;

            while (1 <= y && 1 <= x) {

                if (s2.charAt(y - 1) == s1.charAt(x - 1)) {
                    if (dp[y - 1][x - 1] + 1 == dp[y][x]) {
                        LCS.append(s2.charAt(y - 1));
                        y--; x--;
                    }
                } else {
                    if (dp[y - 1][x] > dp[y][x - 1]) {
                        y--;
                    } else {
                        x--;
                    }
                }
            }
        }

        sb.append(LCS.reverse());

        System.out.print(sb.toString().trim());
    }

    private static void print(int n1, int n2, int[][] dp) {
        for (int i = 1; i < n2 + 1; i++) {
            for (int j = 1; j < n1 + 1; j++) {
                System.out.print(dp[i][j]+" ");
            }
            System.out.println();
        }
    }
}
