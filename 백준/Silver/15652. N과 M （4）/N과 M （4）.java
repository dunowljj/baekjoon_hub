import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static int arr[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        arr = new int[M];
        int depth = 0;
        dfs(N, M, depth, 0);
        System.out.println(sb);
    }
    public static void dfs(int N, int M, int depth, int start){
        if (M == depth) {
            for(int num : arr) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }

        for(int i=start; i<N; i++){
            arr[depth] = i+1;
            start = i;
            dfs(N, M , depth+1, start);
        }
    }
}
