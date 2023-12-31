import java.util.ArrayList;
import java.util.List;

public class Solution {
       
    private int wireCount;

    public int solution(int n, int[][] wires) {
        this.wireCount = wires.length;
        int answer = Integer.MAX_VALUE;
        for (int excludeNo = 0; excludeNo < wireCount; excludeNo++) {
            int count = count(wireCount, wires, excludeNo);

            if (count == 0 || n - count == 0) continue;
            int diff = Math.abs((n - count) - count);
            answer = Math.min(answer, diff);
        }

        return answer;
    }

    private int count(int wireCount, int[][] wires, int exclude) {
        List<Integer>[] adjList = new ArrayList[101];

        for (int i = 0; i < 101; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < wireCount; i++) {
            if (exclude == i) continue;

            int a = wires[i][0];
            int b = wires[i][1];

            adjList[a].add(b);
            adjList[b].add(a);
        }

        boolean[] visited = new boolean[101];
        visited[wires[0][0]] = true;
        return count(adjList, visited, wires[0][0]);
    }

    private int count(List<Integer>[] adjList, boolean[] visited, int start) {
        int count = 1;

        for (int next : adjList[start]) {
            if (!visited[next]) {
                visited[next] = true;
                count += count(adjList, visited, next);
            }
        }

        return count;
    }
}
/**
 */
