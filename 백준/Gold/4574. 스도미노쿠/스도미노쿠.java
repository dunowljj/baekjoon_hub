import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[][] grid;
    static boolean[][] visited;
    static int puzzleIdx = 1;
    static int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};

    // 수열 중복 제거용
    static boolean isFinished;
    static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;



        while (true) {
            int N = Integer.parseInt(br.readLine());
            if (N == 0) break;

            grid = new int[9][9];
            visited = new boolean[10][10];

            // 수열에서 같은 수 선택 못하게 미리 방지
            for (int i = 0; i < 9; i++) {
                visited[i][i] = true;
            }

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int U = Integer.parseInt(st.nextToken());
                String LU = st.nextToken();

                int rowL = LU.charAt(0) - 'A';
                int colL = LU.charAt(1) - '0';

                grid[rowL][colL - 1] = U;


                int V = Integer.parseInt(st.nextToken());
                String LV = st.nextToken();

                int rowV = LV.charAt(0) - 'A';
                int colV = LV.charAt(1) - '0';

                grid[rowV][colV - 1] = V;

                // 방문체크를 통해 해당 수열 다시 사용 불가하게 처리 0~8로 처리
                visited[U][V] = true; visited[V][U] = true;

            }

            // 1개짜리 입력받기
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= 9; i++) {
                String loc = st.nextToken();
                int row = loc.charAt(0) - 'A';
                int col = loc.charAt(1) - '0';

                grid[row][col - 1] = i;
            }

            /*int count = 0;
            for (int[] ints : grid) {
                for (int anInt : ints) {
                    if (anInt != 0 ) count++;
                    System.out.print(anInt + " ");
                }
                System.out.println();
            }
            System.out.println();

            System.out.println("count : " + count);*/

            isFinished = false;
            dfs(0,0);

        }

        bw.write(answer.toString().trim());
        bw.flush();
        bw.close();
    }

    private static void dfs(int row, int col) throws IOException {
        if (isFinished) {
            return;
        }

        // 열 끝에 도달 시 다음 행으로 이동
        if (col == 9) {
            dfs(row + 1, 0);
            return;
        }

        // 모두 채울 시 종료
        if (row == 9) {
            isFinished = true;

            answer.append("Puzzle ").append(puzzleIdx).append("\n");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    answer.append(grid[i][j]);
                }
                answer.append("\n");
            }
            puzzleIdx++;
            return;
        }

        // 빈 grid인 경우 채우기 위해 접근
        if (grid[row][col] == 0) {

            // 1~9중 가능한 숫자 탐색
            for (int i = 1; i <= 9; i++) {
                if (isPossible(row, col, i)) {
                    grid[row][col] = i;

                    // 인접한 퍼즐 탐색하기
                    for (int j = 0; j < 4; j++) {
                        int nr = row + mapper[0][j];
                        int nc = col + mapper[1][j];

                        // 인덱스 넘어가는 경우 처리
                        if (nr < 0 || nc < 0 || nr >= 9 || nc >= 9) {
                            continue;
                        }

                        // 인접한 곳에 0이 있으면, 검사해서 숫자 집어넣기. 방문여부(수열중복)도 확인
                        if (grid[nr][nc] == 0) {
                            for (int k = 1; k <= 9; k++) {
                                if (isPossible(nr, nc, k) && !visited[i][k]) {

                                    grid[nr][nc] = k;
                                    visited[i][k] = true;
                                    visited[k][i] = true;

                                    dfs(row, col + 1);
                                    if (isFinished) return;

                                    grid[nr][nc] = 0;
                                    visited[i][k] = false;
                                    visited[k][i] = false;
                                }
                            }
                        }

                    } //인접 타일 탐색 end
                }
            }

            grid[row][col] = 0;
            return;
        }
        dfs(row, col + 1);
    }

    private static boolean isPossible(int row, int col, int num) {
        // 행 검사
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // 열 검사
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        // 사각형 검사
        row = row / 3 * 3;
        col = col / 3 * 3;

        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3 ; j++) {
                if (grid[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
/*
답이 유일한 경우만 주어진다.

# 풀이
### 수열 중복 제거 방법?
boolean 배열로 제거. 배열이 x2가 되는 비효율

스도쿠 문제와 동일하나, 중복되지 않은 수열도 검사해서 숫자를 넣어야 한다는 점이 다르다.


각 인덱스를 탐색 -> 들어갈 수 있는 수 찾기 -> 해당 위치부터 인접한 위치 탐색
-> 모두 막혔으면 실패, 공간 있으면 채우고 탐색(중복 아닌 수열만 입잡가능)



 */