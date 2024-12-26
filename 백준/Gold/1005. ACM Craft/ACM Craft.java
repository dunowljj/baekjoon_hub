import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {

            st  = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());

            // 입력되는 건물 번호와 일치시키기 위해 인덱스 1~N 사용
            int[] buildTime = new int[N + 1];
            for (int no = 1; no <= N; no++) {
                int time = Integer.parseInt(st.nextToken());
                buildTime[no] = time;
            }

            /**
             * 건설 규칙으로 그래프 생성
             */
            List<Integer>[] graph = new ArrayList[N + 1];
            for (int no = 1; no <= N; no++) {
                graph[no] = new ArrayList<>();
            }

            for (int j = 0; j < K; j++) {
                st = new StringTokenizer(br.readLine());

                int before = Integer.parseInt(st.nextToken());
                int after = Integer.parseInt(st.nextToken());

                graph[after].add(before); // 선행 건물을 찾을수 있도록 초기화
            }

            // 건설해야할 건물
            int W = Integer.parseInt(br.readLine());

            /**
             * 그래프 탐색
             */
            // 선행 건물이 없는 경우 dp 배열에 값 채워놓기
            Integer[] needTime = new Integer[N + 1];
            for (int no = 1; no <= N; no++) {
                if (graph[no].isEmpty()) {
                    needTime[no] = buildTime[no];
                }
            }

            System.out.println(
                    findBuildTimeOf(W, graph, buildTime, needTime)
            );
        }
    }

    // 건설 시간이 0일수도있음
    private static int findBuildTimeOf(int now, List<Integer>[] graph, int[] buildTime, Integer[] needTime) {
        if (needTime[now] != null) {
            return needTime[now];
        }

        needTime[now] = 0;
        for (int before : graph[now]) {
            needTime[now] = Math.max(
                    needTime[now],
                    findBuildTimeOf(before, graph, buildTime, needTime)  + buildTime[now]
            );
        }

        return needTime[now];
    }
}
