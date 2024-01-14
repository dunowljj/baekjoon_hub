import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {

    private static List<Example> examples;
    private static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        examples = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String balls = st.nextToken();
            int strike = Integer.parseInt(st.nextToken());
            int ball = Integer.parseInt(st.nextToken());

            examples.add(new Example(balls, strike, ball));

            if (strike == 3) {
                examples.clear();
            }
        }

        makeAllPatterns(0, new int[3], new boolean[10]);

        System.out.print(count);
    }

    private static void makeAllPatterns(int depth, int[] numbers, boolean[] visited) {
        if (depth == 3) {
            if (matchAll(numbers)) {
                count++;
            }

            return;
        }

        for (int num = 1; num <= 9; num++) {
            if (!visited[num]) {
                numbers[depth] = num;
                visited[num] = true;
                makeAllPatterns(depth + 1, numbers, visited);
                visited[num] = false;
            }
        }
    }

    private static boolean matchAll(int[] numbers) {
        for (Example example : examples) {
            if (!example.isMatched(numbers)) {
                return false;
            }
        }

        return true;
    }

    static class Example {
        List<Integer> balls;
        int strike;
        int ball;

        public Example(String balls, int strike, int ball) {
            this.balls = parse(balls);
            this.strike = strike;
            this.ball = ball;
        }

        private List<Integer> parse(String numbers) {
            return numbers.chars()
                    .mapToObj((c) -> c - '0')
                    .collect(Collectors.toList());
        }

        public boolean isMatched(int[] balls) {
            int strike = 0;
            int ball = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == j && this.balls.get(i) == balls[j]) {
                        strike++;
                        continue;
                    }

                    if (i != j && this.balls.get(i) == balls[j]) {
                        ball++;
                        continue;
                    }
                }
            }

            return this.strike == strike && this.ball == ball;
        }
    }
}
/**
 * 모든 경우의 수를 나누긴 너무 복잡할 거 같다.
 * 10^3가지 모든 조합을 만들고,
 * 최대 100가지 주어지는 질문사항을 각각 테스트해보자
 *
 *
 * ### 조건!!!!
 * 민혁이가 영수의 세 자리 수를 정확하게 맞추어 3 스트라이크가 되면 게임이 끝난다.
 * 아니라면 민혁이는 새로운 수를 생각해 다시 영수에게 묻는다.
 *
 * 단순히 3스트라이크만 체크하는게 아닌 한번 한번마다 검사하고 확인해야할듯? 아니 상관없을지도... 어짜피 그때그때 체크하나 한번에 하나 같다.
 * 답을 알면 답을 말할거니까.
 */