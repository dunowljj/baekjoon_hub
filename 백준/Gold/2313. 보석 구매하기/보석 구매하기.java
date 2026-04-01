import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";
    private static final int INF = 10_001;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        List<int[]> answers = new ArrayList<>();
        int totalValue = 0;

        while (N-- > 0) {
            int L = Integer.parseInt(br.readLine());

            int sum = 0;
            int maxSum = -INF;
            int minCount = INF;

            int start = 1;
            int[] answer = new int[]{0, 0};

            st = new StringTokenizer(br.readLine());
            for (int end = 1; end <= L; end++) {
                int value = Integer.parseInt(st.nextToken());

                // 같은 경우 더 짧은 구간
                if (sum + value <= value) {
                    start = end;
                    sum = value;
                } else {
                    sum += value;
                }

                int count = end - start;

                if (maxSum == sum) {
                    if (count < minCount) { // 같은 경우 더 짧은 구간
                        minCount = count;
                        answer[0] = start;
                        answer[1] = end;
                    }
                } else if (maxSum < sum) {
                    minCount = count;
                    answer[0] = start;
                    answer[1] = end;

                    maxSum = sum;
                }
            }

            answers.add(answer);
            totalValue += maxSum;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(totalValue).append(System.lineSeparator());
        for (int i = 0; i < answers.size(); i++) {
            sb.append(answers.get(i)[0]).append(SPACE).append(answers.get(i)[1]).append(System.lineSeparator());
        }

        System.out.print(sb);
    }
}
