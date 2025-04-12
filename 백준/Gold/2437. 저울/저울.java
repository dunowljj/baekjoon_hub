import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        Set<Integer> weightSums = new HashSet<>();
        weightSums.add(0);

        st = new StringTokenizer(br.readLine());
        int[] weights = new int[N];

        for (int i = 0; i < N; i++) {
            weights[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(weights);

        if (weights[0] >= 2) {
            System.out.print(1);
            return;
        }

        int answer = 0;
        for (int i = 1; i < weights.length; i++) {
            if (weights[i - 1] + 1 < weights[i]) {
                System.out.print(weights[i - 1] + 1);
                return;
            }
            weights[i] += weights[i - 1];
        }

        System.out.print(weights[weights.length - 1] + 1);
    }
}
/**
 * 추의 중복사용은 불가능하다.
 */