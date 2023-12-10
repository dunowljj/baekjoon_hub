import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final int MAX_VALUE = 1_000_000_001;
    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        List<Integer> values = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int value = Integer.parseInt(st.nextToken());
            values.add(value);
        }

        Collections.sort(values);

        Result minDiffResult = binarySearch(0, values);
        for (int i = 1; i < values.size(); i++) {
            Result result = binarySearch(i, values);
            minDiffResult = getResultHasSmallerSum(minDiffResult, result);
        }

        System.out.print(minDiffResult);
    }

    private static Result binarySearch(int index, List<Integer> values) {
        int low = 0;
        int high = values.size() - 2; // 같은 원소를 제거하고 탐색할 것이다.
        int targetValue = values.get(index);

        while (low + 1 < high) {
            int mid = (low + high) / 2;

            // F F F F T T T T T
            // F F F F F T T T T
            // F F F F F F F F T
            int sum = targetValue + values.get(mid);
            if (sum >= 0) {
                high = mid;
            } else {
                low = mid;
            }
        }

        if (low == index) low --;
        if (high == index) high ++;

        if (low == -1) return new Result(targetValue, values.get(high));
        if (high == values.size()) return new Result(targetValue, values.get(low));

        Result lowResult = new Result(targetValue, values.get(low));
        Result highResult = new Result(targetValue, values.get(high));

        // 더 0에 가까운 좌표 반환, 같은 경우 아무거나
        if (lowResult.diffFromZero <= highResult.diffFromZero) {
            return lowResult;
        }

        if (low == index || lowResult.diffFromZero > highResult.diffFromZero) {
            return highResult;
        }

        return lowResult;
    }

    private static Result getResultHasSmallerSum(Result minDiffResult, Result result) {
        if (minDiffResult.diffFromZero < result.diffFromZero) {
            return minDiffResult;
        } else {
            return result;
        }
    }

    static class Result {
        int value1;
        int value2;
        int diffFromZero;

        public Result(int value1, int value2) {
            this.value1 = Math.min(value1, value2);
            this.value2 = Math.max(value1, value2);
            this.diffFromZero = Math.abs(value1 + value2);
        }

        @Override
        public String toString() {
            return value1 + SPACE + value2;
        }
    }
}


/**
 * 0에 가까운 용액 찾기
 * 0에 가장 가까운 용액을 만들어내는 두 용액을 출력. 특성값으로 오름차순 정렬
 * 두 개 이상일 경우 아무것이나 하나 출력
 * 모든 용액들의 특성값은 다르다.
 */