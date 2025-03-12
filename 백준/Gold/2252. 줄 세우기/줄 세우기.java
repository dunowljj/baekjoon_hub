import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
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

        List<Integer> line = new ArrayList<>();

        while (N > 0) {
            List<Integer> remove = new ArrayList<>();
            for (int i = 1; i < indegree.length; i++) {
                if (indegree[i] == 0) {
                    indegree[i] = -1;
                    remove.add(i);
                }
            }

            for (int node : remove) {
                N--;
                removeConnection(adj, indegree, node);
            }
            line.addAll(remove);
        }


        StringBuilder sb = new StringBuilder();
        line.forEach((ch) -> sb.append(ch).append(" "));
        System.out.print(sb.toString().trim());
    }

    private static void removeConnection(List<Integer>[] adj, int[] indegree, int i) {
        for (int next : adj[i]) {
            indegree[next]--;
        }
    }
}

/**
 *  A, B
 *  A가 앞에 서야 한다.
 *
 *  단방향 그래프 형태로 보았을때, 모순되게 싸이클이 존재할 수 없다.
 *  A->B로 그래프를 생성하고, 진입 차수가 0인 학생들을 먼저 줄 세우면 된다.
 */