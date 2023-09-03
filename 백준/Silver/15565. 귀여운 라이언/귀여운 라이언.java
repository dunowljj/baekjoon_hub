import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static final int LION = 1;
    private static final int APEACH = 2;
    public static final String NOT_EXIST = "-1";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] dolls = new int[N];
        for (int i = 0; i < N; i++) {
            dolls[i] = Integer.parseInt(st.nextToken());
        }

        int r = 0, l = 0, lionCount = 0;

        // 최초 lion 등장 찾아서 탐색 시작점 정하기
        while (l < N) {
            if (dolls[l] == LION) {
                // 해당 LION 위치가 탐색 시작점이 된다.
                r = l;
                break;
            }
            l++;
        }

        // lion이 없으면 바로 종료 (K는 1부터)
        if (l == N) {
            System.out.print(NOT_EXIST);
            System.exit(0);
        }

        // lion을 K개 만큼 가지는 최초의 집합 구하기
        int len = 0;
        while (r < N) {
            if (lionCount == K) break;

            if (dolls[r] == LION) lionCount ++;

            r++;
            len++;
        }

        // l -> 해당 집합의 시작 인덱스
        // r -> 해당 집합의 마지막 인덱스 + 1 -> 마지막 인덱스로 변경
        r--;
        // len은 해당 집합의 길이를 나타낸다.

        // K를 만족하는 집합이 하나도 존재하지 않으면 -1 출력 후 종료
        if (lionCount != K) {
            System.out.print(NOT_EXIST);
            System.exit(0);
        }

//        System.out.println("initial len = " + len);
//        System.out.println("initial r = " + r);
//        System.out.println("initial l = " + l);

        // K == 1인 경우 예외 생각해보기


        // 존재한다면 최소 집합 탐색
        int minLen = len;
        while (true) {

            l++;
            len--;
            // 다음 라이언이 나올때까지 탐색
            while (l < N && dolls[l] != LION) {
                l++;
                len--;
            }

            if (l == N) break;
//            System.out.println("len = " + len);
//            System.out.println("r = " + r);
//            System.out.println("l = " + l);
//            System.out.println("------");

            r++;
            len++;
            // 다음 라이언이 나올때까지 탐색
            while (r < N && dolls[r] != LION) {
                r++;
                len++;
            }

            if (r == N) break;

            minLen = Math.min(minLen, len);

            if (minLen == K) break;
//            System.out.println("len = " + len);
//            System.out.println("r = " + r);
//            System.out.println("l = " + l);
//            System.out.println("minLen = " + minLen);
//            System.out.println("=======");
        }

        System.out.print(minLen);
    }
}
