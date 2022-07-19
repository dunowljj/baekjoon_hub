import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        bw.write(calcBC(N, K)+"");
        bw.flush();
        bw.close();
    }
    static int calcBC(int N, int K) {
        return f(N) / (f(K) * f(N - K));
    }

    private static int f(int num) {
        if (num == 0) return 1;

        return num * f(num - 1);
    }
}
