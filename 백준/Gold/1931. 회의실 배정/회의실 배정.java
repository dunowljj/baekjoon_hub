import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        Queue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }
                return o1[1] - o2[1];
            }
        });

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int startT = Integer.parseInt(st.nextToken());
            int endT = Integer.parseInt(st.nextToken());

            queue.offer(new int[]{startT, endT});
        }

        int answer = 0;
        int end = 0;

        // 시간 체크 무엇으로? -> 값비교로 시작과 끝을 계속 갱신하자.
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

      /*      if (curr[0] == curr[1]) {
                answer++;
                continue;
            }*/
            // 이전 끝점보다 현재 시작이 같거나 클때, 끝점을 갱신
            if (end <= curr[0]) {
                end = curr[1];
                answer++;
            }
        }

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }
}
/*
시작시간 끝나는 시간이 겹칠 수 있다.
시작하자마자 끝나는 회의도 있다.

회의의 최대 개수를 구하기

맨 앞 순서 가장 짧은것부터? -> 끝나는 시점을 기준으로 생각해보기
1) 끝나는 시점이 적은 것부터 사용 -> 우선순위 큐로 회의 끝나는 시간 기준 정렬 (회의 끝나는 시간이 정해지면, 어짜피 그 앞에 시간에는 회의를 못연다.)
2) 시작시간은 사용 가능한지 판별

시작하자마자 끝나는 회의를 고려해야한다. 만약 시작 == 끝이라면 어느 위치에 있든 개수를 카운트해주자.
 */