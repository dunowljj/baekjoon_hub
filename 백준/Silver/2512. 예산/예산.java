import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 3 ~ 10,000

        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> budgetRequests = new ArrayList<>();
        int requestSum = 0;
        for (int i = 0; i < N; i++) {
            int budgetRequest = Integer.parseInt(st.nextToken());
            budgetRequests.add(budgetRequest);
            requestSum += budgetRequest;
        }

        int totalBudget = Integer.parseInt(br.readLine());

        int answer = 0;

        if (totalBudget >= requestSum) {
           answer = getMax(budgetRequests);
        } else {
            answer =  binarySearch(budgetRequests, totalBudget);
        }

        System.out.print(answer);
    }

    private static int getMax(List<Integer> budgetRequests) {
        int max = 0;
        for (Integer budgetRequest : budgetRequests) {
            max = Math.max(max, budgetRequest);
        }

        return max;
    }

    private static int binarySearch(List<Integer> budgetRequests, int totalBudget) {
        // T T T T T F F F F
        int low = 1;
        int high = 100_000;

        while (low + 1 < high) {

            int mid = (low + high) / 2;

            if (canAssign(mid, budgetRequests, totalBudget)) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return low;
    }

    private static boolean canAssign(int upperBound, List<Integer> budgetRequests, int totalBudget) {
        int sum = 0;
        for (Integer budgetRequest : budgetRequests) {
            sum += Math.min(budgetRequest, upperBound);
        }

        return totalBudget >= sum;
    }
}
