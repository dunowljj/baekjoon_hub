import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int M;
    static int[] arr;
    static int[] inputNums;
    static boolean[] visited;
    static StringBuilder sb;
    static Set<String> set;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        sb = new StringBuilder();
        set = new LinkedHashSet<>();
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[M]; //수열 저장
        visited = new boolean[N]; // 방문 여부
        //입력된 숫자들 저장
        inputNums = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            inputNums[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(inputNums);

        dfs(0);
        for (String perm : set) {
            sb.append(perm);
        }


        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
    static void dfs(int depth) {
        if (depth == M) {
            for (int num : arr) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            set.add(sb.toString());
            sb.setLength(0);
            return;
        }

        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                arr[depth] = inputNums[i];
                dfs(depth + 1);
                visited[i] = false;
            }
        }
    }

}