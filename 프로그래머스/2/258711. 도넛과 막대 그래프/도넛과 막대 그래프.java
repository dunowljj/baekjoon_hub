import java.util.*;

class Solution {
    
    private List<Integer>[] adj;
    
    private static final int MIN_NODE_NO = 1;
    private static final int MAX_NODE_NO = 1_000_000;
    
    private int[] in;
    private int[] out;
    
    private int[] answer = new int[4]; // [생성 정점의 번호, 도넛 수, 막대 수, 8자 수]
        
    public int[] solution(int[][] edges) {
        
        adj = new ArrayList[MAX_NODE_NO + 1];
        for (int i = MIN_NODE_NO; i <= MAX_NODE_NO; i++) adj[i] = new ArrayList<>();
        
        in = new int[MAX_NODE_NO+1];
        out = new int[MAX_NODE_NO+1];
        
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            
            out[a]++;
            in[b]++;
            
            adj[a].add(b);
        }
        
        int start = findTempVertex();
        answer[0] = start;
        // System.out.println("tempVertex:"+start);
        
        boolean[] visited = new boolean[MAX_NODE_NO + 1];
        for (int adjWithTemp : adj[start]) {
            in[adjWithTemp]--;
            countGraph(adjWithTemp, visited);
        }
        
        return answer;
    }
    
    private int findTempVertex() {
        int tempVertex = 0;
        for (int i = 1; i <= MAX_NODE_NO; i++) {
            if (in[i] == 0 && out[i] >= 2) {
                tempVertex = i;
                break;
            }
        }
        
        return tempVertex;
    }
    
    
    private void countGraph(int now, boolean[] visited) {
        
        // 막대의 맨끝 도달
        if (out[now] == 0) {
            answer[2]++;
            return;
        }
        
        // 8자의 중간점 도달 혹은 8자 size=1그래프
        if (in[now] >= 2 || out[now] >= 2) {
            answer[3]++;
            return;
        }
        
        // 도넛 : 8자 중간점을 거치지 않고, 한바퀴를 돌았음
        if (visited[now]) {
            answer[1]++;
            return;
        }
        
        visited[now] = true;
        
        
        for (int next : adj[now]) {
            countGraph(next, visited);
        }
    }
}
/**
도넛 -> 싸이클 o, in1, out1로 일정함. 
막대 -> 싸이클 x, 일방향. in1,out1; 맨끝 따라가면 in1,out0 발견
8자 -> 싸이클 o, in2,out2 1개 나머지 in1,out1

임의 정점 -> in0, out >= 2 그래프가 2개이상이라는 조건이 있음

1. 인접리스트 생성. 각 노드의 in,out 차수 세주기
2. 탐색해서 정점 찾기. 정점 주변에 노드에 대해 정점이 진입하는 차수 삭제.
3. 정점부터 주변 탐색
- 도넛 : 싸이클 발견까지 in2 이상,out2 이상 발견안되면 도넛
- 막대 : 맨끝에 out0 찾아지면 막대. 막대는 중간부터 탐색하더라도 맨끝에 도달하게 되어있음.
- 8자 : 싸이클 발견까지 in2,out2 발견되면 8자


임시 정점이 8자의 중심에 연결된 경우, 바로 탐색을 종료하기에 1번만 카운트가 된다.
임시 정점이 8자의 일부에 연결된 경우, 1방향만 탐색해서 1번만 카운트 된다.
 

*/