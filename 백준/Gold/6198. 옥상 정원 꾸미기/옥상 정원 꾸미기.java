import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long count = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < N; i++) {
            int height = Integer.parseInt(br.readLine());

            while (!stack.isEmpty() && stack.peek() <= height) {
                stack.pop();
            }

            stack.add(height);

            if (stack.size() > 1) {
                count += stack.size() - 1;
            }
        }

        System.out.print(count);
    }
}

/**
 * 인접한 같은 높이도 확인이 불가능한가? 문장이 중의적이라 헷갈림
 * 예시와 원문을 보니 오른쪽의 더 작은 경우만 볼 수 있음
 *
 *
 * 6 10 3 7 4 12 2
 *
 */