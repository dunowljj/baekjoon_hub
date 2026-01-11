import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";
    static List<Integer>[] adj;
    static int[] visited;
    static int stamp = 1;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
        }

        visited = new int[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a->b 신뢰 => b해킹 시 a까지
            adj[b].add(a);
        }

        int max = 0;
        int[] count = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            count[i] = bfs(i);
            max = Math.max(max, count[i]);
            stamp++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count.length; i++) {
            if (count[i] == max) {
                sb.append(i).append(SPACE);
            }
        }

        System.out.print(sb.toString().trim());
    }

    private static int bfs(int start) {
        visited[start] = stamp;
        int count = 1;

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int now = queue.poll();

            for (int next : adj[now]) {
                if (visited[next] == stamp) continue;
                visited[next] = stamp;

                queue.offer(next);
                count++;
            }
        }

        return count;
    }
}
/**
 * 연결된 그래프에서, 각 그룹을 카운팅하기
 *
 * s1. 그냥 각 탐색마다 방문체크하며 cnt배열에 개수 세기 (o)
 * s2. in 개수를 미리 세고, in이 0인 컴퓨터만 탐색 시작점으로 삼기
 * -> 시작점인데 in이 0인 경우가 없을 수 있다.
 *
 * s3. memo를 활용해서 탐색한 값은 저장해놓기.
 * -> s3 사용 시, 사이클 있는 경우를 어떻게 대비 -> 방문체크
 * -> 방문 체크로 사이클 방지 시, 사이클 부분의 특정 노드의 dp 값이 틀릴 수 있음
 *
 * 시간초과 -> 정적 배열, 배열 사용 및 접근 줄이기
 * -> 안됨. dfs에서 bfs로 변경
 */
