import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int[] cardsCount = new int[20_000_001];
        // (-10_000_000 ~ -1 ) + 10_000_000 -> 1~ (10_000_000 -1)
        // 0~10_000_000  + 10_000_000 -> 10_000_000 ~ 20_000_000

        // 가지고 있는 카드들의 수에 해당하는 인덱스에 ++해서 카운트 0을 고려 안했음;;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int input = Integer.parseInt(st.nextToken());
            int idx = input + 10_000_000;

            cardsCount[idx]++;
        }

        int M = Integer.parseInt(br.readLine());
        int[] answer = new int[M];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            int curr = Integer.parseInt(st.nextToken());
            int idx = curr + 10_000_000;

            answer[i] = cardsCount[idx];
        }

        for (int num : answer) {
            sb.append(num).append(" ");
        }

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
}