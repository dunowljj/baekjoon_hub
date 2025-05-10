import java.util.*;

class Solution {
    
    private List<Integer>[] adj;
    private int n;
    private int answer = 0;
    
    public int solution(int[] info, int[][] edges) {
        n = info.length;
        
        adj = new ArrayList[n + 1];
        for (int i = 0; i < adj.length; i++) adj[i] = new ArrayList<>();
        
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
        }
        
        dfs(0, new ArrayList<>(List.of(0)), 0, 0, info);
        
        return answer;
    }
    
    private void dfs(int now, List<Integer> nexts, int sheep, int wolf, int[] info) {
        if (info[now] == 0) {
            sheep++;
            answer = Math.max(answer, sheep);
        } else {
            wolf++;
            if (sheep <= wolf) return;
        }
        
        List<Integer> newNexts = new ArrayList<>();
        newNexts.addAll(nexts);
        newNexts.addAll(adj[now]);
        newNexts.remove((Integer)now);
        
        for (int next : newNexts) {
            dfs(next, newNexts, sheep, wolf, info);
        }   
    }
}
/**
늑대가 더 많거나 같아지면 안됨.
양을 최대한 많이 모으기.
출발점을 다시 들르는 것은 상관없다.

특정 서브트리를 탐색하면, 진입 불가능했던 늑대노드를 진입하는 경우를 해결해야 한다.
*/