import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int H = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        Stack<Integer> stack = new Stack<>();

        int maxHeight = 0;
        int answer = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < W; i++) {
            int nowHeight = Integer.parseInt(st.nextToken());

            if (i == 0) {
                maxHeight = nowHeight;
                continue;
            }

            if (nowHeight < maxHeight) {
                stack.push(nowHeight);
            }

            if (nowHeight >= maxHeight) {

                // 빗물 계산
                while (!stack.isEmpty()) {
                    answer += maxHeight - stack.pop();
                }

                maxHeight = Math.max(maxHeight, nowHeight);
            }
        }

        // 마지막에 maxHeight 갱신이 안된경우
        // MaxHeight보다 작은 값들만 남아있다. 오른쪽(뒤쪽)부터 탐색한다.
        int rightMaxHeight = 0;
        while (stack.size() >= 2) {

            int pop = stack.pop();
            rightMaxHeight = Math.max(rightMaxHeight, pop);

            int peek = stack.peek();

            if (peek < rightMaxHeight) {
                answer += rightMaxHeight - stack.peek();
            }
        }

        System.out.print(answer);
    }
}
/**
 * maxHeight를 유지
 *
 * 개선필요
 */
