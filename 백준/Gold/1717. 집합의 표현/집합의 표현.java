import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final int UNION = 0;
    public static final int CHECK = 1;
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static int[] parent;
    public static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int operation = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (operation == UNION) {
                union(a, b);
            }

            if (operation == CHECK) {
                if (find(a) == find(b)) sb.append(YES);
                else sb.append(NO);

                sb.append(System.lineSeparator());
            }
        }
        System.out.print(sb.toString().trim());
    }

    static int find(int idx) {
        if (parent[idx] == idx) return idx;
        else return parent[idx] = find(parent[idx]);
    }

    static void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if (pa != pb) {
            parent[pa] = pb;
        }
    }
}

/**
 * 합집합, 혹은 같은 집합인지 확인만 하면 됨
 * union-find 문제
 */