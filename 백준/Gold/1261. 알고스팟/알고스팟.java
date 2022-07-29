import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Room implements Comparable<Room> {
    int x;
    int y;
    int destroy;

    Room(int x, int y, int destroy) {
        this.x = x;
        this.y = y;
        this.destroy = destroy;
    }

    @Override
    public int compareTo(Room o) {
        return destroy - o.destroy;
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        boolean[][] isWall = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                if (input.charAt(j) == '1') {
                    isWall[i][j] = true;
                }
            }
        }


        int answer =  bfs(N,M,isWall);

        bw.write(answer + "");
        bw.flush();
        bw.close();
    }
    static int bfs(int N, int M, boolean[][] isWall) {
        PriorityQueue<Room> queue = new PriorityQueue<>();
        queue.offer(new Room(0, 0, 0));

        boolean[][] visited = new boolean[N][M];
        visited[0][0] = true;

        int[][] mapper = {{-1, 1, 0, 0}, {0, 0, -1, 1}};

        while (!queue.isEmpty()) {

            Room room = queue.poll();
            int x = room.x;
            int y = room.y;
            int des = room.destroy;

            if (x == N - 1 && y == M - 1) {
                return des;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + mapper[0][i];
                int ny = y + mapper[1][i];

                // 인덱스 넘어감 방지
                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;

                if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    // 벽이 있으면 부순 벽 개수 +
                    if (isWall[nx][ny]) {
                        queue.offer(new Room(nx, ny, des + 1));
                    } else {
                        queue.offer(new Room(nx, ny, des));
                    }
                }
                    /*
                    if (nx == N - 1 && ny == M - 1) {
                        return des;
                    }
                    //다음 방문때 값을 리턴해버리면,
                    동일한 벽 개수를 지났고, 마지막에 벽이 뚫린 곳과 막힌 곳이 있다면, 문제가 된다.
                    막힌 곳을 지날때 벽을 카운트하면서 답을 리턴할 가능성이 생기기 떄문이다.

                    */
            }
        }
        return 0;
    }

}