import java.io.*;
import java.util.*;

public class Main {
    static int W;
    static int H;
    static int[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        List<int[]> projector = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        visited = new int[W][H];
        char[][] board = new char[W][H];

        for (int i = 0; i < W; i++) {
            String input = br.readLine();
            for (int j = 0; j < H; j++) {
                visited[i][j] = Integer.MAX_VALUE;

                char curr = input.charAt(j);
                board[i][j] = curr;

                if (curr == 'C') {
                    projector.add(new int[]{i, j, -1, -1}); // x,y, 방향,거울  -> 방향이 처음에 다르므로 거울 -1 시작
                }
            }
        }

        bw.write(bfs(projector, board) + "");
        bw.flush();
        bw.close();
    }

    private static int bfs(List<int[]> projector, char[][] board) {
        // 우선순위큐, 거울개수 기준으로 오름차순 정렬
        Queue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[3] - o2[3];
            }
        });


        // 시작부분, 'C' 정보 둘중 하나 넣기
        int[] start = projector.get(0);
        queue.offer(start);

        // 시작부분 'C' -> '.'로 변경,
        board[start[0]][start[1]] = '.';

        // 방문여부(거울 수) 0으로 설정
        visited[start[0]][start[1]] = 0;

        int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};

        while (!queue.isEmpty()) {

                int[] curr = queue.poll();
                int x = curr[0];
                int y = curr[1];
                int dir = curr[2];
                int mirror = curr[3];

                if (board[x][y] == 'C') {
                    return mirror;
                }

                for (int i = 0; i < 4; i++) {
                    int nx = x + mapper[0][i];
                    int ny = y + mapper[1][i];
                    int nm = curr[3];

                    // 인덱스 초과 || 다음이 벽인 경우
                    if (nx < 0 || ny < 0 || nx >= W || ny >= H
                            || board[nx][ny] == '*') continue;

                    // 방향이 바뀌었다면 거울 수 추가
                    if (i != dir) {
                        nm++;
                    }

                    /*
                     가려는 곳의 이전 방문 거울 수가 더 적은 경우 -> 같다는 추가해선 안된다.
                     꺾이는 지점의 좌표일때, 거울 개수가 추가되는게 아니라, 꺾여서 이동한 위치에 거울 개수가 추가된다.

                     아래 두 케이스를 살펴보자.

                     case 1
                        0 1 2 3 4 5
                      1 . . . . /-C
                      0 C ------/ .

                     case 2
                        0 1 2 3 4 5
                      1 /---------C
                      0 C . . . . .

                     case1먼저 탐색이 이뤄지고 그다음 case2와 같은 탐색이 이뤄진다고 가정하자.

                     1) case 1이 먼저 탐색을 했을 때, visited[1][4]의 값은 1이다.

                     2) case 2 [1,3]에서 다음 칸인 [1,4]을 탐색할때를 생각해보자.
                     이때 [1,3]까지 설치된 거울 개수는 1이고, visited[1][4]도 1이다.
                     if (visited[nx][ny] <= nm) continue와 같이 거울 수가 같은 경우를 continue 해버리면 최소경로를 스킵할 가능성이 생기는 것이다.
                     즉, 우선순위큐를 사용했음에도 방향을 바꾸자마자 도착했기때문에 정답이 거울 2개로 나올 수 있다.
                     */
                    if (visited[nx][ny] < nm) {
                        continue;
                    }

                    visited[nx][ny] = nm;
                    queue.offer(new int[]{nx, ny, i, nm});

//                    test(visited);
                }
        }
        return 0;
    }

    private static void test(int[][] visited) {
        for (int i = 0; i < W; i++) {
            for (int j = 0; j < H; j++) {
                if (visited[i][j] != -1) {
                    System.out.print("o ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
/*
## 시간복잡도
V + E => N제곱 + a * N 제곱 -> O(N^2)


## 풀이
꺾이는 부분마다 거울 설치
4방향 탐색 시 이전방향과 같지 않으면, 거울 개수 카운트하기
문제 : 공간이 넓을때, 괜히 구불구불하게 간다면?
해결 :
    방법1 : 인덱스를 나누기 연산하고, 같은 방향부터 탐색시키기 -> 실패
    방법2 : 거울의 개수가 적은 경우를 우선하기

아기상어 문제도 그렇고, 탐색 순서로 해결하려고 하면 문제가 생긴다. 반례가 무엇일까?
방법 1 반례 : !같은 방향만 먼저 탐색하면, 같은 방향으로 가는 경우가 정답이 아닌 경우 문제가 발생한다. 해당 경우를 먼저 탐색해서 다른 정답이 될 길이 막힐 수 있다.

방법 2 주의 : !!!멀리 돌아서 도착하는 길이 거울수가 더 적을수도 있다. 그 점을 유의해야한다. 우선순위 큐를 활용해 거울 수가 적은것부터 처리하자.
 */
