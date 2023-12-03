import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static final int MIN_CUT_HEIGHT = 0;
    private static final int MAX_CUT_HEIGHT = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        List<Integer> heights = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            heights.add(Integer.parseInt(st.nextToken()));
        }

        int low = MIN_CUT_HEIGHT;
        int high = MAX_CUT_HEIGHT;
        while (low + 1 < high) {

            int mid = (low + high) / 2;

            // T T T T T F F F F
            if (isEnough(mid, M, heights)) {
                low = mid;
            } else {
                high = mid;
            }
        }

        System.out.print(low);
    }

    private static boolean isEnough(int cutHeight, int M, List<Integer> heights) {
        long sum = 0;
        for (int height : heights) {
            sum += remainHeightAfterCut(cutHeight, height);
        }

        return sum >= M;
    }

    private static int remainHeightAfterCut(int cutHeight, int height) {
        int remain = height - cutHeight;

        if (remain < 0 ) return 0;
        else return remain;
    }
}
