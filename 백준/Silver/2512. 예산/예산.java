import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] budgets = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int max = 0;
        for (int i = 0; i < N; i++) {
            int budget = Integer.parseInt(st.nextToken());
            budgets[i] = budget;

            max = Math.max(budget, max);
        }

        int totalBudget = Integer.parseInt(br.readLine());

        int lo = 0;
        int hi = max + 1;

        // T T T F F
        while (lo + 1 < hi) {
            int mid = (lo + hi) / 2;

            if (canAllocate(budgets, mid, totalBudget)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        System.out.print(lo);
    }

    private static boolean canAllocate(int[] budgets, int upperBound, int totalBudget) {
        int sum = 0;
        for (int budget : budgets) {
            sum += Math.min(budget, upperBound);
        }

        return totalBudget >= sum;
    }
}
