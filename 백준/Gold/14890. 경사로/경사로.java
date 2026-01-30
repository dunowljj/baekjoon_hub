import java.awt.desktop.AboutEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        int[][] grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        List<Nums>[] rows = new ArrayList[N];
        List<Nums>[] cols = new ArrayList[N];

        for (int i = 0; i < N; i++) {
            rows[i] = new ArrayList<>();
            cols[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            int r = 0;
            int c = 0;

            while (c < N) {
                if (rows[i].isEmpty()) {
                    rows[i].add(new Nums(grid[i][c], 1));
                    c++;
                    continue;
                }

                if (rows[i].get(rows[i].size() - 1).val == grid[i][c]) {
                    rows[i].get(rows[i].size() - 1).count++;
                } else {
                    rows[i].add(new Nums(grid[i][c], 1));
                }

                c++;
            }

            while (r < N) {
                if (cols[i].isEmpty()) {
                    cols[i].add(new Nums(grid[r][i], 1));
                    r++;
                    continue;
                }

                if (cols[i].get(cols[i].size() - 1).val == grid[r][i]) {
                    cols[i].get(cols[i].size() - 1).count++;
                } else {
                    cols[i].add(new Nums(grid[r][i], 1));
                }
                r++;
            }
        }

        boolean[][] rowBuilt = new boolean[N][N];
        boolean[][] colBuilt = new boolean[N][N];
        int rowCount = N;
        int colCount = N;

        for (int i = 0; i < N; i++) {
            List<Nums> row = rows[i];

            for (int j = 0; j < rows[i].size() - 1; j++) {
                Nums now = row.get(j);
                Nums next = row.get(j + 1);


                if (now.val + 1 == next.val && now.count >= L) {
                    if (!rowBuilt[i][j]) {
                        rowBuilt[i][j] = true;
                        continue;
                    }

                    if (rowBuilt[i][j] && now.count >= L * 2) {
                        continue;
                    }

                }

                if (now.val - 1 == next.val && next.count >= L){
                    if (!rowBuilt[i][j + 1]) {
                        rowBuilt[i][j + 1] = true;
                        continue;
                    }

                    if (rowBuilt[i][j + 1] && next.count >= L * 2) {
                        continue;
                    }
                }

                rowCount--;
                break;
            }

            List<Nums> col = cols[i];
            for (int j = 0; j < cols[i].size() - 1; j++) {
                Nums now = col.get(j);
                Nums next = col.get(j + 1);

                if (now.val + 1 == next.val && now.count >= L) {
                    if (!colBuilt[i][j]) {
                        colBuilt[i][j] = true;
                        continue;
                    }

                    if (colBuilt[i][j] && now.count >= L * 2) {
                        continue;
                    }
                }
                if (now.val - 1 == next.val && next.count >= L){
                    if (!colBuilt[i][j + 1]) {
                        colBuilt[i][j + 1] = true;
                        continue;
                    }

                    if (colBuilt[i][j + 1] && next.count >= L * 2) {
                        continue;
                    }
                }

                colCount--;
                break;
            }
        }

        System.out.print(rowCount + colCount);
    }

    static class Nums {
        int val;
        int count;

        public Nums(int val, int count) {
            this.val = val;
            this.count = count;
        }
    }
}
