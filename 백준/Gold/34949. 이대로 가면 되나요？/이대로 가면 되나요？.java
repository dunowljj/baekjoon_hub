import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;
    static int[] questionCounts;

    static List<Integer>[] reverseAdj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        boolean[] visited = new boolean[N + 1];
        questionCounts = new int[N + 1];
        Arrays.fill(questionCounts, -1);

        reverseAdj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            reverseAdj[i] = new ArrayList<>();
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int start = 1; start <= N; start++) {
            int toward = Integer.parseInt(st.nextToken());

            // 이대부터 다른 지점으로 탐색할 것이므로, 역방향 그래프 생성
            reverseAdj[toward].add(start);
        }


        Queue<Integer> q = new LinkedList<>();
        q.offer(N);
        visited[N] = true;

        int depth = 0; // depth == 질문의 수
        while (!q.isEmpty()) {

            depth++;
            int size = q.size();
            for (int i = 0; i < size; i++) {

                int now = q.poll();

                for (int next : reverseAdj[now]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        questionCounts[next] = depth;
                        q.offer(next);
                    }
                }
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 1; i < N; i++) {
            answer.append(questionCounts[i]).append(System.lineSeparator());
        }
        answer.append(0); // N번째는 이대이므로, 무조건 0이다.

        System.out.print(answer.toString().trim());
    }
}
/**
 * 이대는 N번에 존재.
 * 역방향 그래프를 만들어 BFS 사용하기. 질문의 수만 세면 되기 때문에, 가중치가 모두 1이다.
 */
