import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        List<Integer> minus = new ArrayList<>();
        List<Integer> plus = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int loc = Integer.parseInt(st.nextToken());

            if (loc > 0) plus.add(loc);
            else minus.add((loc * -1));
        }

        Collections.sort(plus, Comparator.reverseOrder());
        Collections.sort(minus, Comparator.reverseOrder());

        int step = 0;
        int idx = 0;
        while (idx < minus.size()) {
            step += (2 * minus.get(idx));
            idx += M;
        }

        idx = 0;
        while (idx < plus.size()) {
            step += (2 * plus.get(idx));
            idx += M;
        }

        idx = 0;
        int maxAbs = 0;
        if (minus.size() == 0) maxAbs = plus.get(0);
        else if (plus.size() == 0) maxAbs = minus.get(0);
        else maxAbs = Math.max(plus.get(0), minus.get(0));


        System.out.print(step - maxAbs);
    }
}
/**
 * "책을 모두 제자리에 놔둔 후에는 다시 0으로 돌아올 필요는 없다."
 * - 구간, +구간 중 먼곳을 나중에 가면 유리하다.
 */