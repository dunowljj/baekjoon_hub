import javax.sound.sampled.Line;
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
        int M = Integer.parseInt(st.nextToken());

        List<Integer>[] adj = new ArrayList[N + 1];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }
        int[] indegree = new int[N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            adj[A].add(B);
            indegree[B]++;
        }

        StringBuilder line = new StringBuilder();
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 1; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int now = queue.poll();

            line.append(now).append(SPACE);

            for (int next : adj[now]) {
                indegree[next]--;
                if (indegree[next] == 0) queue.offer(next);
            }
        }

        System.out.print(line.toString().trim());
    }
}

/**
 *  A, B
 *  A가 앞에 서야 한다.
 *
 *  단방향 그래프 형태로 보았을때, 모순되게 싸이클이 존재할 수 없다.
 *  A->B로 그래프를 생성하고, 진입 차수가 0인 학생들을 먼저 줄 세우면 된다.
 */