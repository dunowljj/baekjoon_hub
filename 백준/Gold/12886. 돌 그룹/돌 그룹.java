import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main {
    static int[] arr = new int[3];
    static boolean[][] visited = new boolean[1500][1500];
    static int A;
    static int B;
    static int C;
    static int answer = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        // 애초에 3으로 안나눠지면 같은 돌이 안된다.

        visit(A, B, C);

        bfs(A, B, C);
        bw.write(answer+"");
        bw.flush();
        bw.close();
    }

    private static void bfs(int A, int B, int C) {
        if ((A + B + C) % 3 != 0) {
            return;
        }

        // 두 개를 뽑은 것끼리 연산 -> 같으면 1, 아니면 정렬 ->  방문체크하고 큐에 넣기
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{A, B, C});


        while (!queue.isEmpty()) {
            int[] groups = queue.poll();
            A = groups[0];
            B = groups[1];
            C = groups[2];
            
            // 모두 같으면 종료
            if (A == B && B == C) {
                answer = 1;
                return;
            }

            // 나머지 연산을 통해 두개씩 짝짓는 모든 경우  탐색
            for (int i = 0; i < 3; i++) {
                int a = groups[i];           // 0, 1, 2
                int b = groups[(i + 1) % 3]; // 1, 2, 0

                int c = groups[(i + 2) % 3]; // 2, 0, 1

                // 두 개의 값이 같으면 다음 탐색. 같은 돌 개수는 연산 불가능하다.
                if (a == b) continue;

                // 두 개의 돌 옮기기
                int na = a > b ? a - b : a + a;
                int nb = a > b ? b + b : b - a;

                if (!visited[na][nb]) {
                    visit(na, nb, c);
                    queue.add(new int[]{na, nb, c});
                }
            }
        }
    }

    private static void visit(int A, int B, int C) {
        visited[A][B] = true;
        /*visited[B][A] = true;
        visited[B][C] = true;
        visited[C][B] = true;
        visited[C][A] = true;
        visited[A][C] = true;*/
    }
}

/*
# 풀이

1. 3개 중 서로 크기가 다른 2개를 뽑는다.
2. bfs탐색을 하며 연산을 한다.

3. 중복되는 경우를 종료시킨다. -> 어떻게?
    방문배열 -> 수들을 정렬하여 방문체크를 하지 않으면 케이스당 6가지를 체크해야한다.
        -> 크기 3의 배열에 넣고 정렬시킨후, 순서대로 방문배열에 넣어 체크하기
        -> 모든 수가 같으면 1 출력 후 종료

4. 같은 개수로 만들 수 있으면 1 아니면 0 출력


## 방문체크의 메모리 문제
방문체크시 숫자들의 최댓값이 500이다. 500, 499 가 주어지면 1, 998이 만들어질 수도 있다.
메모리를 1000*1000*1000으로 하면 바로 2바이트 * 1000 세제곱 -> 2gb다.

### 해결 실패
조건문으로 연산결과가 500을 넘는 경우는 제외하고 배열 크기를 501로 맞춰보자. -> 2gb / 8 = 250mb
생각해보니 500 초과였다가, 다른 값에 넘겨주어 성립하는 경우도 있을 것이다. 1000으로 설정해야 한다.

### 해결
총 돌의 개수가 정해져 있기때문에, 2개만 방문체크를 하면 나머지 값은 같기 때문에 신경 안써도 된다.


# 범위, 시간 2초
## 자료형
1 <= A, B, C <= 500 -> int

## 성능이 더 좋을거란 예상으로 방문체크를 여러 곳을 했다.

 */