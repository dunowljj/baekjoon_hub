import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] stair = new int[N + 1];
        Arrays.fill(stair, Integer.MAX_VALUE);
        stair[0] = 0;

        for (int i = 0; i < N; i++) {
            stair[i + 1] = Math.min(stair[i + 1], stair[i] + 1);

            if ((i + (i / 2)) > N) continue;
            stair[(i + (i / 2))] = Math.min(stair[(i + (i / 2))], stair[i] + 1);
        }
        
        if (stair[N] > K) System.out.print("water");
        else System.out.print("minigimbob");
    }
}

/**
 * 나누기 소수점은 어떻게하는가?
 *
 *
 * 1 한칸 오르기
 * 2 지팡이 두드리기

 */