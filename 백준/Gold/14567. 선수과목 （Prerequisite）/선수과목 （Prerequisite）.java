import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";
    static List<Integer>[] adj;
    static int[] pre;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        pre = new int[N + 1];
        adj = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a가 선수과목
            // a->b
            pre[b]++;
            adj[a].add(b);
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int no = 1; no <= N; no++) {
            if (pre[no] == 0) {
                pre[no] = -1;
                queue.offer(no);
            }
        }

        int semester = 1;
        int[] answer = new int[N + 1];
        while (!queue.isEmpty()) {

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int now = queue.poll();
                answer[now] = semester;

                for (int next : adj[now]) {
                    pre[next]--;
                }
            }

            for (int no = 1; no <= N; no++) {
                if (pre[no] == 0) {
                    pre[no] = -1;
                    queue.offer(no);
                }
            }

            semester++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(answer[i]).append(SPACE);
        }
        System.out.print(sb.toString().trim());
    }
}
/**
 * in 0인 과목 큐에 넣기
 * 제거 하고, in 배열 갱신
 * 각 학기에 대해 계산해야함.
 */