import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    
    static long M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        M = Long.parseLong(st.nextToken());

        long[] arr = new long[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }
        Arrays.sort(arr);

        // l,r 초기 위치 지정
        // arr[l]과 M이상 차이나는 최초의 위치를 r로 지정
        int l = 0;
        int r = 0;

        long min = Long.MAX_VALUE;
        while (l < N && r < N) {
            long diff = arr[r] - arr[l];

            if (diff >= M) {
                min = Math.min(min, arr[r] - arr[l]);
                l++;
            } else {
                r++;
            }
        }

        System.out.print(min);
    }
}

/**
 * N 최대 10^5
 * M 최대 2* 10^9
 *
 * 단, 수의 중복자체는 허용해야한다.
 * M=0이면 자기자신을 선택가능한가?
 */
