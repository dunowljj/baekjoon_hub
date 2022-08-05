import java.io.*;
import java.util.StringTokenizer;

public class Main {

    static int[] arr;
    static int[] operators;
    static int N;
    static int max = Integer.MIN_VALUE;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        operators = new int[4];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            operators[i] = Integer.parseInt(st.nextToken());
        }

        dfs(1, arr[0]);

        bw.write(max+"\n"+min);
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, int result) {
        if (depth == N) {
            if (max < result) {
                max = result;
            }

            if (min > result) {
                min = result;
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operators[i] > 0) {
                operators[i]--;

                if (i == 0) dfs(depth + 1, result + arr[depth]);
                else if (i == 1) dfs(depth + 1, result - arr[depth]);
                else if (i == 2) dfs(depth + 1, result * arr[depth]);
                else if (i == 3) dfs(depth + 1, result / arr[depth]);

                operators[i]++;
            }
        }
    }
}
/*
0 1 2 3
덧 뺄 곱 나

종료 조건을 잘 지키면 된다.
 */