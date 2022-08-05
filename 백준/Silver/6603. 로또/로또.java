import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] nums;
    static int k;
    static int[] answer = new int[6];
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {

            st = new StringTokenizer(br.readLine());
            k = Integer.parseInt(st.nextToken());
            if (k == 0) break;

            nums = new int[k];
            for (int i = 0; i < k; i++) {
                nums[i] = Integer.parseInt(st.nextToken());
            }

            dfs(0, 0);
            sb.append("\n");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
    
    static void dfs(int depth, int idx) {
        if (depth == 6) {
            for (int num : answer) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }

        for (int i = idx; i < k; i++) {
            answer[depth] = nums[i];
            dfs(depth + 1, i + 1);
        }
    }
}
/*
주어진 수를 입력받고, 6개의 중복없는 오름차순 수열을 뽑아내는 것
 */