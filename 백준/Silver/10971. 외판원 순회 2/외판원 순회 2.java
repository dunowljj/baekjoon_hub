import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] W;
    static boolean[] visited;
    static int[] costs;
    static int firstCity;
    static int min = 10_000_001;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        visited = new boolean[N];
        costs = new int[N - 1];

        //init
        W = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // i는 출발하는 도시 위치
        for (int i = 0; i < N; i++) {
            firstCity = i;
            dfs(0,i);
        }

        bw.write(min+"");
        bw.flush();
        bw.close();
    }

    // i는 이동해서 도착한 도시이며, 다음 재귀에 출발점이 된다.
    static void dfs(int depth, int i) {
        if (depth == N - 1) {
            int backCost = W[i][firstCity];
            if (backCost == 0) return; //돌아가는 비용이 0 이면 길이 없다는 의미이므로 종료.

            int sum = backCost;
            for (int cost : costs) {
                sum += cost;
            }
            min = Math.min(min, sum);
            return;
        }


        for (int j = 0; j < N; j++) {
            if (W[i][j] != 0 && !visited[i] && !visited[j]) {
                visited[i] = true;
                costs[depth] = W[i][j];
                dfs(depth + 1, j);
                visited[i] = false;
            }
        }
    }

}
/*
외판원순회(TSP)

1~N 번호의 도시
W[i][j] i -> j 여행 비용
갈수 없는 도시는 비용 0 처리
W[i][j] != W[j][i] 비용 비대칭
항상 순회할 수 있는 경우만 입력으로 주어진다.

한 번 들린 도시 다시 안감. -> 최단경로 크루스칼? x -> 갔던 도시를 못간다는 것은, 현재 위치를 고려해야하므로, dfs 완전탐색
전체를 순회하면서 모든 경우의 수를 확인한다. -> W[i][j] != 0 && !visited[i] && !visited[j] 일때 W[i][j]를 선택하면, i도시 에서 j도시로 이동한다는 의미다.
-> i,j 는 방문처리가 되며, 도착했을때의 j는 재귀해서 출발점(i)이 된다.

출발한 도시에만 방문 체크, 다음 도시로 이동하면 이전 도시만 체크되어 있다. 마지막 도시에 가면 이미 다른도시들이 다 방문체크되어 있어서 depth = N -1로 종료

다시 원래 도시로 돌아와야 한다 -> 맨 처음 도시 인덱스를 저장했다가 돌아오는 길의 비용을 찾는데 사용한다.


100만이하 최대 10개 -> 10_000_000 최댓값
 */