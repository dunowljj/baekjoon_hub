import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.util.Comparator.comparingInt;

public class Main {

    private static int[] dp;
    private static int[][] lines;

    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        lines = new int[N + 1][2];
        lines[0] = new int[]{0, 0}; // 아무와도 겹치지 않는 라인 생성

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            lines[i] = new int[]{
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())
            };
        }

        Arrays.sort(lines, comparingInt(a -> a[0]));

        dp = new int[N + 1];

        for (int i = 1; i <= N; i++) {

            dp[i] = 1;    // 최소 개수인 1로 초기화
            for (int j = 0; j < i; j++) {
                if (lines[i][1] > lines[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        int max = 0;
        for (int i = 1; i <= N; i++) {
            max = Math.max(max, dp[i]);
        }

        System.out.print(N - max);
    }

    private static void printDp() {
        for (int i = 1; i <= N; i++) {
            System.out.print(dp[i] + " ");
        }

        System.out.println();
    }
}

/*
전깃줄 수 1~100
위치 1~500

1) 100개 전깃줄마다 해당 전깃줄에 겹치지 않는 전깃줄 수를 센다.
2) 겹치지 않는 수가 가장 많은 경우, 해당 전깃줄과 안겹치는 나머지들이 남길 수 있는 최대 전깃줄이다.
- 문제는 a줄과 겹치지 않는 b, c가 있다해도, b와c가 겹칠수도 있다.
- 애초에 A전봇대의 수를 기준으로 정렬하면, B 연결 지점만으로 교차하지 않는지 확인 가능하다.
- 정렬했기때문에, 현재 전선x 기준 이전 인덱스의 전선 중에 y라는 전선이 x와 교차하지 않는다면,
 y와 교차하지 않는 이전의 전선들 또한 x와 교차하지 않는다. 즉, 해당 전선의 dp값을 이용 가능하다.




 */