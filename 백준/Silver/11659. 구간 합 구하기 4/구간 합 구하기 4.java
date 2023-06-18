import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int numberOfToken = Integer.parseInt(st.nextToken());
        int lineCount = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] sumArr = new int[numberOfToken + 1];

        // 구간 합 배열 만들기
        for (int i = 1; i < numberOfToken + 1; i++) {
            sumArr[i] = sumArr[i - 1] + Integer.parseInt(st.nextToken());
        }


        // 각각 합 계산 및 출력
        for (int line = 0; line < lineCount; line++) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken()) - 1;
            int j = Integer.parseInt(st.nextToken());

            System.out.println(sumArr[j] - sumArr[i]);
        }
    }
}

/**
 *   5 4 3 2 1
 * 0 5 9 12 14 15
 *
 * 1,3 -> 12 - 0
 * 2,3 -> 12 - 5
 * 2,5 -> 15 -5
 */
