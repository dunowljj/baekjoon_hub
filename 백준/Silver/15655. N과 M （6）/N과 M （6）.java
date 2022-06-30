import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[] arr;
    static boolean[] visited;
    static int[] inputNums;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        sb = new StringBuilder();
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[M]; //수열 저장
        visited = new boolean[N]; //방문여부 체크

        //입력된 숫자들 저장
        inputNums = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            inputNums[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(inputNums);

        dfs(0, 0);

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
    static void dfs(int depth, int start) {
        if (depth == M) {
            for (int num : arr) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }

        for (int i = start; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                arr[depth] = inputNums[i];
                dfs(depth + 1, i + 1);
                visited[i] = false;
            }
        }
    }

}
/*
중복 수 x
중복 수열 x
오름차순
입력되는 수는 10000이하

정답이 오름차순만 나오도록 -> 미리 정렬 후 시작지점 활용
 */