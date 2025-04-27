import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {

    private static int N;
    private static BigInteger K;

    private static BigInteger[] counts;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = new BigInteger(st.nextToken());

        if (N == 1) {
            System.out.print("1 3");
            return;
        }

        counts = new BigInteger[N + 1];
        memoMove(N);

        // 0 ~ counts[N-1], counts[N-1], count[N-1]+1 ~
        System.out.print(findMove(N, BigInteger.ONE, 1, 2, 3));
    }

    private static String findMove(int height, BigInteger start, int now, int another, int dest) {
        if (height == 1) {
            return now + " " + dest;
        }

        BigInteger count = counts[height - 1];
        BigInteger mid = start.add(count);

        if (K.compareTo(mid) < 0) {
            return findMove(height - 1, start, now, dest, another);
        } else if (K.compareTo(mid) == 0) {
            return now + " " + dest;
        } else if (mid.compareTo(K) < 0){
            return findMove(height - 1, mid.add(BigInteger.ONE), another, now, dest);
        }

        return "";
    }

    private static BigInteger memoMove(int h) {
        if (h == 1) {
            return counts[h] = BigInteger.ONE;
        }

        BigInteger move = memoMove(h - 1)
                .multiply(BigInteger.TWO)
                .add(BigInteger.ONE);

        return counts[h] = move;
    }
}

/**
 * 최적의 루트로 간다고 해도, 원판이 60개면 2^60이상 된다.
 * K의 크기는 안정해져있고, 이동가능 횟수임만 명시됨
 *
 * 원판 개수와, 이동 횟수를 통해서 어느 구간에 있는지 확인이 가능할까?
 * f(x) = 2f(x - 1) + 1
 *
 */
