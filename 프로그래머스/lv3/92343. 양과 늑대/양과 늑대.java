import java.util.*;
import java.util.stream.*;


class Solution {

    static final int SHEEP = 0;
    int answer = 0; 

    public int solution(int[] info, int[][] edges) {
        List<Integer>[] adjList = new ArrayList[info.length];
        
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
        }
        
        List<Integer> nexts = new ArrayList<>();
        nexts.add(0);

        dfs(0, adjList, info, 0, 0, nexts);
        
        return answer;
    }
    
    private void dfs(int start, List<Integer>[] adjList, int[] info, int sheep, int wolf, List<Integer> nexts) {
        
        if (info[start] == SHEEP) {
            sheep ++;
            answer = Math.max(sheep, answer);
        } 
        else {
            wolf ++;
            if (sheep <= wolf) return;
        }

        // 다음에 탐색할 범위 List 만들기
        List<Integer> newNexts = new ArrayList<>();
        newNexts.addAll(nexts);
        newNexts.remove((Integer)start); // remove는 오버로딩되어 있어서 인수로 int값을 그냥 넣으면 해당 인덱스의 값을 삭제한다.
        newNexts.addAll(adjList[start]);
        
        for (int next : newNexts) {
            dfs(next, adjList, info, sheep, wolf, newNexts);
        }
    }
}
/*
잡아먹히지 않고 최대한 많은 양 데려오기
최대 양 구하기
0번이 루트 노드이다.

0 양
1 늑대


linkedList 형태로 연결하는게 나을까?
-> adjList로 만드는게 배열로 임의 접근도 되고 나아보인다.

edges는 [부모 - 자식] 형태로 주어진다. 애초에 순서를 정렬하거나 신경쓸 필요가 없으며, 단방향으로 연결된 인접리스트를 만들 수 있다.

1) 노드 번호로 해당 노드의 자식노드들을 찾을 수 있는 단방향 그래프(인접리스트) 생성
2) dfs로 탐색한다. 탐색시마다 다음 탐색위치를 list에 담아서 갱신해줘야 한다. 그 이유는,
- 그렇지 않고 단순 dfs를 사용하면 돌아가는 경우때문에 연산수가 기하급수적으로 증가한다.
- 양을 더 모으면 늑대가 있는 곳을 지나갈 수 있는 경우가 있다. 그런 경우 때문에 방문 체크로 해결하기 어렵다.
3) 양이 합류할때마다 max 값을 갱신한다.
*/