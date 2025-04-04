import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int NOT_VISITED = 0;

    public static int[] visited;
    static StringBuilder answer = new StringBuilder();
    static int[] vote;

    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            int n = Integer.parseInt(br.readLine());
            count = 0;
            vote = new int[n + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                vote[i] =  Integer.parseInt(st.nextToken());
            }

            visited = new int[n + 1]; // 0방문x, 1탐색중, 2팀구성완료
            for (int start = 1; start <= n; start++) {
                if (visited[start] == 2) continue;
                dfs(start);
            }

            answer.append(n - count).append(System.lineSeparator());
        }
        System.out.print(answer.toString().trim());
    }

    private static void dfs(int curr) {
       visited[curr] = 1;
       int next = vote[curr];

       if (visited[next] == 0) {
           dfs(next);
       } else if (visited[next] == 1) {
           for (int i = next; curr != i; i = vote[i]) count++;
           count++;
       }

       visited[curr] = 2;
    }
}

/**
 * 서로 맞물리게 신청하는 경우, 싸이클이 형성되는 경우만 한 팀이 될 수 있다.
 *
 * 1명이 딱 1명만 지정가능하므로, 싸이클끼리 교집합은 생길 수 없다.
 *
 * case1: 자기자신을 선택해서 혼자 팀이 되는 경우
 * case2: 싸이클을 형성하는 경우
 *
 * case2를 제외하고, case2와 연결된 노드도 모두 제했을때, 나머지 노드는 모두 싸이클을 형상한다.
 *
 *
 * 2 -> 1 -> 3 -> 1 인 경우 1은 싸이클에  포함되어선 안된다.
 * - 여기서 시작점을 1로 탐색한 후에, 시작점을 2로 탐색한다면, bool만으로 싸이클 체크가 제대로 안된다.
 *
 */