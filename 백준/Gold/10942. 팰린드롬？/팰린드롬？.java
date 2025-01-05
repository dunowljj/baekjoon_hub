import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] nums = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        // 펠린드롬 체크
        boolean[][] isPalindrome = new boolean[N + 1][N + 1];

        for (int start = 1; start <= N; start++) {
            for (int end = start; end <= N; end++) {
                checkPalindrome(start, end, nums, isPalindrome);
            }
        }

        StringBuilder sb = new StringBuilder();
        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            if (isPalindrome[S][E]) sb.append(1);
            else sb.append(0);

            sb.append(System.lineSeparator());
        }

        System.out.println(sb.toString().trim());
    }

    private static void checkPalindrome(int start, int end, int[] nums, boolean[][] isPalindrome) {
        int ns = start;
        int ne = end;

        while (ns < ne && nums[ns] == nums[ne]) {
            ns++; ne--;
        }

        // 해당범위에서 모두 대칭인 경우
        isPalindrome[start][end] = ns >= ne;
    }
}

/**
 * N 2000
 * 2000 + 1999 +... 2 + 1 -> 2001 * 2000 / 2 -> 200만개의 연속부분수열 존재
 * 2000 + 1999*2 + 1998*3 ... 2 * 1999 + 2000
 * M 100만
 *
 * 100만 * 2000 -> 200억
 * -> 미리 팰린드롬 여부를 구해놔야한다.
 */