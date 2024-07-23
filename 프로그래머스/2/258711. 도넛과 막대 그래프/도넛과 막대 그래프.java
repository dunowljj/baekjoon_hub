import java.util.*;

class Solution {
    
    private static final int IDX_VERTEX = 0;
    private static final int IDX_DONUT = 1;
    private static final int IDX_BAR = 2;
    private static final int IDX_EIGHT = 3;
    
    private static final int MAX_VERTEX = 1_000_000;
    
    int[] answer = new int[4];
    Set<Integer> candidates = new HashSet<>();
    boolean[] visited = new boolean[MAX_VERTEX + 1];
    
    public int[] solution(int[][] edges) {
        
        // 인접리스트 생성 및 초기화
        List<Integer>[] adjList = new ArrayList[MAX_VERTEX + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            
            adjList[a].add(b);
        }
        
        // 임의 정점 찾기
        int tempVertex = findTemp(adjList);
        answer[IDX_VERTEX] = tempVertex;
        
        // 임의 정점 기준으로 탐색          
        countGraphByDFS(adjList, tempVertex); // 인접리스트, 그래프의 시작점을 인수로 넘긴다.
        
        return answer;
    }
    
    public int findTemp(List<Integer>[] adjList) {
        for (int start = 0; start < adjList.length; start++) {
            if (!visited[start]) {
                visited[start] = true;
                // 시작점 out2 초과 -> 정점이다.
                if (adjList[start].size() > 2) {
                    return start;
                }
                
                // 시작점 out2 -> 정점 후보
                if (adjList[start].size() == 2) {
                    candidates.add(start);
                }
                
                visited[start] = true;
                Set<Integer> cycleChecker = new HashSet<>();
                cycleChecker.add(start);

                findTemp(adjList, start, cycleChecker);            
            }
        }
        
       return candidates.iterator().next();
    }

    public void findTemp(List<Integer>[] adjList, int now, Set<Integer> cycleChecker) {
        for (int next : adjList[now]) {
            int out = adjList[next].size();   
            
            if (!visited[next]) {
                if (out == 2) candidates.remove(next);
                visited[next] = true;
                cycleChecker.add(next);
                findTemp(adjList, next, cycleChecker);
            }
            
            // 싸이클 완료시 out2이면 후보 제외
            if (visited[next] && cycleChecker.contains(next)) {
                if (out == 2) candidates.remove(next);
            }
        }
    }
    
    public void countGraphByDFS(List<Integer>[] adjList, int start) {
        int outCount = adjList[start].size();  
        
        for (int next : adjList[start]) {
            Set<Integer> cycleChecker = new HashSet<>();
            
            cycleChecker.add(start);
            outCount = adjList[next].size();  
            
            // out2 o 하나라도 발견 -> 8자 그래프
            if (outCount == 2) {
                answer[IDX_EIGHT]++;
                continue;
            }

            // 사이클 o -> 도넛 그래프
            if (cycleChecker.contains(next)) {
                answer[IDX_DONUT]++;
                continue;
            }

            // 나가는 간선 없음 -> 맨 끝 정점 (사이클 x) -> 막대 그래프
            if (outCount == 0) {
                answer[IDX_BAR]++;
                continue;
            }

            countGraphByDFS(adjList, next, cycleChecker);   
        }
    }
    
    public void countGraphByDFS(List<Integer>[] adjList, int now, Set<Integer> cycleChecker) {
         for (int next : adjList[now]) {
            
            cycleChecker.add(now);
            int outCount = adjList[next].size();  
            
             
            // out2 o 하나라도 발견 -> 8자 그래프
            if (outCount == 2) {
                answer[IDX_EIGHT]++;
                return;
            }

            // 사이클 o -> 도넛 그래프
            if (cycleChecker.contains(next)) {
                answer[IDX_DONUT]++;
                return;
            }

            // 나가는 간선 없음 -> 맨 끝 정점 (사이클 x) -> 막대 그래프
            if (outCount == 0) {
                answer[IDX_BAR]++;
                return;
            }

            countGraphByDFS(adjList, next, cycleChecker);   
        }
    }
}

/**
그래프 수의 합은 2 이상

answer = [정점 번호, 도넛, 막대, 8자]


단순하게 임의 정점을 먼저 찾고 시작하기
그래프 수의 합은 2 이상

answer = [정점 번호, 도넛, 막대, 8자]

1. 임의 정점 찾기
out2 초과이면 무조건 임의 정점

out2라면 임의 정점 후보에 넣기
사이클 내부에 있으면 정점 후보에서 제외 
    : 시작점 이후에 out2를 발견하는 경우 후보 제외
    : 싸이클 완료시에 out2를 발견하는 경우 후보 제외

: 방문 x -> 탐색 계속
: 방문 o, 사이클 x -> 무시
: 방문 o, 사이클 o -> 정점 후보 삭제


2. 정점 찾은 후 그래프 세기

싸이클 o, out2 o -> 8자
싸이클 o, out2 x -> 도넛
싸이클 x,        -> 막대


*/
