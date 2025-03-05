import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {


    private static int N, K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        int[][] land = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                land[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        K = Integer.parseInt(br.readLine());
        List<Integer> blueprint = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            blueprint.add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(blueprint, (b1, b2) -> b2 - b1);

        int[] blueprintSum = new int[K + 1];
        int[][] zeroSum = new int[N + 1][N + 1];
        int[][] valueSum = new int[N + 1][N + 1];
        initSumArrays(land, zeroSum, valueSum, blueprint, blueprintSum);

        int maxVal = 0;
        for (int n = 1; n <= N; n++) {
            int step = n - 1;
            for (int i = 1; i <= N - step; i++) {
                for (int j = 1; j <= N - step; j++) {
                    int[] leftTop = {i, j};
                    int[] leftBottom = {i, j + step};
                    int[] rightTop = {i + step, j};
                    int[] rightBottom = {i + step, j + step};

                    int zeroCount = zeroSum[rightBottom[0]][rightBottom[1]]
                            + zeroSum[leftTop[0] - 1][leftTop[1] - 1]
                            - zeroSum[leftBottom[0] - 1][leftBottom[1]]
                            - zeroSum[rightTop[0]][rightTop[1] - 1];

                    // 설계도 수보다 0이 많으면, 불가능.
                    if (zeroCount > K) {
                        continue;
                    }

                    int value = valueSum[rightBottom[0]][rightBottom[1]]
                            + valueSum[leftTop[0] - 1][leftTop[1] - 1]
                            - valueSum[leftBottom[0] - 1][leftBottom[1]]
                            - valueSum[rightTop[0]][rightTop[1] - 1];

                    maxVal = Math.max(value + blueprintSum[zeroCount], maxVal);
                }
            }
        }

        System.out.print(maxVal);
    }

    private static void initSumArrays(int[][] land, int[][] zeroSum, int[][] valueSum, List<Integer> blueprint, int[] blueprintSum) {
        // 세로 합
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (land[i][j] == 0) zeroSum[i][j]++;
                valueSum[i][j] = land[i][j];

                zeroSum[i][j] += zeroSum[i - 1][j];
                valueSum[i][j] += valueSum[i - 1][j];
            }
        }

        // 가로 합
        for (int j = 1; j <= N; j++) {
            for (int i = 1; i <= N; i++) {
                zeroSum[i][j] += zeroSum[i][j - 1];
                valueSum[i][j] += valueSum[i][j - 1];
            }
        }

        for (int i = 1; i <= K; i++) {
            blueprintSum[i] = blueprint.get(i - 1);
            blueprintSum[i] += blueprintSum[i - 1];
        }
//        print(N, sum, "sum");
//        print(N, zeroSum, "zeroSum");
//        print(N, blueprintSum, "bluprint");
    }

    private static void print(int N, int[][] sum, String title) {
        System.out.println("["+title+"]");
        for (int i = 1; i <= sum.length; i++) {
            for (int j = 1; j <= sum[i].length; j++) {
                System.out.print(sum[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void print(int N, int[] sum, String title) {
        System.out.println("["+title+"]");
        for (int i = 1; i < sum.length; i++) {
            System.out.print(sum[i] + " ");
        }
        System.out.println();
    }
}

/**
 * 음이 아닌 정수 0~
 *
 * 설계도를 적절히 사용한 후, 가치가 최대인 영역을 정복하기.
 * 0인 칸을 선택하고, 설계도를 쓰는게 더 나을수도 있다.
 * 설계도는 일회용이고, 칸당한번이므로, 비용이 큰 설계도부터 사용하자.
 *
 * 500*500 => 25만칸인데, 정사각형의 크기가 정해지지 않았다..
 *
 * 2차원 누적합으로 0의 개수, 그리고 총 자원의 합을 구하는 2개 배열을 만들기
 * 설계도도 n개의 설계도 사용시 최대값을 미리 구함.
 *
 *
 * 1. 누적합을 사용하고, 공집합 활용을 위해 사이즈를 1 크게 지정하기
 * - 이때 누적합은 2 종류가 필요하다.
 * - 1) 땅의 가치에 대한 누적합 2)빈 0의 개수를 세는 누적합
 * - 0인칸을 모두 없앨 수 있어야한다.
 * 2. 정사각형의 길이별로, 누적합을 이용하여 전체 땅을 탐색.
 * - 길이 1~500; 1~500길이인 정사각형에 대해서 각 500^2,499^2~1개 탐색
 * - 설계도의 개수보다 0의 수가 크다면, 처리할 수 없는 땅이다.
 */