import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static final int IDX_CONVERT = 10_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int[] cardsCount = new int[20_000_001];

        // 가지고 있는 카드들의 수에 해당하는 인덱스에 ++해서 카운트 0을 고려 안했음;;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int input = Integer.parseInt(st.nextToken());
            cardsCount[input + IDX_CONVERT]++;
        }

        int M = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            int curr = Integer.parseInt(st.nextToken());
            int count = cardsCount[curr + IDX_CONVERT];

            sb.append(count).append(" ");
        }

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
}