import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int seq[];
    static int S;
    static int N;
    static int visited = 0;
    static int answer = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        seq = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            seq[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0,0,0);

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, int start, int sum) {
        // S와 같은데, 만약 그 이후에 더한 값에서 또 S가 있을수도 있다는 생각에 바로 종료를 하지 않았다.
        if (sum == S && depth != 0) {
            answer++;
        }
        if (depth == N) return;

        for (int i = start; i < N; i++) {
            dfs(depth + 1, i + 1, sum + seq[i]);
        }

    }

}
/*
방문 여부를 비트마스크로 ?
합이기때문에, 순서만 바꾼 수열이 중복된다. 그냥 start 인덱스를 지정하면 끝나는 일인데, 비트마스크를 어떻게 활용?
sum이 처음에 0이라서 문제가 된다. S가 0 인 경우가 문제
 */
