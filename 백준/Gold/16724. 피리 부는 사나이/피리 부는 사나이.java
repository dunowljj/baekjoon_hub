import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N,M;

    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        parent = new int[N * M];

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                char dir = line.charAt(j);

                int nr = i;
                int nc = j;

                if (dir == 'U') {
                    nr--;
                } else if (dir == 'D') {
                    nr++;
                } else if (dir == 'L') {
                    nc--;
                } else if (dir == 'R') {
                    nc++;
                }

                int idx = toIdx(i, j);
                int nextIdx = toIdx(nr, nc);

                union(idx, nextIdx);
            }
        }

        Set<Integer> count = new HashSet<>();
        for (int i = 0; i < parent.length; i++) {
            count.add(find(parent[i]));
        }

        System.out.print(count.size());
    }

    private static int find(int idx) {
        if (parent[idx] == idx) return idx;
        else return parent[idx] = find(parent[idx]);
    }

    private static void union(int idx, int nextIdx) {
        int pa = find(idx);
        int pb = find(nextIdx);

        if (pa != pb) {
            parent[pa] = pb;
        }
    }

    private static int toIdx(int i, int j) {
        return i * M + j;
    }
}

/**
 * 밖으로 나가는 움직임은 없다. 즉, union-find를 사용해서 집합을 만들 수 있다.
 * 아니면 양방향 그래프를 만들고 탐색해도 된다.
 *
 */