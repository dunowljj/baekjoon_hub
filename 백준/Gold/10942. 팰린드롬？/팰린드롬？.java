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

        // 길이 1, 2인경우 미리 처리
        for (int i = 1; i <= N; i++) {
            isPalindrome[i][i] = true;

            // 주어지는(칠판에 적은) 수는 자연수이며, 배열 0번째 요소는 0이므로 없는 값 취급 가능
            if (nums[i - 1] == nums[i]) isPalindrome[i - 1][i] = true;
        }

        // 길이 3부터 탐색
        for (int len = 3; len <= N; len++) {
            for (int start = 1; start <= N - len + 1; start++) {
                int end = start + len - 1;
                isPalindrome[start][end] = (nums[start] == nums[end]) && isPalindrome[start + 1][end - 1];
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

    // 길이가 3이상인 경우만 주어지므로 인덱스 위치 검증ㅇ 필요 없다.
}

/**
 * N 2000
 * 2000 + 1999 +... 2 + 1 -> 2001 * 2000 / 2 -> 200만개의 연속부분수열 존재
 * 2000 + 1999*2 + 1998*3 ... 2 * 1999 + 2000
 * M 100만
 *
 * 100만 * 2000 -> 200억
 * -> 미리 팰린드롬 여부를 구해놔야한다. -> 하나씩 구해도 통과하긴 한다.
 * -> dp를 이용해 빠르게 구할 수 있다.
 */