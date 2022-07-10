import java.io.*;

public class Main {
    static int S = 0;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        S = 0;
        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            excute(br.readLine());
        }
        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    private static void excute(String input) {
        String[] inputs = input.split(" ");
        String command = inputs[0];
        int num = 0;
        switch (command) {
            case "add":
                num = Integer.parseInt(inputs[1]);
                S |= (1 << num);
                break;
            case "remove":
                num = Integer.parseInt(inputs[1]);
                S &= ~(1 << num);
                break;
            case "check":
                num = Integer.parseInt(inputs[1]);
                sb.append((S & (1 << (num))) != 0 ? "1\n" : "0\n");
                break;
            case "toggle":
                num = Integer.parseInt(inputs[1]);
                S ^= (1 << num);
                break;
            case "all":
                S |= (~0);
                break;
            case "empty":
                S &= 0;
                break;
        }
    }
}