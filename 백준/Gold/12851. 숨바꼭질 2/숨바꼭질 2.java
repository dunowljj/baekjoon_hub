import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int N;
    private static int K;

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

        Integer[] wayDp = new Integer[max]; // 방법의 수를 저장 -> 횟수가 더 적다면, 방법의 수를 갱신 ;; 횟수가 같다면 방법의 수를 더한다
        Integer[] time = new Integer[max];

        wayDp[N] = 1;
        time[N] = 0;
        int nowTime = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(N);

        while (!queue.isEmpty()) {
            int len = queue.size();

            for (int i = 0; i < len; i++) {
                int now = queue.poll();

                if (now == K) {
                    System.out.print(time[K] + "\n" + wayDp[K]);
                    System.exit(0);
                }

                int next = now * 2;
                if (now != 0 && next < max) {

                    if (time[next] == null) {
                        time[next] = nowTime + 1;
                        wayDp[next] = wayDp[now];
                        queue.offer(next);
                    }

                    // 이미 방문한 곳인데, 동시에 도착 -> 아미 큐에 값이 있으므로 중복해서 넣지 않는다.
                    else if (time[next] == nowTime + 1) {
                        wayDp[next] += wayDp[now];
                    }
                }

                next = now + 1;
                if (next <= K) {

                    if (time[next] == null) {
                        time[next] = nowTime + 1;
                        wayDp[next] = wayDp[now];
                        queue.offer(next);
                    }

                    else if (time[next] == nowTime + 1) {
                        wayDp[next] += wayDp[now];
                    }
                }

                next = now - 1;
                if (0 <= next) {
                    if (time[next] == null) {
                        time[next] = nowTime + 1;
                        wayDp[next] = wayDp[now];
                        queue.offer(next);
                    }

                    else if (time[next] == nowTime + 1) {
                        wayDp[next] += wayDp[now];
                    }
                }
            }

            nowTime++;
        }

        System.out.print(time[K] + "\n" + wayDp[K]);
    }
}
/**
 * 순간이동은 X값 기준이므로 후진이 불가능하다. 동생이 더 앞에있다면 차를 구하면 된다.
 *
 */