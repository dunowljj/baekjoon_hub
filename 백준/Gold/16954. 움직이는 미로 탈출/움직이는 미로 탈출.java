import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

class Location {
    int x;
    int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        boolean[][] isWall = new boolean[8][8];

        for (int i = 0; i < 8; i++) {
            String input = br.readLine();
            for (int j = 0; j < 8; j++) {
                if (input.charAt(j) == '#') {
                    isWall[i][j] = true;
                }
            }
        }

       
        bw.write( bfs(7,0, isWall)+"");
        bw.flush();
        bw.close();
    }

    static int bfs(int x, int y, boolean[][] isWall) {
        Queue<Location> queue = new LinkedList<>();
        queue.add(new Location(x, y));

        // 제자리, 좌, 우, 상,하, 오른쪽 대각 , 왼쪽 대각
        int[] mx = {0, 0, 0, 1, -1, -1, -1, 1, 1};
        int[] my = {0, -1, 1, 0, 0, 1, -1, 1, -1};
        boolean[][] visited;

        while (!queue.isEmpty()) {
            int size = queue.size();
            visited = new  boolean[8][8];
            for (int i = 0; i < size; i++) {
                Location location = queue.poll();

//                // 내려온 벽에 충돌
//                if (isWall[x][y] == true) {
//                    continue;
//                }

                for (int j = 0; j < 9; j++) {
                    int nx = location.x + mx[j];
                    int ny = location.y + my[j];

                    // 인덱스 초과 / 다음 좌표가 벽이면 이동 불가
                    if (isOutOfIndex(nx, ny) || isWall[nx][ny] || visited[nx][ny]) continue;

                    // 이동 후 맨위이면 도착 가능 (어짜피 벽이 다떨어질것)
                    if (nx == 0) {
                        return 1;
                    }

                    // 윗칸이 벽이면 어짜피 다음번에 충돌 (벽이 내려옴), 인덱스 초과 주의
                    if (isWall[nx - 1][ny]) {
                        continue;
                    }

                    visited[nx][ny] = true;
                    queue.offer(new Location(nx, ny));
                }
            }
            wallFalling(isWall);
        }
        return 0;
    }

    private static void wallFalling(boolean[][] isWall) {
        // 1~7행 -> 2~8행에 붙여넣기
        for (int i = 7; i >= 1; i--) {
            System.arraycopy(isWall[i - 1], 0, isWall[i], 0, 8);
        }

        // 맨 윗줄 빈공간으로 초기화
        for (int i = 0; i < 8; i++) {
            isWall[0][i] = false;
        }
    }

    private static boolean isOutOfIndex(int nx, int ny) {
        return nx < 0 || ny < 0 || nx >= 8 || ny >= 8;
    }
}
/*
8*8 체스판

- 옆칸 이동 or 대각선 이동 or 제자리
- 8초동안 살아남는다면, 도착가능.
- 먼저 이동 후, 바로 위에 벽이 있다면 실패. continue

캐릭터를 전진시키는 방식을 사용하면, 벽이 사라지는 경우를 구현하기가 힘들다.
그냥 배열을 계속 갱신하는게 마음 편할 것 같다. 애초에 8*8배열이라 그렇게 메모리나 시간이 크게 소모되지도 않는다.

- arraycopy를 활용해서 1번째 행부터 7번째 행까지를 2~8행에 붙여넣는다. 첫열은 벽 없음으로 초기화한다.
- bfs를 사용하기때문에 깊이가 바뀔때마다 벽이 떨어지도록 갱신해야한다. 이전 깊이와 현재 깊이를 비교해서 깊이가 바뀌면 벽이 떨어진다.

 */