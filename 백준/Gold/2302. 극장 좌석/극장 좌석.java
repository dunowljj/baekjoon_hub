import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static boolean[] isVIP;
    static int N, M;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        isVIP = new boolean[N + 1];

        for (int i = 0; i < M; i++) {
            int fixNo = Integer.parseInt(br.readLine());
            isVIP[fixNo] = true;
        }

        dfs(1);

        System.out.println(count);
    }

    static void dfs(int idx) {
        if (idx >= N) {
            count++;
            return;
        }

        if (!isVIP[idx] && !isVIP[idx + 1])  {
            dfs(idx + 2);
        }

        dfs(idx + 1);
    }
}
/**
 * - vip아니라면 swap 가능.
 * - swap한 곳을 다시 swap하면 인접이 아님
 * - swap하지 않고 넘어갈 수도 있음
 */