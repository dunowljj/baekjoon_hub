import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        List<Integer>[] adjList = new ArrayList[N];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int parentNo = Integer.parseInt(st.nextToken());

            if (parentNo != -1) adjList[parentNo].add(i);
        }

        int needRemoveNo = Integer.parseInt(br.readLine());
        boolean[] isRemoved = new boolean[N];
        checkLowerNodes(needRemoveNo, adjList, isRemoved);

        int leafCount = 0;

        for (int i = 0; i < adjList.length; i++) {
            if (!isRemoved[i] && adjList[i].isEmpty()) {
                leafCount++;
            }

            // 지워진 노드의 부모 노드가 리프노드가 될 가능성이 있다.
            // 자식 노드가 1개인데 그게 지워진 노드인 경우
            if (adjList[i].size() == 1 && adjList[i].get(0) == needRemoveNo) {
                leafCount++;
            }
        }


        System.out.print(leafCount);
    }

    private static void checkLowerNodes(int needRemoveNo, List<Integer>[] adjList, boolean[] removed) {
        removed[needRemoveNo] = true;
        for (int child : adjList[needRemoveNo]) {
            checkLowerNodes(child, adjList, removed);
        }
    }
}
