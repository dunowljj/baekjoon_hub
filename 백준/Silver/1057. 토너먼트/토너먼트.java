import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int kim = Integer.parseInt(st.nextToken());
        int lim = Integer.parseInt(st.nextToken());

        int round = 1;
        while (N > 1 && !AreEncountered(kim, lim)) {
            round++;
            kim = kim / 2 + kim % 2;
            lim = lim / 2 + lim % 2;

            N = N / 2 + N % 2;
        }

        System.out.print(round);
    }

    // 두 사람의 번호가 인접(1차이)하고, 둘 중 큰 번호가 짝수일때
    private static boolean AreEncountered(int kim, int lim) {
        return Math.abs(kim - lim) == 1 && Math.max(kim, lim) % 2 == 0;
    }
}
/**
 * 서로 대결하지 않는 경우가 있나?
 * 부전승을 해도 결국 대결하는데?
 *
 *  1 2 3 4 5 6 7 8 9 10 11
 *    1   2   3   4   5  6
 *        1       2      3
 *                1      2
 *
 *  1 2 3 4 5 6 7 8 9 10 11 12 13
 *    1   2   3   4   5     6
 *        1       2         3
 *                1         2
 *
 * N / 2 + N % 2 -> 다음 번호
 * 마지막 번호이고 홀수인 경우 부전승이며 N/2가 다음 번호이다.
 */