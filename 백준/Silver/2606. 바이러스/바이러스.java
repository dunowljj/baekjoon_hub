import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private final static int[][] mapper = {{1, -1, 0, 0}, {0, 0, 1, -1}};
    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int numOfComputer = Integer.parseInt(br.readLine());
        int numOfPair = Integer.parseInt(br.readLine());

        List<Integer>[] graph = new ArrayList[numOfComputer + 1];
        boolean[] visited = new boolean[numOfComputer + 1];

        for (int i = 1; i <= numOfComputer; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < numOfPair; i++) {
            st = new StringTokenizer(br.readLine());
            int c1 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());

            graph[c1].add(c2);
            graph[c2].add(c1);
        }

        visited[1] = true;
        countInfected(graph, visited, 1);

        System.out.print(answer);
    }

    private static void countInfected(List<Integer>[] graph, boolean[] visited, int start) {
        for (int next : graph[start]) {
            if (!visited[next]) {
                visited[next] = true;
                answer++;
                countInfected(graph, visited, next);
            }
        }
    }
}

/*
노드 연결

 */