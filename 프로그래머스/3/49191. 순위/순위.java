import java.util.*;

class Solution {
    int[] info;
    List<Integer>[] adjList;
    
    public int solution(int n, int[][] results) {
        int answer = 0;
        
        // winner -> loser 단방향 그래프 생성
        adjList = new ArrayList[n + 1];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }
        
        for (int[] r : results) {
            int winner = r[0];
            int loser = r[1];
            
            adjList[winner].add(loser);
        }
        
        // win/lose 정보의 수
        info = new int[n + 1];
        
        for (int start = 1; start < adjList.length; start++) {
            boolean[] visited = new boolean[n + 1];
            visited[start] = true;
            info[start] += countInfo(start, visited); // 승리 카운트 추가
            
            // for (int count : info) {
            //     System.out.print(count + " ");
            // }
            // System.out.println();
        }
        
        
        for (int count : info) {
            if (count == n - 1) answer++;
            // System.out.print(count + " ");
        }
        
        return answer;
    }
    
    public int countInfo(int start, boolean[] visited) {
        int count = 0;
        
        for (int next : adjList[start]) {
            if (!visited[next]) {
                visited[next] = true;
                info[next]++; // 패배 카운트 추가
                count += countInfo(next, visited) + 1;
            }
        }
        
        return count;
    }
}
/**

n-1개의 승부 결과가 있다면 등수를 도출할 수 있다.

단방향 그래프 생성 후, 자신에게 들어오는 간선 수/나가는 간선 수를 추적햔다.

 
중복없이 특정 노드에서 시작하여 도착하는 노드의 in(lose) 값을 + 한다.
중복없이 특정 노드에서 시작하여 도착하는 노드 개수당 특정노드의 out(win) 값을 + 한다.

예시에서,
- 1 : in 0, out 2 
- 2 : in 3, out 1
- 3 : in 1, out 2
- 4 : in 0, out 3
- 5 : in 4, out 0

이렇게 처리를 했을때, in + out = n인 경우 등수를 알 수 있다.

등수를 알 수 있는지 여부만 알면된다. win, lose 횟수의 합이 n만 되면되므로, win/lose 구분없이 값만 합산하면 된다.
**/