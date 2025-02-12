import java.util.*;

class Solution {
    
    private static boolean isFinish = false;
    private static int n;
    
    public boolean solution(int n, int[][] path, int[][] order) {
        this.n = n;
        
        List<Integer>[] adjList = new ArrayList[n];
        for (int i = 0; i < n; i++) adjList[i] = new ArrayList<>();
        for (int[] p : path) {
            int a = p[0];
            int b = p[1];
            
            adjList[p[0]].add(p[1]);
            adjList[p[1]].add(p[0]);   
        }
        
        // 선행되어야 하는 노드를 가르키는 단방향 그래프 생성
        List<Integer>[] directedAdj = new ArrayList[n];
        for (int i = 0; i < n; i++) directedAdj[i] = new ArrayList<>();
        createGraph(adjList, directedAdj, new boolean[n]);
        
        for (int[] o : order) {
            int preNode = o[0];
            int postNode = o[1];
            
            directedAdj[preNode].add(postNode); // pre -> post
            // System.out.println("pre:"+preNode+" --> post:"+postNode);
        }
        
        return tSort(directedAdj);
    }
    
    private void createGraph(List<Integer>[] adjList, List<Integer>[] directedAdj, boolean[] visited) { 
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int now = queue.poll();    
            
                for (int next: adjList[now]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        directedAdj[now].add(next); // pre -> post
                        // System.out.println("pre:"+now+" --> post:"+next);
                        queue.offer(next);
                    }  
                }
            }
        }
    }
    
    private boolean tSort(List<Integer>[] directedAdj) {
        Queue<Integer> queue = new LinkedList<>();
        int[] inDegree = new int[n];
        
        // 진입차수 카운트
        for (int i = 0; i < directedAdj.length; i++) {
            for (int next: directedAdj[i]) {
                inDegree[next]++;
            }
        }
        
        for(int i = 0; i < directedAdj.length; i++) {
            if(inDegree[i] == 0) queue.offer(i);
        }

        int count = 0;
        while(!queue.isEmpty()) {
            int now = queue.poll();

            count++;

            for(int next : directedAdj[now]) {
                if(--inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return count == directedAdj.length;
    }
}
/**
풀이 방법 변경

1. 인접리스트를 양방향으로 초기화한다. 
2. 선행되어야 하는 노드(pre)가 선행이 필요한 노드(post)를 가르키도록 단방향 그래프를 만든다.
  - 전체를 탐색하면서 depth에 따른 단방향 그래프를 새로 생성가능하다.
  - dfs로 초기화하면 이미 양방향간선으로 싸이클이 생긴 경우를 커버하지 못한다.
  - bfs로 초기화하고, 선행이 되어야하는 관계성만 나타내자. 예시로 0-1, 0-2, 1-2가 있을때, 1-2는 그래프에 나타낼 필요가 없다.
3. 생성한 그래프에 order 정보도 2와 같은 방식으로 추가한다.

여기서 pre <- post를 가르키는게 논리적으로 맞을 수 있지만, 0부터 탐색하는 편의를 보장하기 위해 pre -> post로 설정한다.

위상정렬을 하자. 0은 진입 차수가 0일수밖에 없다.

*/