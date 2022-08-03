import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static boolean[] isNextBig;
    static boolean[] visited;
    static int k;
    static long max = 0L;
    static long min = 100_000_000_000L;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        k = Integer.parseInt(br.readLine());

        visited = new boolean[10]; // 0~9
        isNextBig = new boolean[k + 2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < k + 1; i++) {
            if ((st.nextToken()).equals("<")) {
                isNextBig[i] = true;
            }
        }

        dfs(0,0L, 0);

        if ((max + "").length() < k + 1) {
            bw.write("0"+max+"\n");
        } else {
            bw.write(""+max+"\n");
        }

        if ((min + "").length() < k + 1) {
            bw.write("0"+min);
        } else {
            bw.write(""+min);
        }

        bw.flush();
        bw.close();
    }
    static void dfs(int depth, long answer, int before) {
        if (depth == k + 1) {
            max = Math.max(answer, max);
            min = Math.min(answer, min);
            return;
        }

        long digit = (long) Math.pow(10, k - depth);
        for (int i = 0; i <= 9; i++) {

            if (!visited[i]) {
                if (depth == 0
                        || (isNextBig[depth] && before < i)
                        || (!isNextBig[depth] && before > i)) {
                    visited[i] = true;
                    dfs(depth + 1, answer + (i * digit), i);
                    visited[i] = false;
                }
            }
        }


    }

}
/*
부등호 저장
비교해서 들어가는 값 찾기
2<=k<=9 -> 0~9최대 10개 수 -> 10!

0인 경우 처리 -> 자릿수 정해져있음 -> 자리 안맞으면 맨 앞에 0 추가하면 된다.
 */