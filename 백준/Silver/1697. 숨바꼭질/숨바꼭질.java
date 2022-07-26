import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int answer = 0;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        visited = new boolean[170_000];
        bfs(N, K, 0);

        bw.write(answer +"");
        bw.flush();
        bw.close();
    }

    static void bfs(int N, int K, int depth) {
        if (N == K) return;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{N, depth});
        while (!queue.isEmpty()) {

            int curr[] = queue.poll();
            N = curr[0];
            depth = curr[1];

            if (N == K) {
                answer = depth;
                return;
            }

            // 음수구간 탐색 불필요
            if (N != 0 && !visited[N -1]) {
                visited[N - 1] = true;
                queue.offer(new int[]{N - 1, depth + 1});
            }

            // 10만 넘어가거나 목표 위치가 더 작다면 더하거나 곱할 필요 없음
            if (N > 100_000 || N > K) continue;

            if (!visited[N + 1]) {
                visited[N + 1] = true;
                queue.add(new int[]{N + 1, depth + 1});
            }

            // 1일때 더하나 2곱하나 같은데, 둘다 넣으면 연산이 2배가 된다. 하나 배제
            if (N == 1) continue;

            // 현재 위치가 목표 보다 작은데, 현재 위치 곱하기 2는 목표보다 클 때
            if (N < K && (2 * N > K)) {
                // 현재 위치를 곱해서 - 접근하는 횟수보다 + 연산만 사용하는 횟수가 더 적을때 -> 곱할 필요가 없다.
                if ((2 * N) - K + 1 > K - N) {
                    continue;
                }
            }

            if (!visited[2 * N]) {
                visited[2 * N] = true;
                queue.add(new int[]{N * 2, depth + 1});
            }
        }
    }
}
/*
이동방법 :
2 * X / X + 1 / X - 1
완전탐색 아닌가? 연습삼아 두개 다 해보기 -> 깊이우선, 완전탐색 시 헤어나올수 없는 암흑까지 탐색할수도 있다.

BFS 사용
- 첫 수빈이의 위치에서 순간이동, 전진, 후진 3가지 경우를 적용하여 큐에 넣는다.
- 큐에서 결과 값 하나를 빼서 해당 값으로 다시 진행한다.

-> 아무 조건없이 모두 큐에 넣으면 메모리 초과가 발생한다. *2 때문에 int범위도 초과할듯
 */