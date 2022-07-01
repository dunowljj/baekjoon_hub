import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] inputNums;
    static int[] arr;
    static boolean[] visited;
    static int M;
    static int N;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        inputNums = new int[N];
        visited = new boolean[N];
        arr = new int[M];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i<N; i++)
            inputNums[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(inputNums);

        dfs(0);

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    static void dfs(int depth) {
        if (depth == M) {
            for (int num : arr) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }

        else{
            int before = 0;
            for(int i = 0; i<N; i++){
                if(visited[i])
                    continue;

                if(before != inputNums[i]){
                    visited[i] = true;
                    arr[depth] = inputNums[i];
                    before = inputNums[i];
                    dfs(depth+1);
                    visited[i] = false;
                }
            }
        }
    }

}
/*
dfs를 재귀해서 들어갈 땐 before가 0으로 초기화되고, for문을 순회할때는 이전값이 저장된다.
-> 같은 수가 입력되었을때, 해당 자릿수에 똑같은 수가 또 들어올 수 없게 하는 장치
 */