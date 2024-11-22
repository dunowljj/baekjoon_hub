import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int MAX_HEIGHT = 256;
    public static final int IMPOSSIBLE = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        // init
        int[][] land = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");

            for (int j = 0; j < M; j++) {
                land[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int minTime = Integer.MAX_VALUE;
        int height = 0;
        for (int h = 0; h <= MAX_HEIGHT; h++) {
            int needTime = calculateTime(land, h, B);
            if (needTime == IMPOSSIBLE) continue;

            if (minTime >= needTime) {
                minTime = needTime;
                height = h;
            }
        }

        System.out.print(minTime + " " + height);
    }

    private static int calculateTime(int[][] land, int h, int B) {
        int needBlock = 0;
        int needTime = 0;

        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[i].length; j++) {
                int block = h - land[i][j];
                int time = block;

                if (block < 0) {
                    time = IMPOSSIBLE * block * 2;
                }

                needBlock += block;
                needTime += time;
            }
        }

        if (B >= needBlock) return needTime;
        else return IMPOSSIBLE;
    }
}
/**
 * N, M 집
 * 500 *500 -> 250_000
 * 25만 * 256 -> 6천만 이상 + 10^7 -> int 범위내
 *
 * 땅고르는데 걸리는 최소 시간을 어떻게 찾을까?
 * - 블록제거후 인벤토리는 2초
 * - 블록을 꺼내어 놓는 것은 1초가 걸린다.
 *
 *
 * - 한 부분만 심히 튀어나온 경우 그부분만 땅을 고르면 된다.
 * - 상대적으로 높은 부분이 여러곳일때, 낮은곳이 적다면 블록을 올리는게 더 최소가 될 수있다.
 * - 올릴 블록이 없다면 결국 제거해야한다.
 *
 *
 * 0~256 중 특정 수로 땅을 고르는 모든 경우의 수를 계산한다.
 * - 각 요소에서 땅을 고르는데 필요한 블록 수와 시간을 구한다.
 * - 인벤토리에 블럭이 모자른 경우 불가능하다.
 * - 최소 시간이 걸리는 경우를 찾는다. 높은 경우가 나중에 갱신되도록 하기.
 *
 * 답이 여러개라면 땅의 높이가 가장 높은 경우
 */
