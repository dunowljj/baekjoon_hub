/**
 * 방법 1 : 인접리스트를 초기화하고, 제거 여부를 확인하는 removed 배열을 만들어 dfs로 탐색하며 지워야할 부분을 제거한다.
 * - 인접리스트를 초기화하고, dfs로 순회하면서 제거 여부를 removed(boolean) 배열에 갱신한다.
 * - 인접리스트를 순회하면서 리프노드를 카운트한다. 다음 두 경우를 리프노드로 간주한다.
 *   1) 특정 노드와 인접한(자식인) 노드의 리스트가 비어있거나 
 *   2) 특정 노드와 인접한(자식인) 노드가 1개인데, 해당 노드가 삭제된 경우
 */

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

        System.out.print(countLeaf(adjList, needRemoveNo, isRemoved));
    }

    // 부모가 아니고, 부모가 지워지지 않는 노드

    private static void checkLowerNodes(int needRemoveNo, List<Integer>[] adjList, boolean[] removed) {
        removed[needRemoveNo] = true;
        for (int child : adjList[needRemoveNo]) {
            checkLowerNodes(child, adjList, removed);
        }
    }
    
    private static int countLeaf(List<Integer>[] adjList, int needRemoveNo, boolean[] isRemoved) {
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
        return leafCount;
    }
}

/**
* 방법 2: 주어지는 부모 노드의 정보를 이용해서 풀기.
* - dfs 특정 노드가 삭제되면 해당 노드의 부모 값이 들은 배열에 값을 -2(지워짐 표시)로 갱신한다.
* - 후에 특정 노드가 리프노드인지 검사할 때, "특정 노드가 누군가의 부모인지"를 검사한다. 즉, 부모 배열을 처음부터 끝까지 순회하면서 요소의 값이 특정 노드 값과 같다면 리프노드이다.
* - 방법 1의 2)는 예외
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static final int REMOVED = -2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = Integer.parseInt(st.nextToken());
        }

        int needRemoveNo = Integer.parseInt(br.readLine());
        removeNodes(needRemoveNo, parent);

        Set<Integer> parentSet = Arrays.stream(parent)
                .boxed()
                .collect(Collectors.toSet());

        System.out.print(countLeaf(parent, parentSet));
    }

    private static void removeNodes(int needRemoveNo, int[] parent) {
        parent[needRemoveNo] = REMOVED;
        for (int childNo = 0; childNo < parent.length; childNo++) {
            if (parent[childNo] == needRemoveNo) {
                removeNodes(childNo, parent);
            }
        }
    }

    private static int countLeaf(int[] parent, Set<Integer> parentSet) {
        int leafCount = 0;
        for (int childNo = 0; childNo < parent.length; childNo++) {
            if (parent[childNo] != REMOVED && hasNoChild(parentSet, childNo)) {
                leafCount++;
            }
        }
        return leafCount;
    }

    // 해당 노드가 부모노드가 아님
    // -> 해당 노드의 자식 노드가 없다 + 지워진 노드의 경우 부모노드를 참조한다는 사실 자체를 지웠기때문에
    private static boolean hasNoChild(Set<Integer> parentSet, int childNo) {
        return !parentSet.contains(childNo);
    }
}
