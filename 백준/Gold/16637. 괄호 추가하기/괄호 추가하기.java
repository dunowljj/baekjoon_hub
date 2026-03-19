import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] nums;
    static char[] operators;
    static int max = Integer.MIN_VALUE;
    static int N;
    static int operandCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        String line = br.readLine();

        if (N == 1) {
            System.out.print(Integer.parseInt(line));
            return;
        }

        operandCount = N / 2 + 1;
        nums = new int[operandCount];
        operators = new char[operandCount];
        int ni = 0;
        int oi = 0;
        operators[oi++] = '+';

        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) nums[ni++] = line.charAt(i) - '0';
            if (i % 2 == 1) operators[oi++] = line.charAt(i);
        }

        findMax(1, nums[0]);

        System.out.print(max);
    }

    private static void findMax(int idx, int result) {
        if (idx == operandCount) {
            max = Math.max(result, max);
            return;
        }

        // 순차 계산
        findMax(idx + 1, operate(result, nums[idx], operators[idx]));

        // 뒤에 괄호 생성 후 계산
        if (idx + 2 <= operandCount) {
            int bracketResult = operate(nums[idx], nums[idx + 1], operators[idx + 1]);
            findMax(idx + 2, operate(result, bracketResult, operators[idx]));
        }
    }

    private static int operate(int operand1, int operand2, char operator) {
        if (operator == '*') {
            return operand1 * operand2;
        }

        if (operator == '+') {
            return operand1 + operand2;
        }

        if (operator == '-') {
            return operand1 - operand2;
        }

        throw new IllegalArgumentException();
    }
}
/**
 * 연산자 우선순위 동일. 연산 순서 괄호만 상관있음.
 *
 * 중첩 괄호 불가. N==19이면 9개 연산자.-> 5개 괄호 가능
 *
 * - 이전에 괄호 사용 여부 파악
 * - 했다면 이번에 괄호 불가, 아니면 가능
 * - 최댓값 계산
 */


