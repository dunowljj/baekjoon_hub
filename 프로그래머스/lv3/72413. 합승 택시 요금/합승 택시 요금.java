import java.util.*;

class Solution {
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;
        
        int[][] adjArr = new int[n + 1][n + 1];
        int[][] minFares = new int[n + 1][n + 1];
        boolean[] visited = new boolean[n + 1];
        
        for (int[] fare : fares) {
            adjArr[fare[0]][fare[1]] = fare[2];
            adjArr[fare[1]][fare[0]] = fare[2];
        }
 
        for (int start = 1; start <= n; start ++) {
            updateFares(start, adjArr, minFares[start], visited);
            visited = new boolean[n + 1];
        }
        
        // int start = 0;
        // for (int[] faress : minFares) {
        //     System.out.print((start++) +" : ");
        //     for (int fare : faress) System.out.print(fare + " "); 
        //     System.out.println();
        // }
        
        for (int i = 1; i <= n; i++) {
            if (minFares[s][i] == 0 && s != i
                || minFares[i][a] == 0 && i != a
                || minFares[i][b] == 0 && i != b) continue;
            
            answer = Math.min(answer, minFares[s][i] + minFares[i][a] + minFares[i][b]);
            
            // System.out.println(i + " : " + 
            //                    " s->i : " + minFares[s][i] +
            //                    ", i->a : " + minFares[i][a] +
            //                    ", i->b : "+minFares[i][b]);            
            // System.out.println(answer);
        }
        
        return answer;
    }
    
    private void updateFares(int s, int[][] adjArr, int[] startTo, boolean[] visited) {
        // 1차적으로 연결된 간선들만 초기화
        for (int i = 1; i < startTo.length; i++) {
            int fare = adjArr[s][i];
            if (fare != 0) startTo[i] = fare;            
        }
        
        dijkstra(s, adjArr, startTo, visited);
    }
    
    private void dijkstra(int s, int[][] adjArr, int[] startTo, boolean[] visited) {
        // 최소거리 노드 찾기
        int min = findMin(startTo, visited); 

        // 다음 최소노드 없으면 종료.
        if (min == -1) return;
        
        visited[min] = true;
        
        // 찾은 노드를 이용해 다른 노드들까지의 거리 갱신하기
        for (int dest = 1; dest < adjArr[min].length; dest++) {
            if (visited[dest]) continue;
            if (s == dest) continue;
            
            int minToDest = adjArr[min][dest]; // 최소노드부터 목적지까지 단순거리
            if (minToDest == 0) continue;
            
            // 시작점부터 도착점까지 최소 요금
            // 갱신된 요금이 없는 경우
            if (startTo[dest] == 0) {
                startTo[dest] = startTo[min] + minToDest;
                continue;
            }
            
            // 요금 0원 아님 -> 간선존재 && [시작점 -> 목적지] > [시작점 -> 최소노드] + [최소노드 -> 목적지]
            if (startTo[dest] > startTo[min] + minToDest) {
                startTo[dest] = startTo[min] + minToDest;
            }
        }
        
        dijkstra(s, adjArr, startTo, visited);
    }
    
    private int findMin(int[] fares, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        
        for (int i = 0; i < fares.length; i++) {
            int fare = fares[i];
            if (visited[i]) continue;
            
            if (fare != 0 && fare < min) {
                min = fare;
                index = i;
            }
        }
        
        return index;
    }
}
/*
귀가 시 공통 경로까지만 택시를 같이타고, 후에는 나뉘어타고 간다.

택시비를 아끼는 방향으로 진행한다.

아예 합승을 하지 않고 각자 이동하는 경우의 예상 택시요금이 더 낮다면, 합승을 하지 않아도 됩니다. 
--> 각자의 최단거리를 구해야한다.

최저 택시 요금은 둘의 요금을 합한 경우 -> 각자 이동한 최저합이 이것보다 작으면 합승 안해도됨

요금이 0인 경우는 없다.

## 시간

간선의 수는 200 * 199 이하
지점 갯수 3~200개

## 풀이

특정 노드에서 A, B까지의 거리를 매번 계산하기?
시작점에서 다익스트라 1회
A,B, 시작점을 제외한 197개의 노드에서 A,B에 대한 최단거리 구하기

각 노드에 대해서, (최단거리는 간선의 가중치로 최저 요금을 말한다.)
1) 특정 경유지까지 같이가는 경우 : 
- [시작점 -> 특정 노드]의 최단거리 + ([특정 노드 -> A]의 최단거리 + [특정 노드 -> B]의 최단거리)

2) 한 목적지가 경유지가 되는 경우 
- [시작점 -> A -> B], [시작점 -> B -> A] 최단거리도 구한다. (사실상 1) 내용에 포함)
이 값들을 비교해서 최소값을 구하면 된다.

3) 각자 가는게 빠른 경우
- [시작 -> A]최단거리 + [시작 -> B]최단거리

다익스트라를 한 후, bfs로 각 위치까지의 최단거리 + A,B까지의 거리의 최솟값을 구하자

### 고민
- [시작점 -> 특정] 노드로 이동하는 경우 이미 목적지를 마주친다면? => 2)가 더 최단거리여서 상관없다.
- 혹은 A에 가는 길에 이미 B를 마주친다면? A가는길에 약간 돌아가면 B를 거칠 수 있다면? -> 2)이 해결해준다. 경유지를 들리는 경우를 탐색하면서 이런 경우가 발생하면, 어짜피 2)가 더 가깝다.

*/