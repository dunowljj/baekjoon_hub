import java.io.*;

public class Main {
    static int N;
    static int[] perm;
    static boolean[] visited;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        perm = new int[N];
        visited = new boolean[N];

        dfs(0);

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    static void dfs(int depth) {
        if (depth == N) {
            for (int num : perm) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
        }

        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                perm[depth] = i + 1;
                dfs(depth + 1);
                visited[i] = false;
            }
        }
    }
}
