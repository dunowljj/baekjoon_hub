import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {

    static StringBuilder answer = new StringBuilder();
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        if (N > 20) {
            System.out.print(getMinMove(N));
        } else {
            move(N, 1, 2, 3);
            System.out.println(count);
            System.out.print(answer.toString().trim());
        }
    }

    private static void move(int n, int now, int another, int dest) {
        if (n == 1) {
            count++;
            answer.append(now).append(" ").append(dest).append(System.lineSeparator());
            return;
        }

        move(n - 1, now, dest, another);
        move(1, now, another, dest);
        move(n - 1, another, now, dest);
    }

    private static BigInteger getMinMove(int n) {
        if (n == 1) return BigInteger.valueOf(1);
        return getMinMove(n - 1)
                .multiply(BigInteger.valueOf(2))
                .add(BigInteger.valueOf(1));
    }
}
/**
 * 1) n-1개의 원판 2로 이동시키기
 *
 * 2) 맨 밑장이 될 원판 3으로 이동시키기
 *
 * 3) n-1개 원판 3으로 이동시키기
 * 현재 옮기려는 원판 개수가,
 * -짝수 -> 1에 처음 내려놓기
 * -홀수 -> 3에 처음 내려놓기
 *
 * 이동 횟수 ->  f(n) =  2 * f(n-1) + 1;
 */