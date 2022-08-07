import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Coin {
    int x;
    int y;
    int move = 0;
    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coin(int x, int y, int move) {
        this.x = x;
        this.y = y;
        this.move = move;
    }
}
public class Main {
    static boolean[][] canGo;
    static int N;
    static int M;
    static int answer = -1;
    static boolean[][][][] visited;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        visited = new boolean[N][M][N][M];

        int[][] coins = new int[2][3];
        canGo = new boolean[N][M];
        int count = 0;
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                char ch = input.charAt(j);
                // 동전 위치 저장, 갈수 있는 곳 표시
                if (ch == 'o') {
                    canGo[i][j] = true;
                    coins[count++] = new int[]{i, j};
                } else if (ch == '.') {
                    canGo[i][j] = true;
                }
            }
        }

        if (N == 1 && M == 1) {
            // do nothing
        } else {
            bfs(coins);
        }

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }

    static void bfs(int[][] coins) {
        int[] c1 = coins[0];
        int[] c2 = coins[1];

        //쓸데없이 move가 2개 만들어지는 문제
        Queue<Coin[]> queue = new LinkedList<>();
        queue.add(new Coin[]{new Coin(c1[0], c1[1]), new Coin(c2[0], c2[1])});

        /*Queue<int[]> q1 = new LinkedList<>();
        Queue<int[]> q2 = new LinkedList<>();*/
        visited[c1[0]][c1[1]][c2[0]][c2[1]] = true;
        /*q1.add(coin1);
        q2.add(coin2);*/

        int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};

        while (!queue.isEmpty()) {
//        while (!q1.isEmpty() || !q2.isEmpty()) {
//            coin1 = q1.poll();
//            coin2 = q2.poll();
            Coin[] twoCoins = queue.poll();
            Coin coin1 = twoCoins[0];
            Coin coin2 = twoCoins[1];

            for (int i = 0; i < 4; i++) {
                int x1 = coin1.x;
                int y1 = coin1.y;
                int nx1 = x1 + mapper[0][i];
                int ny1 = y1 + mapper[1][i];
                int cnt1 = coin1.move;

                if (cnt1 >= 10) {
                    return;
                }

                int x2 = coin2.x;
                int y2 = coin2.y;
                int nx2 = x2 + mapper[0][i];
                int ny2 = y2 + mapper[1][i];
                int cnt2 = coin2.move;

//                int x1 = coin1[0];
//                int y1 = coin1[1];
//                int nx1 = x1 + mapper[0][i];
//                int ny1 = y1 + mapper[1][i];
//                int cnt1 = coin1[2];
//
//                if (cnt1 > 10) {
//                    return;
//                }
//
//                int x2 = coin2[0];
//                int y2 = coin2[1];
//                int nx2 = x2 + mapper[0][i];
//                int ny2 = y2 + mapper[1][i];
//                int cnt2 = coin2[2];



                // 떨어진 동전 확인
                int fallCount = 0;

                if (isFallenCoin(nx1, ny1)) fallCount++;
                if (isFallenCoin(nx2, ny2)) fallCount++;

                if (onlyOneCoinFallen(fallCount)) {
                    answer = cnt1 + 1;
                    return;

                } else if (bothCoinsFallen(fallCount)) {
                    continue;
                }

                if (!canGo[nx1][ny1]) {
                    nx1 = x1;
                    ny1 = y1;
                }

                if (!canGo[nx2][ny2]) {
                    nx2 = x2;
                    ny2 = y2;
                }

                if (!visited[nx1][ny1][nx2][ny2]) {
                    visited[nx1][ny1][nx2][ny2] = true;

                    queue.add(new Coin[]
                            {new Coin(nx1, ny1, cnt1 + 1),
                            new Coin(nx2, ny2, cnt2 + 1)});


                    // 하나도 안떨어진 경우 이동
//                        q1.add(new int[]{nx1, ny1, cnt1 + 1}); //빈곳이나 코인이면 이동
//                        q2.add(new int[]{nx2, ny2, cnt2 + 1});
                }
            }
        }
    }

    private static boolean onlyOneCoinFallen(int fallenCount) {
        return fallenCount == 1;
    }
    private static boolean bothCoinsFallen(int fallenCount) {
        return fallenCount == 2;
    }
    private static boolean isFallenCoin(int x, int y) {
        return x < 0 || y < 0 || x >= N || y >= M;
    }
}
/*
동전 : 버튼 클릭 시 두개가 한꺼번에 상하좌우 이동
벽이면 이동x
보드 바깥으로 떨어질 수 있다. -> 인덱스 넘으면 떨어짐
이동하려는 칸에 동전이 있어도 한 칸 이동

<두 동전 중 하나만 떨어뜨리기 위해 몇 번 클릭?>

1<=N,M<=20
o 동전 / . 빈칸 / # 벽

두 동전 떨어뜨릴 수 없거나 10번 이상 눌러야 하면 -1 출력

벽을 이용해 한 개의 동전만 움직일 수도 있다. -> 벽을 마주하면 이동횟수만 ++ 하기 아닐 경우 위치도 갱신

 */