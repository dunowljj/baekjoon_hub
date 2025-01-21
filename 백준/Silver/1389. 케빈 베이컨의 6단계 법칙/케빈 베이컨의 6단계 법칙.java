import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Set<Integer>[] adjList = new HashSet[N + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new HashSet<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adjList[a].add(b);
            adjList[b].add(a);
        }

        // 탐색하며 방문체크, kb수 더해서 구하기
        int min = Integer.MAX_VALUE;
        int minUserNo = 0;
        for (int startNo = 1; startNo <= N; startNo++) {
            Integer[] kBValue = new Integer[101];
            Queue<Integer> queue = new LinkedList<>();

            int kbSum = calcKbSum(adjList, startNo, kBValue, queue);

            // 여러명일 경우 가장 작은 사람 출력해야함. >= 대신 > 사용해서 작은 경우만 갱신
            if (min > kbSum) {
                minUserNo = startNo;
                min = kbSum;
            }
        }

        System.out.print(minUserNo);
    }

    private static int calcKbSum(Set<Integer>[] adjList, int start, Integer[] kbValue, Queue<Integer> queue) {
        int kbSum = 0;

        queue.offer(start);
        kbValue[start] = 0;

        while (!queue.isEmpty()) {
            int now = queue.poll();

            for (Integer next : adjList[now]) {
                if (kbValue[next] == null) {
                    kbValue[next] = kbValue[now] + 1;
                    kbSum +=  kbValue[now] + 1;

                    queue.offer(next);
                }
            }
        }

        return kbSum;
    }
}

/**
 * 노드 100, 간선 5000
 * 전수검사해도 각노드마다 5천번 -> 50만번 탐색으로 끝낼 수 있다.
 *
 * 각 노드로의 최단거리의 합이 가장 작은 사람을 구해라
 *
 * 친구 관계는 중복되어 들어올 수도 있다.
 */