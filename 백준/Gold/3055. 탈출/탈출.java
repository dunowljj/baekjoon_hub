import java.io.*;
import java.util.*;

class Point6 {
    int x;

    int y;

    public Point6(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    static int R;
    static int C;
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Queue<Point6> queueFlood = new LinkedList<>();
        Queue<Point6> queueHedge = new LinkedList<>();

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        Point6 D = null;
        Point6 S = null;
        visited = new boolean[R][C];
        char[][] forest = new char[R][C];
        for (int i = 0; i < R; i++) {
            String input = br.readLine();
            for (int j = 0; j < C; j++) {
                char curr = input.charAt(j);
                forest[i][j] = curr;

                if (curr == 'D') {
                    D = new Point6(i, j);
                }

                if (curr == 'S') {
                    queueHedge.offer(new Point6(i, j));
                    visited[i][j] = true;
                    forest[i][j] = '.'; // 홍수낼때 편의를 위해 . 으로 변경
                }

                if (curr == '*') {
                    queueFlood.offer(new Point6(i, j));
                }
            }

        }

        int value = bfs(D, forest, queueHedge, queueFlood);
        String answer = value == -1 ? "KAKTUS" : value+"";

        bw.write(answer);
        bw.flush();
        bw.close();
    }

    static int bfs(Point6 D, char[][] forest, Queue<Point6> queueHedge, Queue<Point6> queueFlood) {

        int mapper[][] = {{1, -1, 0, 0}, {0, 0, 1, -1}};

        int second = 0;
        while (!queueHedge.isEmpty()) {
            int size1 = queueHedge.size();
            // 고슴도치 이동
            for (int i = 0; i < size1; i++) {
                Point6 curr = queueHedge.poll();

                // 현재 위치에 물 차오른 경우
                if (forest[curr.x][curr.y] == '*') continue;

                for (int j = 0; j < 4; j++) {
                    int nx = curr.x + mapper[0][j];
                    int ny = curr.y + mapper[1][j];

                    // 인덱스 초과 || 가려는 곳에 물이 차있음 || 돌로 막힌 경우 || 이미 방문한 경우
                    if (nx < 0 || ny < 0 || nx >= R || ny >= C
                            || forest[nx][ny] == '*' || forest[nx][ny] == 'X' || visited[nx][ny]) continue;


                    // 비버굴과 가려는 위치가 같을때
                    if (D.x == nx && D.y == ny) {
                        return second + 1;
                    }

                    visited[nx][ny] = true;
                    queueHedge.offer(new Point6(nx, ny));
                }
            }

            // 물이 차오름 -> 해당 깊이만큼만 물이 차오르도록 조치하고, 다음턴에는 다음 깊이 탐색하도록 설정해야함
            int size2 = queueFlood.size();
            for (int i = 0; i < size2; i++) {
                Point6 curr = queueFlood.poll();

                for (int j = 0; j < 4; j++) {
                    int nx = curr.x + mapper[0][j];
                    int ny = curr.y + mapper[1][j];
                    // 인덱스 초과 || 빈공간 아닌 경우 (물, 돌, 비버 소굴)
                    if (nx < 0 || ny < 0 || nx >= R || ny >= C
                            || forest[nx][ny] != '.') continue;

                    forest[nx][ny] = '*';
                    queueFlood.offer(new Point6(nx, ny));
                }
            }

            second++;
        }
        return -1;
    }

    /*private static void test(char[][] forest) {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                System.out.print(forest[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }*/
}
/*
물이 찰 예정인 곳으로 이동하지 않음
X : 돌
* : 물
D : 비버 굴
S : 고슴도치
D,S는 한개

매 초 물이 차야한다.
고슴도치도 매 초 움직여야한다.

매 초를 구분해서 (깊이마다) 물이 차오르게 하면서 고슴도치를 탐색시키면 된다.
물에 대한 큐, 고슴도치에 대한 큐를 따로 놓기.

고슴도치 큐에 넣고, 큐 사이즈 측정 -> 사이즈만큼 순회 -> 순회 후 혹은 사이즈 측정 후 물에 대한 로직 처리

다른 풀이 : 오히려 물을 먼저 범람시키고, 고슴도치를 이동하는 방식을 사용해
ㅆ다.



 */