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

        int[] memo = new int[N + 1];
        List<Counsel> counsels = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            counsels.add(new Counsel(st.nextToken(), st.nextToken()));
        }

        // 다음 칸에다가 최댓값 갱신하기
        for (int idx = 0; idx < N; idx++) {
            Counsel now = counsels.get(idx);
            int counselFinishIdx = idx + now.time - 1;

            if (counselFinishIdx < N && memo[counselFinishIdx + 1] < memo[idx] + now.pay) {
                memo[counselFinishIdx + 1] = memo[idx] + now.pay;
            }

            memo[idx + 1] = Math.max(memo[idx], memo[idx + 1]);
        }

        int max = 0;
        for (int i = 0; i < memo.length; i++) {
            max = Math.max(memo[i], max);
        }

        System.out.print(max);

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