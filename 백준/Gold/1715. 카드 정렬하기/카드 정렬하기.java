import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            pq.offer(Integer.parseInt(br.readLine()));
        }

        int compareCount = 0;
        while (pq.size() > 1) {
            int mergedSize = pq.poll() + pq.poll();

            compareCount += mergedSize;
            pq.offer(mergedSize);
        }

        System.out.print(compareCount);
    }
}

/**
 * 더하는 값만큼 비교 횟수에 추가된다.
 *
 * 합치는 횟수는 어짜피 동일하므로, 작은 수끼리 먼저 합쳐야 최대한 적은 비교횟수를 가지게 된다.
 * 우선순위큐로 작은 크기의 뭉치를 찾자.
 *
 * 총합 최대 1억
 */