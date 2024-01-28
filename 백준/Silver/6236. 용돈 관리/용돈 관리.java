import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int goalOfCount = Integer.parseInt(st.nextToken());

        int[] use = new int[N];
        int maxUse = 0;
        for (int day = 0; day < N; day++) {
            int willUse = Integer.parseInt(br.readLine());
            use[day] = willUse;
            maxUse = Math.max(maxUse, willUse);
        }

        // 1 T T T F F F n
        // - 인출 금액 커짐
        // - 인출 횟수 작아짐
        // 인출 횟수가 점점 작아지는 흐름. 작은 인출 횟수는 커버가 된다.
        int lo = maxUse - 1;
        int hi = 1_000_000_001;
        while (lo + 1 < hi) {
            int mid = (lo + hi) / 2;

            // 목표 횟수보다 더 많거나 같은 회수를 인출하는 경우
            // 인출 금액이 더 큰(횟수가 더 적어지는) 경우를 탐색해야 한다.
            if (goalOfCount < getWithdrawalCount(use, mid)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        System.out.print(hi);
    }

    private static int getWithdrawalCount(int[] use, int withdrawAmount) {
        int ownMoney = withdrawAmount;
        int withdrawalCount = 1; // 먼저 출금하고 시작

        for (int willUsed : use) {
            // 소지금으로 당일 충당 불가능 -> 남은 돈 입금, 새로 출금 후 돈을 사용한다.
            if (ownMoney < willUsed) {
                withdrawalCount ++;
                ownMoney = withdrawAmount - willUsed;

            // 소지금으로 당일 충당 가능
            } else {
                ownMoney -= willUsed;
            }
        }

        return withdrawalCount;
    }
}
/**
 *  현우는 M이라는 숫자를 좋아하기 때문에,
 *  정확히 M번을 맞추기 위해서 남은 금액이 그날 사용할 금액보다 많더라도
 *  남은 금액은 통장에 집어넣고 다시 K원을 인출할 수 있다.
 *  인출 회수가 목표 회수보다 처음으로 같거나 작은 경우를 구하면 된다
 */