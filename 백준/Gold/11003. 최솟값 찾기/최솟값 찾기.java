import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        Deque<Num> dq = new LinkedList<>();

        st = new StringTokenizer(br.readLine());
        Num[] nums = new Num[N + 1];
        int[] mins = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            nums[i] = new Num(i, Integer.parseInt(st.nextToken()));
        }

        // min[1]은 i는 1일때 window 기준 최솟값 (1-L+1 ~ 1)
        dq.offerLast(nums[1]);
        mins[1] = nums[1].val;

        int lIndex = 1 - L + 1;
        int rIndex = 2;
        while (rIndex < mins.length) {

            // right 추가하면서, 더 큰 값 미리 제거
            Num right = nums[rIndex];
            while (!dq.isEmpty() && dq.peekLast().val >= right.val) {
                dq.pollLast();
            }
            dq.offerLast(right);

            if  (!dq.isEmpty() && dq.peekFirst().index <= lIndex) {
                dq.pollFirst();
            }

            mins[rIndex] = dq.peekFirst().val;
            lIndex++; rIndex++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < mins.length; i++) {
            sb.append(mins[i]).append(SPACE);
        }
        System.out.print(sb.toString().trim());
    }

    static class Num {
        int index;
        int val;

        public Num(int index, int val) {
            this.index = index;
            this.val = val;
        }
    }
}
/**
 * sliding하면서 미리 최솟값을 L크기로 구해놓으면 되지 않을까?
 * i+1-L,i+2-L, ... i+L-L
 */