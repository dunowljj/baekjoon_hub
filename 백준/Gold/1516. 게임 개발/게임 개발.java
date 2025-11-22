import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static List<Integer>[] afterGraph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] buildTime = new int[N + 1]; // 건설 시간 배열
        int[] completeTime = new int[N + 1]; // 건설 시간 배열
        int[] in = new int[N + 1]; // 진입 차수, 선행 노드 개수를 카운트하는 배열 -> 0이면 리프, 건설 가능
        afterGraph = new ArrayList[N + 1]; // 선행 노드로 after 연관된 노드를 검색하기 위한 그래프
        for (int i = 0; i < afterGraph.length; i++) afterGraph[i] = new ArrayList<>();

        for (int no = 1; no <= N; no++) {
            st = new StringTokenizer(br.readLine());

            int time = Integer.parseInt(st.nextToken());
            buildTime[no] = time;

            int pre = 0;
            while ((pre = Integer.parseInt(st.nextToken())) != -1) {
                afterGraph[pre].add(no);
                in[no]++;
            }
        }

        int unCompletedCount = N;
        int currTime = 0;
        while (unCompletedCount > 0) {

            // 건설 시작이 준비된 모든 건물(leaf)를 찾는다.
            // 그 중 가장 건설 시간이 짧은 경우를 찾는다.
            List<Integer> leaves = new ArrayList<>();
            int min = Integer.MAX_VALUE;
            for (int no = 1; no <= N; no++) {
                if (in[no] == 0 && buildTime[no] > 0) {
                    leaves.add(no);
                    min = Math.min(min, buildTime[no]);
                }
            }

            currTime += min;
            // 가장 짧은 건설 시간만큼 시간이 흐른다.
            // 건설이 완료된 경우 처리한다.
            for (int i = 0; i < leaves.size(); i++) {
                int leafNo = leaves.get(i);
                buildTime[leafNo] -= min;

                // 건설 완료!
                if (buildTime[leafNo] == 0) {
                    unCompletedCount--;
                    completeTime[leafNo] = currTime;

                    // leaf를 선행으로 필요로 하던 노드들을 찾고, 해당 노드들의 진입 차수를 줄여준다.
                    for (int after : afterGraph[leafNo]) {
                        in[after]--;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < completeTime.length; i++) {
            sb.append(completeTime[i]).append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());

    }

    static class Building {
        int time;
    }
}
/**
 * 위상정렬을 기반으로 하되, 시간이라는 개념을 포함시킨다.
 *
 * pre가 없는 리프노드를 모아놓는다. 그 중 시간이 적은 노드부터 처리한다.
 * -> 시간은 모든 건설 가능한 건물에서 동시에 차감되어야 한다.
 *
 * 리프노트를 제거할 때, 해당 리프노드 번호 기준으로 after인 노드를 찾을 수 있어야 함.
 */