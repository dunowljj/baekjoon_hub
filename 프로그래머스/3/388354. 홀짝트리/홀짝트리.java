import java.util.*;

class Solution {
    
    List<Integer>[] adj;
    Set<Integer> checked = new HashSet<>();
    
    int groupNoSeq = 0;
    Map<Integer, Integer> nodeToGroup = new HashMap<>(); // node -> groupNo    
    Map<Integer, List<Integer>> groupToNodes = new HashMap<>(); // groupNo -> nodes  

    public static final int FORWARD_TREE = 0;
    public static final int REVERSE_TREE = 1;
    
    public int[] solution(int[] nodes, int[][] edges) {
        int[] answer = new int[2];
        int n = 1_000_000;
        
        adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) adj[i] = new ArrayList<>();
        
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        
        for (int root : nodes) {
            grouping(root);
        }
        
//         for (Map.Entry<Integer, List<Integer>> e : groupToNodes.entrySet()) {
//             System.out.print("["+e.getKey()+"]: ");
            
//             for (int no : e.getValue()) {
//                 System.out.print(no +" ");
//             }
//             System.out.println();
//         }
        
        for (List<Integer> tree : groupToNodes.values()) {
            // 루트가 아닐때 색깔 세기
            int yellow = 0;
            int red = 0;
            
            // 각 그룹(트리)를 1회만 탐색하고, 각 노드에 대한 색깔정보 배열을 만든다.
            for (int nodeNo : tree) {
                boolean isYellowWhenNotRoot = (nodeNo % 2 == (adj[nodeNo].size() - 1) % 2);
                
                if (isYellowWhenNotRoot) yellow++;
                else red++;
            }
            
            // yello1개 -> 모두 red로 만들 수 있음 -> 역홀짝
            if (yellow == 1) {
                answer[REVERSE_TREE]++;
            }
            
            // red1개 -> 모두 yellow -> 홀짝
            if (red == 1) {
                answer[FORWARD_TREE]++;
            }
        }
        
        return answer;
    }
    
    private void grouping(int root) {
        if (nodeToGroup.containsKey(root)) {
            return;
        }
        
        nodeToGroup.put(root, groupNoSeq);
        List<Integer> group = new ArrayList<>();
        group.add(root);
        
        groupingNodes(root, group);
        
        groupToNodes.put(root, group);
        groupNoSeq++;
    }
    
    private void groupingNodes(int now, List<Integer> group) {
        for (int next : adj[now]) {
            if (!nodeToGroup.containsKey(next)) {
                nodeToGroup.put(next, groupNoSeq);
                group.add(next);
                
                groupingNodes(next, group);    
            }
        }   
    }   
}
/**
홀수 : 번호 호룻, 자식 홀수
짝수 : 짝,짝
역홀 : 홀,짝
역짝 : 짝,홀

자식이 0개 -> 짝수로 판정
특정 노드를 루트로 삼고, 한 depth씩 탐색해야한다.

nodes 40만개, 원소가 중복되지 않음
edges 100만

1.그래프 초기화
2.각 노드를 루트로 삼고 1depth씩 탐색
연산 횟수가 4 * 10^11로 너무 크다.

- 어디를 루트로 삼느냐에 따라 결과가 달라짐.
- 트리가 여러개라서 각각 판별해서 세줘야한다.

탐색하면서 그룹을 나눠줘야하나?
edge중복은 없겠지?

두가지 모두 되는 경우도 존재한다.

한번 탐색만으로 노드의 색깔을 판별할 수 있지 않을까?
예시 1의 3을 보자.
- 3의 인접 노드 수는 3개이다.
- 1) 3이 루트이면 값3, 자식 3 -> 홀짝 - YelloNode이다.
- 2) 3이 루트가 아니면, 값3, 자식 2 -> 역홀짝 - RedNode이다.

!!노드의 값을 고정이기때문에, 루트일때와 루트가 아닐때 색깔이 같을수가 없다. 루트가 아닐때는 자식수가 정확히 -1이기 때문이다.

그렇다면 각 트리(그룹)에 대해 루트가 아닐때 색깔을 모두 세준다. 그중 다른 색이 정확히 1개만 있는 경우, 루트로 전환하면 홀짝||역홀짝 트리를 만들 수 있다.

[역 역 역 정] -> r가능

### 길이 == 2
- 두가지 모두 될 수 있는 경우는 길이가 2일때를 말하는 듯
- [역 홀] -> r,y가능

### 길이 == 1
노드 1개 -> 자식이 무조건 0개
노드값이 짝수 -> [정] -> y
노드값이 홀수 -> [역] -> r
*/