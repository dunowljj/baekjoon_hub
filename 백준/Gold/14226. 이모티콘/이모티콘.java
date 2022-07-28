import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int[][] visited;
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int S = Integer.parseInt(br.readLine());

        visited = new int[2001][2001];

        int start = 1;
        int clip = 0;
        visited[start][clip] = 0;
        bfs(start,clip,S);



        bw.write(answer+"");
        bw.flush();
        bw.close();

    }

    static void bfs(int start, int clip, int S) {
        Queue<int[]> queue = new LinkedList();
        queue.offer(new int[]{start, clip, 0});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int num = curr[0];
            clip = curr[1];
            int depth = curr[2];

            if (num == S) {
                answer = depth;
                return;
            }
            
            //하나 지우기
            if (num > 2) {
                if (visited[num - 1][clip] == 0) {
                    queue.offer(new int[]{num - 1, clip, depth + 1});
                    visited[num - 1][clip] = visited[num][clip] + 1;
                }

            }

            if (num > S) continue;

            //복사
            if(num != clip) {
                if (visited[num][num] == 0) {
                    queue.offer(new int[]{num, num, depth + 1});
                    visited[num][num] = visited[num][clip] + 1;
                }
            }

            //붙여넣기
            if ((num < 1000) && visited[num + clip][clip] == 0) {
                queue.offer(new int[]{num + clip, clip, depth + 1});
                visited[num + clip][clip] = visited[num][clip] + 1;
            }


        }

    }
}

/*
2<=S<=1000

1. 클립보드 덮어쓰며 복사
2. 붙여넣기
3. 하나 지우기
각각 1초씩 걸린다.

현재 값이 같아도, 클립보드에 값이 달라 속도가 다르기 떄문에 방문하지 않은 곳으로 볼 수 있다.
속도가 다르다는 것은, 같은 위치에 있어도 클립보드에 저장된 숫자의 크기가 다르다는 의미이다. 결국 남은 연산의 수가 다 다르다.

숨바꼭질 문제를 예시로 들면 특정 숫자에 도착했을 경우, 몇 번이 걸려서 어떤방법으로 도착했던지 간에
도착지까지 남은 연산을 할때 조건이 동일하다. 또한 먼저 도착한 element의 이동 횟수가 해당 거리까지의 최솟값이 된다.
그래서 먼저 도착하는 값의 깊이로 방문 배열을 갱신하고, 방문 배열이 0인 경우만 해당 노드를 방문하도록 설정할 수 있다.
1차원 배열로 방문 여부를 정확히 나타낼 수 있는 것이다.

이 문제의 경우 특정 숫자에 도착했을때, 클립보드에 저장된 숫자값에 따라 남은 연산의 수가 모두 다르다. 2차원 배열로 방문 배열을 만들고,
 */