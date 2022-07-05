import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static final int SIZE_OF_LOTTO = 6;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int[] S;
        int[] answer = new int[6];

        while (true) {
            st = new StringTokenizer(br.readLine());
            int k =  Integer.parseInt(st.nextToken());
            if (k == 0) {
                break;
            } else {
                S = new int[k];
            }

            for (int i = 0; i < k; i++) {
                S[i] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(S);

            dfs(0, S, answer, 0);
            sb.append("\n"); //케이스 사이 개행
        }

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
    static void dfs(int depth, int[] S, int[] answer, int start) {
        if (depth == SIZE_OF_LOTTO) {
            for (Integer num : answer) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }

        for (int i = start; i < S.length; i++) {
                answer[depth] = S[i];
                dfs(depth + 1, S, answer, i + 1);
        }

    }
}
/*
독일 로또 1~49 6개
k * k - 1 ..* k - 5 / 6!
6 < k < 13
중복x 오름차순 정렬해서 뽑기

배열을 사용할때와 차이점이 뭐가 있길래 이렇게 나올까?

 */
