import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stack<Character> bracket = new Stack<>();
        Stack<Result> result = new Stack<>();

        char[] input = br.readLine().toCharArray();
        int n = input.length;

        if (n == 1 || isCloseBracket(input[0])) impossible();
        bracket.push(input[0]); // i-1 사용을 위해 넣고 시작

        for (int i = 1; i < n; i++) {
//
//            System.out.println("result");
//            for (Result r : result) {
//                System.out.print(r.value + " ");
//            }
//            System.out.println("---");
//            System.out.println();
//
//            System.out.println("bracket");
//            for (Character b : bracket) {
//                System.out.print(b + " ");
//            }
//
//            System.out.println("---");
//            System.out.println();
//            System.out.println(":::::");


            if (isOpenBracket(input[i])) {
                bracket.push(input[i]);
                continue;
            }

            if (input[i] == ')') {
                if (bracket.isEmpty() || bracket.peek() != '(') impossible();

                bracket.pop();

                // 1 맨 처음 결과값이 없다면 새로 추가 ex. ( + ),  (( + )
                // 새로 시작하는 값이기 때문에 추가해줘야함. (), [] 와 같은경우 해당 값 바로추가

                // 더 깊지 않은 경우도 새로 시작하는 경우가 있음. 해당 케이스를 거를 수 있어야한다.
                // 닫을때 같은 깊이인 경우도 새로 추가될 수 있다. ( ( (())( + ) ~~ )())
                if (result.isEmpty()) {
                    result.push(new Result(bracket.size(), 2));
//                    System.out.println("empty || <");
                    continue;
                }

                if (input[i - 1] == '(') {
                    result.push(new Result(bracket.size(), 2));
                }

                // ( () )
                // ( () () )
                // -> 결국 하위 결과를 모두빼서 * 해주면 된다.
                // -> 하위 결과가 없으면 2 혹은 3 그냥 추가하면 된다.
                //

                // 결과를 저장할때 괄호의 개수보다 현재 닫을때 괄호의 개수가 1개 더 적다.  ex. (() () + ),  (() + )
                // 이번에 닫으면서 바로 하위 괄호들을 종합해야하는 경우
                if (result.peek().depth - 1 == bracket.size()) {
//                    System.out.println("이전 값에 곱하기");

                    result.push(new Result(bracket.size(), result.pop().value * 2));
                    // 종합한 후에 바로 합산이 필요할 수 있으므로 continue하지 않음
                }


                // 결과를 저장하던 깊이와 현재 닫으려는 괄호의 깊이가 같다.
                // 모두 합산해서 나중에 한꺼번에 곱해줘야하므로, 미리 Result에 합산해서 다시 넣어준다.
                if (result.peek().depth == bracket.size()) {
                    int sameDepthSum = 0;

                    while (!result.isEmpty() && result.peek().depth == bracket.size()) {
                        sameDepthSum += result.pop().value;
                    }
                    result.push(new Result(bracket.size(), sameDepthSum));
//                    System.out.println("같은 깊이 합치기");
                }
            }

            if (input[i] == ']') {
                if (bracket.isEmpty() || bracket.peek() != '[') impossible();

                bracket.pop();

                if (result.isEmpty()) {
                    result.push(new Result(bracket.size(), 3));
//                    System.out.println("empty || <");
                    continue;
                }

                if (input[i - 1] == '[') {
                    result.push(new Result(bracket.size(), 3));
                }

                if (result.peek().depth - 1 == bracket.size()) {
//                    System.out.println("이전 값에 곱하기");

                    result.push(new Result(bracket.size(), result.pop().value * 3));
                }

                if (result.peek().depth == bracket.size()) {
                    int sameDepthSum = 0;

                    while (!result.isEmpty() && result.peek().depth == bracket.size()) {
                        sameDepthSum += result.pop().value;
                    }
                    result.push(new Result(bracket.size(), sameDepthSum));
//                    System.out.println("같은 깊이 합치기");
                }
            }
        }

        if (!bracket.isEmpty()) {
            impossible();
        } else {
            int answer = 0;

            while (!result.isEmpty()) {
                answer += result.pop().value;
            }

            System.out.print(answer);
        }
    }

    private static void impossible() {
        System.out.print("0");
        System.exit(0);
    }

    private static boolean isOpenBracket(char ch) {
        return ch == '(' || ch == '[';
    }

    private static boolean isCloseBracket(char ch) {
        return ch == ')' || ch == ']';
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
 *( () [[]] )
 *
 * - 맨 안 괄호부터 계산하기
 * -> 스택을 사용해서 맨 안부터 계산 가능
 *
 * - 같은 깊이에서 괄호를 시작한다면, 값을 합산해줘야하며, 해당 깊이를 닫을때 전체에서 곱해서 계산해야한다.
 * -> 깊이는 앞서 열린 괄호의 개수로 스택의 사이즈로 측정 가능
 *
 * - 괄호를 닫을때마다 결과값을 깊이와 함께 자료구조에 저장한다. 자료구조에서 해당하는 깊이를 전부뽑아 합한 후, 곱한다.
 *
 *
 *
 *  괄호를 닫을때
 *  1 Result에 아무값이 없다면 해당하는 괄호값을 Result에 push
 *  2 Result의 깊이가 낮다면 새로 값을 추가해야한다. 예시로, '(()((' 에 ')'를 더하는 경우, Result에 값이 있으나 새로 값을 추가해줘야한다.
 *  3 Result에 현재 삽입하려는 깊이보다 하나 깊은 깊이에 값이 있다면, 4에서 미리 합쳐논 값이다. 해당 값을 꺼내 곱하고, 더 낮은 깊이의 연산을 대비해 다시 Result에 넣는다.
 *  4 Result에 같은 깊이의 괄호 값이 있다면, 곱할때를 대비해 미리 합쳐서 다시 Result에 push
 *
 *
 *  ( ( (()) () ) ())
 */