import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    final static int INF = 16_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        int[][] dist = new int[V + 1][V + 1];
        for (int i = 1; i < dist.length; i++) {
            Arrays.fill(dist[i], INF);
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            dist[a][b] = c;
        }

        for (int k = 1; k <= V; k++) {
            for (int a = 1; a <= V; a++) {
                for (int b = 1; b <= V; b++) {
                    if (dist[a][b] > dist[a][k] + dist[k][b]) {
                        dist[a][b] = dist[a][k] + dist[k][b];
                    }
                }
            }
        }

        int min = INF;
        for (int i = 1; i <= V; i++) {
            min = Math.min(min, dist[i][i]);
        }

        if (min == INF) {
            System.out.print(-1);
        } else {
            System.out.print(min);
        }
    }
}
/**
 * 1~V번 마을
 * 단방향 도로, 두 지점 왕복도 사이클로 친다.
 * 쌍이 같은 도로 여러번 주어지지 않음
 * 방문했던 곳을 또 가도 상관은 없다.
 *
 * 시작점 -> 시작점 돌아오는 가장 작은 사이클 찾기
 *
 * 최단경로, 도착이 시작점인 조건으로 탐색하기
 *
 */