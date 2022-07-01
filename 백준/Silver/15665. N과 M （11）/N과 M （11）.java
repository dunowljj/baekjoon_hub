import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] inputNums;
    static int[] arr;
    static int M;
    static int N;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        inputNums = new int[N];
        arr = new int[M];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i<N; i++)
            inputNums[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(inputNums);

        dfs(0);

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
            return;
        }

        else{
            int before = 0;
            for (int i = 0; i < N; i++) {

                if (before != inputNums[i]) {
                    arr[depth] = inputNums[i];
                    before = inputNums[i];
                    dfs(depth + 1);
                }
            }
        }
    }

}
/*
같은 수가 여러개 주어질 수 있음 + 같은 수 여러번 고를 수 있음
방법2 : 현재에서 방문여부만 제거

 */