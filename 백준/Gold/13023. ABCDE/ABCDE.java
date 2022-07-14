import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static ArrayList<Integer>[] list;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new boolean[N];

        list = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list[a].add(b);
            list[b].add(a);
        }


        for (int i = 0; i < N; i++) {
            dfs(0, i);
        }

        System.out.print(0);
    }
    static void dfs(int depth, int start) {
        if (depth == 4) {
            System.out.print("1");
            System.exit(0);
        }
        visited[start] = true;
        for (int x : list[start]) {
            if (!visited[x]) {
                dfs(depth + 1, x);
            }
        }
        //list[start]에 값이 없어서for문이 실행되지 않을 경우 대비해서 밖에서 off해줘야 한다.
        visited[start] = false;
    }
}
/*
인접리스트 활용
방문x의 경우, 해당 리스트의 마지막 수와 일치하는 경우 나머지 하나를 이어 붙인다.
 */