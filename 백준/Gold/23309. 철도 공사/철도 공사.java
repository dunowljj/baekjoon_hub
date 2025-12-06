import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final int CLOSED = 0;

    static int N, M;
    static int[] pre = new int[1_000_001];
    static int[] next = new int[1_000_001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int[] serialNos = new int[N];
        for (int i = 0; i < N; i++) {
            int serialNo = Integer.parseInt(st.nextToken());
            serialNos[i] = serialNo;
        }

        for (int i = 0; i < N; i++) {
            int preNo = serialNos[(N + i - 1) % N];
            int nowNo = serialNos[i];
            int nextNo = serialNos[(N + i + 1) % N];

            pre[nowNo] = preNo;
            pre[nextNo] = nowNo;

            next[preNo] = nowNo;
            next[nowNo] = nextNo;
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String command = st.nextToken();

            if (command.startsWith("B")) {
                int targetNo = Integer.parseInt(st.nextToken());
                int newNo = Integer.parseInt(st.nextToken());

                if (command.equals("BP")) {
                    int preNo = pre[targetNo];

                    // pre -> new -> target
                    next[preNo] = newNo;
                    pre[targetNo] = newNo;

                    pre[newNo] = preNo;
                    next[newNo] = targetNo;

                    answer.append(preNo).append(LINE_SEPARATOR);

                } else if (command.equals("BN")) {
                    int nextNo = next[targetNo];

                    // target -> new -> next
                    next[targetNo] = newNo;
                    pre[nextNo] = newNo;

                    pre[newNo] = targetNo;
                    next[newNo] = nextNo;

                    answer.append(nextNo).append(LINE_SEPARATOR);
                }

            } else if (command.startsWith("C")) {
                int targetNo = Integer.parseInt(st.nextToken());

                if (command.equals("CP")) {
                    int p2 = pre[pre[targetNo]];
                    int p1 = pre[targetNo];

                    // before: p2 -> p1 -> target
                    // after:  p2 -> target
                    pre[targetNo] = p2;
                    next[p2] = targetNo;

                    pre[p1] = CLOSED;
                    next[p1] = CLOSED;

                    answer.append(p1).append(LINE_SEPARATOR);

                } else if (command.equals("CN")) {
                    int n1 = next[targetNo];
                    int n2 = next[next[targetNo]];

                    // before: target -> n1 -> n2
                    // after:  target -> n2
                    next[targetNo] = n2;
                    pre[n2] = targetNo;

                    pre[n1] = CLOSED;
                    next[n1] = CLOSED;

                    answer.append(n1).append(LINE_SEPARATOR);
                }
            }
        }

        System.out.print(answer.toString().trim());
    }
}
/**
 * 2개 이상 역일때만 폐쇄가 가능하다.
 * 설립된 역을 스킵해야 하는가?
 * Map기반 로직 시 시간초과 발생. 잘못된 구현 혹은 GC 문제
 */