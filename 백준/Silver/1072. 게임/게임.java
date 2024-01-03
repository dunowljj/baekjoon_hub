import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int MAX = 1_000_000_000;
    public static final String NEVER_CHANGED = "-1";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        int Z = getWinningRate(X, Y);

        if (X == 0) {
            System.out.print("1");
            System.exit(0);
        }

        if (Z >= 99) {
            System.out.print("-1");
            System.exit(0);
        }

        int low = 0;
        int high = MAX;
        int mid = 0;

        // F F T T T
        // 변하는 가장 첫 값
        while (low + 1 < high) {
            mid = (low + high) / 2;

            if (isChanged(X, Y, Z, mid)) {
                high = mid;
            } else {
                low = mid;
            }
        }

        System.out.print(high);
    }

    private static boolean isChanged(int X, int Y, int Z, int mid) {
        return getWinningRate(X + mid, Y + mid) != Z;
    }

    private static int  getWinningRate(int x, int y) {
        return (int) ((long) (y) * 100 / (x));
    }
}

/**
 * 게임 횟수는 최대 10억판
 *
 * 99%, 100%인 경우 아무리 해도 안오를듯.
 */