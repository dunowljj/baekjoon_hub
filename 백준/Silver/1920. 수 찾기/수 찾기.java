import java.io.*;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder answer = new StringBuilder();
        Set<Integer> set = new TreeSet<>();

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            set.add(num);
        }

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            int num = Integer.parseInt(st.nextToken());

            if ( set.contains(num)) {
                answer.append(1).append("\n");
            } else {
                answer.append(0).append("\n");
            }
        }

        bw.write(answer.toString().trim());
        bw.flush();
        bw.close();
    }
}
