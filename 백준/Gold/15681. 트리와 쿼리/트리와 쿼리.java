import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static List<Integer>[] adj;
    static int counts[];
    static boolean visited[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        adj = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int U = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());

            adj[U].add(V);
            adj[V].add(U);
        }

        counts = new int[N + 1];
        visited = new boolean[N + 1];
        visited[R] = true;

        count(R);

        for (int i = 0; i < Q; i++) {
            int U = Integer.parseInt(br.readLine());

            System.out.println(counts[U]);
        }
    }

    private static int count(int now) {
        boolean hasChild = false;

        for (Integer next : adj[now]) {
            if (!visited[next]) {
                hasChild = true;
                visited[next] = true;

                counts[now] += count(next);
            }
        }

        if (!hasChild) {
            return counts[now] = 1;
        }

        return counts[now] = counts[now] + 1;
    }
}
/**
 * N 2~10만
 * Q 1~10만
 * 만약 쿼리마다 탐색을 하면 -> 최대 100억회
 *
 * 루트 기준으로 최초 순회에 개수를 미리 세놓자.
 */
