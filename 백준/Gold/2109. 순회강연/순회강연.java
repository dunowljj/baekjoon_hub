import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        boolean[] visited = new boolean[10001];

        // 비싼 가격부터, 가격 같으면 마감 기한이 널널한 것부터.
        PriorityQueue<int[]> pq = new PriorityQueue(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o2[0] == o1[0]) {
                    return o2[1] - o1[1];
                }
                return o2[0] - o1[0];
            }
        });

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            pq.offer(new int[]{p, d});
        }

        long sum = 0;
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();

            int price = curr[0];
            int day = curr[1];


            for (int i = day; i >= 1; i--) {
                if (!visited[i]) {
                    visited[i] = true;
                    sum += price;
                    break;
                }
            }
        }

        bw.write(sum+"");
        bw.flush();
        bw.close();
    }
}

/*
하루에 한 곳만 강연
몇 번 대학에서 강의를 했는지는 몰라도 된다. 최대로 벌 수 있는 돈만 출력하면 된다.

d 일째에 강연을 하는 것이 아닌, d일 이내에만 강연을 하면 되는 경우라서 주의해야 한다.

만약 1일에 비싼 가격의 강의라서 먼저했는데, 해당 강의는 다음날에도 할 수 있다면, 1일에 다른 강의가 올 수 있는 가능성을 막을수도 있다.

1일차부터 탐색 -> 1중에 최댓값 -> 2중에 최댓값 (처리 후 제거) ->
찾으면 2일차 -> 2중에 최댓값...
이것도 문제인게 만약 3번째에 2번째로 큰 값이 1,2일차보다 큰 값이 있다면? 문제가 된다.

높은 가격부터 무조건 처리할때,
- 1일차에 가장 높은 가격이 2개가 있다면, 하나밖에 사용하지 못한다.
- 마찬가지로 2일차에 높은 가격이 발견되었는데, 이미 3일차의 높은 가격들을 3개 이상 사용했다면 사용 불가일 것이다. 이것을 체크해야한다.
- 결국, (해당 일수;d) > (이전 일수에서의 강연 참여 횟수) + (해당 일수의 강연 참여 횟수)인 경우만 강연에 참여할 수 있다.
- 비싼 강연을 먼저 처리하되, 특정 날짜와 그 날짜 이전에 참여한 강연 수를 카운트할 수 있어야한다.





n^2 -> 10^10 : 100억

## 범위
10_000 * 10_000 = 10^10이 정수의 최댓값이다. 100억인데, int를 초과하니 long으로 집계해야한다.

*/