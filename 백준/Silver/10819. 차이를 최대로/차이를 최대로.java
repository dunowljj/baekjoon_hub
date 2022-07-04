import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int max = 0;
    static int[] curr;
    static int[] arr;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        visited = new boolean[N];
        curr = new int[N];

        dfs(0);

        bw.write(max+"");
        bw.flush();
        bw.close();
    }

    static void dfs(int depth) {
        if (depth == N) {
            max = Math.max(max, calcResult(curr));
            return;
        }

        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                curr[depth] = arr[i];
                dfs(depth + 1);
                visited[i] = false;
            }
        }
    }

    private static int calcResult(int[] curr) {
        int sum = 0;
        for (int i = 0; i < N - 1; i++) {
            sum += Math.abs(curr[i] - curr[i + 1]);
        }

        return sum;
    }
}
/*
배치 가능 경우의 수
N! -> 최대 8! = 40320  -> * 7 = 56만번~
 */