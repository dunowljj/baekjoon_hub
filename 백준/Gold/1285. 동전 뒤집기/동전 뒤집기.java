import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Main {
    static int N;
    static int[][] coins;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());

        coins = new int[N][N];
        // H 앞면, T 뒷면 -> 앞면 1, 뒷면 0

        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < N; j++) {
                if (input.charAt(j) == 'H') {
                    coins[i][j] = 1;
                }
            }
        }
        int answer = Integer.MAX_VALUE;
        // 비트가 어떤 행을 뒤집을지 결정
        for (int bit = 0; bit < (1 << (N - 1)); bit++) {

            int sum = 0;
            // 같은 열에 대해 행들 탐색
            for (int col = 0; col < N; col++) {

                int count = 0;
                for (int row = 0; row < N; row++) {
                    int curr = coins[row][col];

                    // 현재 행을 비트연산해서, 뒤집은 행인지 확인한다.
                    if ((bit & 1 << row) != 0) {
                        curr ^= 1;
                    }

                    // 한쪽 면만 체크
                    if (curr == 1) {
                        count++;
                    }

                }

                sum += Math.min(count, N - count);
            }
            answer = Math.min(answer, sum);
        }


        bw.write(answer+"");
        bw.flush();
        bw.close();
    }



}
/*
## 풀이
모든 경우의 수를 따질때, 행을 뒤집을 수 있는 모든 경우의 수 마다 열을 모두 체크한다면 모든 값을 탐색하는 것이다.
결론적으로 각각 행을 뒤집은 모든 상태에 대해 열을 검사해서 더 작은 면의 수를 센다.
행말고 열만 뒤집을 수 있는 상황이고, 열에 대해서는 어짜피 작은쪽으로 뒤집으면 되기 때문에, 더 수가 적은 면의 개수만 체크하면 된다.

행을 뒤집는 경우의 수 -> 최대 2^20
비트마스크로 2^20가지를 간단하게 나타내서 탐색할 수 있다. 행을 뒤집을 것인지 말것인지 여부를 비트마스크로 나타내는 것이다.
비트마스크를 사용하지 않을 시에는? 복잡한 탐색법을 사용해야할지도 모른다.
 */
