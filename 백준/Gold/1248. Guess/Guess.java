import java.io.*;

public class Main {
    static int n;
    static char[][] signMatrix;
    static int[] seq;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        signMatrix = new char[n][n];
        seq = new int[n];

        String input = br.readLine();
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                signMatrix[i][j] = input.charAt(idx++);
            }
        }

        dfs(0);

    }

    static void dfs(int depth) {
        if (depth == n) {
            for (int num : seq) {
                sb.append(num).append(" ");
            }
            System.out.print(sb.toString().trim());
            System.exit(0);
        }

        for (int i = -10; i <= 10; i++) {
            seq[depth] = i;
            if (check(depth)) {
                dfs(depth + 1);
            }
        }
    }

    private static boolean check(int idx) {
        int sum = 0;
        for (int i = idx; i >= 0; i--) {
            sum += seq[i];
            if (signMatrix[i][idx] != (sum == 0 ? '0' : (sum > 0  ? '+' : '-'))) {
                return false;
            }
        }
        return true;
    }
}
/*
sign matrix의 Sij는 sequence[i]~[j]의 합이 음수인지 양수인지 0인지 판별한 결과
시퀀스 길이는 1~10, 각 요소는 -10 ~ 10의 정수
주어진 시퀀스와 일치하는 sign matrix와 일치하는 시퀀스가 1개 이상이면, 그 중 아무거나 출력

-10 ~10까지에서 10개의 순열 뽑기?
10개로 10개 뽑기는 10!이면 된다.
 */