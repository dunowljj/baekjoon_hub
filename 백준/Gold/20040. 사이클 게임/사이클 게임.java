import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parent = new int[n];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
        int turn = 0;

        for (turn = 1; turn <= m; turn++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());

            if (find(n1) == find(n2)) {
                System.out.print(turn);
                return;
            } else {
                union(n1, n2);
            }
        }

        System.out.print("0");
    }

    private static int find(int n) {
        if (parent[n] == n) return n;
        else return parent[n] = find(parent[n]);
    }

    private static void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p1 != p2) {
            parent[p1] = p2;
        }
    }
}

/**
 * 어느 세 점도 일직선에 속하지 않는다.
 * 그룹화해서, 같은 그룹이면 싸이클이 완성되었다고 판단하기
 */