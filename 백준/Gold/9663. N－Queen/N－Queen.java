import java.io.*;

public class Main {
    static int[] queen;
    static int N;
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        queen = new int[N];
        recur(0);

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }

    static void recur(int row) {
        if (row == N) {
            answer++;
            return;
        }

        for (int i = 0; i < N; i++) {
            queen[row] = i;
            if (queenable(row)) {
                recur(row + 1);
            }
        }
    }


    private static boolean queenable(int row) {
        for (int i = 0; i < row; i++) {
            if (queen[i] == queen[row]) {
                return false;
            }

            if (Math.abs(queen[row] - queen[i]) == Math.abs(row - i)) {
                return false;
            }
        }
        return true;
    }


//uncheck 하는 방법이 문제다. 이전 값들ㅇ
}
