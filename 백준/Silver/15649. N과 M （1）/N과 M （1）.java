import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static StringBuilder sb;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new boolean[N + 1];

        dfs(0, "");

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, String answer) {
        if (depth == M) {
            sb.append(answer+"\n");
            return;
        }

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(depth + 1, answer + i + " ");
                visited[i] = false;
            }
        }
    }
}
/*
출력 : 1~N 까지 수에서 M개 고른 수열
중복 수열 x
-> 순서만 바꾼것은 중복으로 치지 않는다.
같은 수 한번만 선택 가능.
공백 구분 출력

1~N까지 DFS, 방문 여부 체크해서 겹치지 않게해서
 */
