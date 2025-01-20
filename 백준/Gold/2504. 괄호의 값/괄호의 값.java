import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stack<Character> bracket = new Stack<>();
        Stack<Result> result = new Stack<>();

        char[] input = br.readLine().toCharArray();
        int n = input.length;

        if (n == 1 || isClose(input[0])) exit();

        bracket.push(input[0]); // i-1 사용을 위해 넣고 시작

        for (int i = 1; i < n; i++) {
            char nowBracket = input[i];
            char beforeBracket = input[i - 1];

            if (isOpen(nowBracket)) {
                bracket.push(nowBracket);
                continue;
            }

            if (isClose(nowBracket)) {
                if (bracket.isEmpty() || !isOpenClosePair(bracket.peek(), nowBracket)) exit();

                bracket.pop();

                int nowBracketValue = valueOf(nowBracket);
                int nowDepth = bracket.size();

                if (isOpenClosePair(beforeBracket, nowBracket)) {
                    result.push(new Result(nowDepth, nowBracketValue));
                    continue;
                }

                // 닫는 괄호이며, Result에 (nowDepth + 1인)하위 괄호들이 존재할때
                // 닫으면서 하위 괄호들을 전체에 value를 곱해야 한다.
                int sum = 0;
                while (!result.isEmpty() && result.peek().depth == nowDepth + 1) {
                    sum += result.pop().value;
                }

                if (sum == 0) continue;
                result.push(new Result(nowDepth, sum * nowBracketValue));
            }
        }

        if (!bracket.isEmpty()) {
            exit();
        } else {
            int answer = 0;

            while (!result.isEmpty()) {
                answer += result.pop().value;
            }

            System.out.print(answer);
        }
    }

    private static boolean isOpen(char ch) {
        return ch == '(' || ch == '[';
    }

    private static boolean isClose(char ch) {
        return ch == ')' || ch == ']';
    }

    private static int valueOf(char nowBracket) {
        if (nowBracket == '(' || nowBracket == ')') return 2;
        if (nowBracket == '[' || nowBracket == ']') return 3;

        return 0;
    }
    // 앞 뒤 순서가 정확해야함

    private static boolean isOpenClosePair(char openBracket, char closeBracket) {
        if (openBracket == '(') return closeBracket == ')';
        if (openBracket == '[') return closeBracket == ']';
        return false;
    }

    private static void exit() {
        System.out.print("0");
        System.exit(0);
    }

    static class Result {
        int depth;
        int value;

        public Result(int depth, int value) {
            this.depth = depth;
            this.value = value;
        }
    }
}

/**
 * - 맨 안 괄호부터 계산하기
 * -> 스택을 사용해서 맨 안의 괄호부터 계산 가능
 *
 * - 같은 깊이에서 괄호를 시작한다면, 값을 합산해줘야하며, 해당 깊이를 닫을때 전체에서 곱해서 계산해야한다.
 * -> 깊이는 앞서 열린 괄호의 개수로 스택의 사이즈로 측정 가능
 *
 * - 괄호를 닫을때마다 결과값을 깊이와 함께 자료구조에 저장한다. 자료구조에서 해당하는 깊이를 전부뽑아 합한 후, 곱한다.
 *
 *
 * 여는 괄호에선 추가만하고, 닫는 괄호에서 처리한다.
 *
 * ### 닫는 괄호 처리
 * 
 * 1 가장 작은 괄호가 주어지는 경우 ex. ()
 * -> Result에 2 추가
 *
 * 2 괄호 스택이 비어있는 경우 (), ()()
 *  -> 1에 해당함
 *
 * 3 닫으면서 depth가 하나 낮은 괄호들의 합을 곱해야하는 경우 ex. (()), (() ()), (() (()) )
 * -> Result에 하나 깊은 depth 모두 뽑아서 합한 후, 곱한다.
 * -> 현재 depth로 해당 값을 다시 Result에 넣는다.
 *
 * 
 * ### 추가
 * - 덜 닫힌 괄호가 남았을 경우를 고려해 스택에 괄호가 남았는지 확인해야 한다.
 * - Result에 있는 값들을 모두 더하면 끝
 */