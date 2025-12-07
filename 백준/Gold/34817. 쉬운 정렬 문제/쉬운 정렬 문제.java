import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] origin = new int[N];
        int[] min = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            origin[i] = num;
        }

        Arrays.fill(min, Integer.MAX_VALUE);
        min[N - 1] = origin[N - 1];
        for (int i = N - 2; i >= 0; i--) {
            min[i] = Math.min(origin[i], min[i + 1]);
        }

        for (int i = 0; i < N - 1; i++) {
            if (origin[i] > min[i + 1] && origin[i] - min[i + 1] > K) {
                System.out.print("NO");
                return;
            }
        }

        System.out.print("YES");
    }
}
/**
 * 오름차순 정렬
 * 버블 정렬이기 때문에, 기존 배열에서 i번째 요소보다 오른쪽에 더 작은 수가 있으면 정렬시 무조건 맞닿게 된다.
 * 오른쪽의 가장 작은 수와의 차가 K이하인지 확인
 *
 *
 */