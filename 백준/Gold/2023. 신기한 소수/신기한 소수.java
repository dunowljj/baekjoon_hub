import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    private static int N;
    private static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        find();

        System.out.print(answer.toString().trim());
    }

    private static void find() {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(2);
        queue.offer(3);
        queue.offer(5);
        queue.offer(7);

        int len = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {

                int now = queue.poll();

                if (len == N) {
                    answer.append(now).append(System.lineSeparator());
                    continue;
                }

                for (int j = 0; j <= 9; j++) {
                    int next = (now * 10) + j;

                    if (isPrime(next)) {
                        queue.offer(next);
                    }
                }
            }

            len++;
        }
    }

    private static boolean isPrime(int num) {
        for (int i = 2; i <= ((int) Math.sqrt(num)); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}

/**
 * 8자리 -> 10^8개
 *
 * -> 총 검사 경우의 수 10^9개 미만
 * -> 소수판별을 위해 10^9에 대해 10^4~5개만큼 검사를하면? 너무 많다.
 * -> 체를 쓰자니 10^8개는 4MB초과이다.
 *
 * 8자리의 조합을 만드는 bfs로 풀어보자.
 */