import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static final String SPACE = " ";
    private static int N;
    private static int K;

    private static Queue<Integer> queue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if (K <= N) {
            StringBuilder sb = new StringBuilder();

            sb.append((N - K)).append(System.lineSeparator());
            for (int i = N; i >= K; i--) {
                sb.append(i).append(SPACE);
            }

            System.out.print(sb.toString().trim());
            System.exit(0);
        }

        int max = K * 2 + 1;

        queue = new LinkedList<>();
        queue.offer(N);

        Integer[] parent = new Integer[max];
        parent[N] = -1;

        int nowTime = 0;
        while (!queue.isEmpty()) {

            int len = queue.size();

            for (int i = 0; i < len; i++) {
                int nowLoc = queue.poll();

                if (nowLoc == K) {
                    printAnswer(nowTime, nowLoc, parent);
                    System.exit(0);
                }

                int nextLoc = nowLoc * 2;
                if (nextLoc != 0 && nextLoc < max && parent[nextLoc] == null) {
                    parent[nextLoc] = nowLoc;
                    queue.offer(nextLoc);
                }

                nextLoc = nowLoc + 1;
                if (nextLoc <= K && parent[nextLoc] == null) {
                    parent[nextLoc] = nowLoc;
                    queue.offer(nextLoc);
                }

                nextLoc = nowLoc - 1;
                if (nextLoc >= 0 && parent[nextLoc] == null) {
                    parent[nextLoc] = nowLoc;
                    queue.offer(nextLoc);
                }
            }

            nowTime++;
        }
    }

    private static void printAnswer(int nowTime, int nowLoc, Integer[] parent) {
        StringBuilder sb = new StringBuilder();
        Stack<Integer> trace = new Stack<>();

        // 시작점까지 경로 추적
        while (nowLoc != N) {
            trace.push(nowLoc);
            nowLoc = parent[nowLoc];
        }
        trace.push(N);

        sb.append(nowTime).append(System.lineSeparator());

        while (!trace.isEmpty()) {
            int t = trace.pop();
            sb.append(t).append(SPACE);
        }

        System.out.print(sb.toString().trim());
    }
}

/**
 * 10만까지 2의 배수로 가려면?  2^17 = 131,072
 *
 * 3^17 = 129_149_163
 *
 *
 */