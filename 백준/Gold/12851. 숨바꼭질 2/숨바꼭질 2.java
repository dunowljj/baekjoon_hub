import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class Main {

    private static int N;
    private static int K;

    private static Integer[] wayDp;
    private static Integer[] time;

    private static  Queue<Integer> queue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if (K <= N) {
            System.out.println((N - K)+"\n1");
            System.exit(0);
        }

        int max = K * 2 + 1;

        wayDp = new Integer[max];
        time = new Integer[max];

        wayDp[N] = 1;
        time[N] = 0;
        int nowTime = 0;

        queue = new LinkedList<>();
        queue.offer(N);

        while (!queue.isEmpty()) {
            int len = queue.size();

            for (int i = 0; i < len; i++) {
                int now = queue.poll();

                if (now == K) {
                    System.out.print(time[K] + "\n" + wayDp[K]);
                    System.exit(0);
                }

                dp(now, nowTime, (n) -> n * 2, (nowLoc, next) -> (nowLoc != 0 && next < max));
                dp(now, nowTime, (n) -> n + 1, (nowLoc, next) -> (next <= K));
                dp(now, nowTime, (n) -> n - 1, (nowLoc, next) -> (0 <= next));
            }

            nowTime++;
        }

        System.out.print(time[K] + "\n" + wayDp[K]);
    }

    private static void dp(int now, int nowTime, Function<Integer, Integer> moveStrategy, BiPredicate<Integer, Integer> canMove) {
        int next = moveStrategy.apply(now);

        if (!canMove.test(now, next)) {
            return;
        }

        if (time[next] == null) {
            time[next] = nowTime + 1;
            wayDp[next] = wayDp[now];
            queue.offer(next);
            return;
        }

        // 이미 방문한 곳인데, 동시에 도착 -> 아미 큐에 값이 있으므로 중복해서 넣지 않는다.
        if (time[next] == nowTime + 1) {
            wayDp[next] += wayDp[now];
            return;
        }
    }
}
/**
 * 순간이동은 X값 기준이므로 후진이 불가능하다. 동생이 더 앞에있다면 차를 구하면 된다.
 */
