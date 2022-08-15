import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
class Register {
    int n;
    StringBuilder record;

    public Register(int n) {
        this.n = n;
        record = new StringBuilder();
    }

    public Register(int n, StringBuilder record) {
        this.n = n;
        this.record = record;
    }

    public int D(int n) {
        record.append("D");
        return (2 * n) % 10_000;
    }

    public int S(int n) {
        record.append("S");
        n = n - 1;

        if (n == -1) {
            n = 9999;
        }
        return n;
    }

    public int L(int n) {
        // 가장 높은 자릿수의 자리값 구하고 제거
        int maxDigitNum = n / 1000;
        n -= (maxDigitNum * 1000);

        // 전체에 10 곱한 후, 높은 자릿수 일의 자리에 더하기
        n *= 10;
        n += maxDigitNum;

        record.append("L");
        return n;
    }

    public int R(int n) {
        //가장 작은 자릿수를 가장 큰 자릿수로 옮긴다.

        // 1의 자리 숫자
        int minDigitNum = n % 10;

        // 1의 자리 제거
        n /= 10;

        // 1000의 자리로 1의 자리 옮김
        n += minDigitNum * 1000;

        record.append("R");
        return n;
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());


            String answer = bfs(A, B);
            bw.write(answer+"\n");
        }


        bw.flush();
        bw.close();
    }
    static String bfs(int A, int B) {
        Queue<Register> queue = new LinkedList<>();
        queue.add(new Register(A));

        boolean[] visited = new boolean[10000];

        while (!queue.isEmpty()) {
            Register register = queue.poll();
            int n = register.n;
            StringBuilder record = register.record;

            if (n == B) {
                return record.toString().trim();
            }

            for (int i = 0; i < 4; i++) {
                int nn = operate(register, n, i);

                if (!visited[nn]) {
                    visited[nn] = true;
                    queue.add(new Register(nn, new StringBuilder(register.record)));
                }

                record = register.record;
                // 마지막에 추가한 연산 지우고 다음으로 넘어가기
                record.delete(record.length() - 1, record.length());
            }
        }
        return "";
    }

    private static int operate(Register register,int n, int type) {
        switch (type) {
            case 0 :
                return register.D(n);
            case 1 :
                return register.S(n);
            case 2 :
                return register.L(n);
            case 3 :
                return register.R(n);
        }
        return 0;
    }


}
/*
A가 0인 경우 어떻게? A != B
n이 0이면 S시 9999 저장
 */