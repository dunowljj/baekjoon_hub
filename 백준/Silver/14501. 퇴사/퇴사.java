import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        int[] dp = new int[N + 1]; // 다음 요소에 dp값을 갱신해서 이렇게 해놈
        List<Counsel> counsels = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            counsels.add(new Counsel(st.nextToken(), st.nextToken()));
        }

        // [0,0]
        // 다음 칸에다가 최댓값 갱신하기
        for (int idx = 0; idx < N; idx++) {
            Counsel now = counsels.get(idx);
            int counselFinishIdx = idx + now.time - 1;

            if (counselFinishIdx < N && dp[counselFinishIdx + 1] < dp[idx] + now.pay) {
                dp[counselFinishIdx + 1] = dp[idx] + now.pay;
            }

            // 이 줄을 추가하지 않으면, 상담이 종료된 다음 시점에만 금액이 갱신된다.
            dp[idx + 1] = Math.max(dp[idx], dp[idx + 1]);
        }

        // 굳이 전체 dp 배열을 순회할 필요 없이 마지막 값만 확인하면 된다.
        System.out.print(dp[N]);
    }
    
    static class Counsel {
        int time;
        int pay;

        public Counsel(String time, String pay) {
            this.time = Integer.parseInt(time);
            this.pay = Integer.parseInt(pay);
        }

        @Override
        public String toString() {
            return "Counsel{" +
                    "time=" + time +
                    ", pay=" + pay +
                    '}';
        }
    }
}

/**
 * 현재일포함 기간동안 상담 불가 --> (현재 일수) + (Ti - 1) 까지 다른 상담을 못한다. -> 인덱스 = 일수 -1 이므로 (인덱스+Ti)
 * N+1일 이후까지 진행되는 상담은 할 수 없다. --> (현재 일수 + (Ti - 1)) > N 이면 상담 불가 -> 인덱스 + Ti < N 이어야 한다.
 * 최대 수익을 구하자.
 *
 * 1) 완탐으로 모든 경우 구하기
 * - N : 1~15 이기때문에 2^15번의 연산으로 해결 가능하다.
 * 2) dp
 * - 각 일수에서 벌 수 있는 최대 금액을 구한다.
 * - 상담이 종료되는 시점에 금액을 넣어야 할듯하다.
 */
