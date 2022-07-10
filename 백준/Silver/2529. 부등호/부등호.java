import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class Main {
    static int k;
    static int[] arr;
    static boolean[] isDescending;
    static boolean[] visited;
    static long max = 0L;
    static long min = 10_000_000_000L;
    static StringBuilder maxStr = new StringBuilder();
    static StringBuilder minStr = new StringBuilder();
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        k = Integer.parseInt(br.readLine());
        arr = new int[k + 1];
        visited = new boolean[10];

        StringTokenizer st = new StringTokenizer(br.readLine());

        isDescending = new boolean[k + 1];
        for (int i = 1; i <= k; i++) {
            String token = st.nextToken();
            if (">".equals(token)) {
                isDescending[i] = true;
            }
        }
        dfs(0);
        sb.append(maxStr.toString().trim()).append("\n").append(minStr.toString().trim());

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
    
    static void dfs(int depth) {
        if (depth == k + 1) {
            String currStr = "";
            for (int num : arr) {
                currStr += num;
            }
            long curr = Long.parseLong(currStr);
            if (max < curr) {
                max = curr;
                maxStr.delete(0, maxStr.length());
                maxStr.append(currStr);
            }
            if (min > curr) {
                min = curr;
                minStr.delete(0, minStr.length());
                minStr.append(currStr);
            }

            /*
            숫자로 변환해서 비교하는 과정에서 0이 떨어져 나감
            - 마지막에 0 더해주기, biginteger
            - String으로 처리하기 -> 각 자리수 비교하는 메서드 구현 -> 메모리 너무 큼?
             */
            return;
        }

        for (int i = 0; i < 10; i++) {
            if (depth == 0) {
                visited[i] = true;
                arr[depth] = i;
                dfs(depth + 1);
                visited[i] = false;
                continue;
            }
            if (!visited[i] && isCorrectWithSign(i, depth)) {
                visited[i] = true;
                arr[depth] = i;
                dfs(depth + 1);
                visited[i] = false;
            }
        }
    }
    static boolean isCorrectWithSign(int i, int depth) {
        return !isDescending[depth] && arr[depth - 1] < i || isDescending[depth] && arr[depth - 1] > i;
    }
}
/*
앞뒤 숫자 한 자릿수 -> 0~9
부등호 관계를 만족시키는 정수
총 가짓수 int 초과 안함

순서는 뒤집혀도 되지만, 중복은 안됨 -> 방문 여부 체크
첫번째 혹은 마지막 번째를 어떻게 처리할 것인가?

 */
