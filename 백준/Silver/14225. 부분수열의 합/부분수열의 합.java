import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static boolean[] subSum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        subSum = new boolean[100_000 * 20 + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);
        dfs(0,0, arr);

        for (int i = 1; i < subSum.length; i++) {
            if (!subSum[i]) {
                bw.write((i) + "");
                break;
            }
        }
        bw.flush();
        bw.close();
    }
    static void dfs(int idx, int sum, int[] arr) {
        if (idx == arr.length) {
            subSum[sum] = true;
            return;
        }

        dfs(idx + 1, sum + arr[idx], arr);
        dfs(idx + 1, sum, arr);

        }
    }
/*
1~20크기 수열
수열의 수들은 100_000이하 -> 10만 * 20이 합의 최대

20개 -> 2의 20제곱개 탐색 -> 2초 제한 (매우 충분) -> 완전탐색

작은 순으로 합을 정렬, 1부터 2의 20승까지 탐색하면서 합이 일치하지 않는 것 탐색

 */