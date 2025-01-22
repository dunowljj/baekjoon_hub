import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int[] visited;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        visited = new int[140_000];
        parent = new int[140_000];

        visited[N] = 1;
        bfs(N,K,0);

        bw.write((visited[K] -1)+"\n");

        Stack<Integer> stack = new Stack<>();
        while (K != N) {
            stack.push(K);
            K = parent[K];
        }
        stack.push(N);

        while (!stack.isEmpty()) {
            bw.write(stack.pop()+" ");
        }



        bw.flush();
        bw.close();
    }


    static void bfs(int N, int K, int time) {
        Queue<Integer> queue = new LinkedList();

        queue.add(N);

        while (!queue.isEmpty()) {
            N = queue.poll();


            if (N == K) {
                return;
            }

            if (N < K  && N <= 67000 && visited[N * 2] == 0) {
                visited[N * 2] = visited[N] + 1;
                queue.add(N * 2);
                parent[N * 2] = N;
            }

            if (N != 0 && visited[N - 1] == 0) {
                visited[N - 1] = visited[N] + 1;
                queue.add(N - 1);
                parent[N - 1] = N;
            }

            if (visited[N + 1] == 0 && N != 1 && N < K) {
                visited[N + 1] = visited[N] + 1;
                queue.add(N + 1);
                parent[N + 1] = N;
            }
        }
    }
}
/*
bfs로 구한다고 치면, 깊이를 큐에 기록하면 깊이는 쉽게 구할 수 있다. 문제는 이동 경로인데, 깊이 방향으로 탐색하게 된다.

배열을 10만으로 지정
깊이를 매번 큐에 넣지 않고, 배열을 갱신하면서 계산하는게 더 효율적

부모 노드를 구할때, 방문여부를 체크하기때문에 겹치지 않는다.

 */