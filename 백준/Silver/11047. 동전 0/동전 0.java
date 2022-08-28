import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int answer = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] coins = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            coins[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < N; i++) {
            // 안빼질때까지 뺀다.
            while (K - coins[i] >= 0) {
                K -= coins[i];
                answer++;
                if (K == 0) {
                    bw.write(answer+"");
                    bw.flush();
                    bw.close();
                    return;
                }
            }
        }


    }
}
/*
목표 금액 : 1~1억

## 문제 1
ex)
목표 : 900
동전 : 300, 500, 50
-> 300 *3
-> 500 * 1 + 300 * 1 + 100 * 2

--> 무조건 큰 동전부터 넣는게 최솟값이 아니다.
하지만 문제에서 주어지는 동전이 2이상일때, 이전 값의 배수이다. 큰 동전만 우선시 되면 된다.

동전의 가치는 오름차순으로 주어진다 -> 정렬이 필요 없다.

## 문제 2
동전으로 합을 만들 수 없는 경우?
해당 조건이 없으므로, 만들 수 있는 수만 나온다고 가정.


 */