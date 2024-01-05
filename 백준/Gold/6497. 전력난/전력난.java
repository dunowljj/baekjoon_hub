import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.BufferOverflowException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {

    private static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String input;
        while (!(input = br.readLine()).isBlank() && !input.startsWith("0 0")) {

            int m = Integer.parseInt(input.split(" ")[0]); // 길의 수
            int n = Integer.parseInt(input.split(" ")[1]); // 집의 수

            parent = new int[m];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = i;
            }


            int totalLen = 0;
            int minLen = 0;
            int[][] inputs = new int[n][3];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                inputs[i][0] = Integer.parseInt(st.nextToken());
                inputs[i][1] = Integer.parseInt(st.nextToken());
                inputs[i][2] = Integer.parseInt(st.nextToken());
                totalLen += inputs[i][2];
            }

            Arrays.sort(inputs, Comparator.comparingInt(i -> i[2]));

            for (int i = 0; i < n; i++) {
                int x = inputs[i][0];
                int y = inputs[i][1];
                int len = inputs[i][2];

                if (isConnected(x, y)) continue;

                union(x, y);
                minLen += len;
            }

            System.out.println((totalLen - minLen));
        }
    }

    private static boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }

    private static void union(int x, int y) {
        int px = find(x);
        int py = find(y);

        if (px < py) {
            parent[py] = px;
        } else {
            parent[px] = py;
        }
    }

    private static int find(int x) {
        if (parent[x] == x) return x;
        return find(parent[x]);
    }
}


/*8
7 11
0 1 7
0 3 5
1 2 8
1 3 9
1 4 7
2 4 5
3 4 15
3 5 6
4 5 8
4 6 9
5 6 11
0 0
 */