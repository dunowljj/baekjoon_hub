import java.io.*;
import java.util.*;

public class Main {
    static int[] inputNums;
    static int[] arr;
    static int M;
    static int N;
    static StringBuilder sb = new StringBuilder();
    static Set<Integer> set = new LinkedHashSet<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        inputNums = new int[N];
        arr = new int[M];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i<N; i++) {
            set.add(Integer.parseInt(st.nextToken()));
        }

        Iterator<Integer> iter = set.iterator();
        for (int i = 0; i < set.size(); i++) {
            inputNums[i] = iter.next();
        }
        Arrays.sort(inputNums);

        dfs(0, 0);

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, int start) {
        if (depth == M) {
            for (int num : arr) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }

        int before = 0;
        for (int i = start; i < N; i++) {

            if (before != inputNums[i]) {
                arr[depth] = inputNums[i];
                before = inputNums[i];
                dfs(depth + 1, i);
            }
        }
    }

}
/*
같은 수가 여러개 주어질 수 있음 + 같은 수 여러번 고를 수 있음
방법1 : 방문여부 제거하고 set 으로 입력받은 숫자를 거르기, 시작점 부여

 */