import java.util.*;
// import java.util.stream.*;

class Solution {
    
    static class Node implements Comparable<Node> {
        int no;
        int time;
        
        Node (int no, int time) {
            this.no = no;
            this.time = time;
        }
        
        public int compareTo(Node node) {
            return this.time - node.time;
        }
    }
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        int[] answer = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        
         // 현재 출발지에서 도착지(인덱스)까지 최소로 가능한 intensity를 저장
        int[] intensities = new int[n + 1];
        Arrays.fill(intensities, Integer.MAX_VALUE);
        boolean[] isSummit = new boolean[n + 1];
        boolean[] isGate = new boolean[n + 1];
            
        for(int summit : summits) isSummit[summit] = true;
        for(int gate : gates) isGate[gate] = true;
        
        // 양방향 그래프 생성
        Set<Node>[] graph = new HashSet[n + 1];
        for (int i = 0; i < n + 1; i++) {
            graph[i] = new HashSet<>();
        }
        
        // 계속 최적화해도 시간 초과가 계속 나왔다. 애초에 그래프를 만들때 쓸데없는 순회를 줄여야 한다.
        // 단방향 설정을 해놓으면, 탐색 시에 gate와 summit에 대해 체크할 필요없다.
        for (int[] path : paths) {
            int start = path[0];
            int dest = path[1];
            int time = path[2];
            
            /*
             * gate는 나가는 단방향, summit은 들어오는 단방향만 존재한다.
             * 
             */
            if (isSummit[start] || isGate[dest]) {
                graph[dest].add(new Node(start, time));
            } else if (isSummit[dest] || isGate[start]) {
                graph[start].add(new Node(dest, time));
            } else {
                graph[start].add(new Node(dest, time));  
                graph[dest].add(new Node(start, time)); 
            }
             
            // 게이트끼리, 산봉우리끼리 연결되는 경우는 주어지지 않는거같다.
        }
        
        // intensities의 순회를 생략하기 위한 정보를 넣는 큐
        PriorityQueue<Node> queue = new PriorityQueue<>(); 
        
        for (int start : gates) {
            queue.offer(new Node(start, 0));
            intensities[start] = 0; // 나중에 poll값 체크 시 0해줘야 한다. 그리고 가장 먼저 처리된다.
        }

        while (!queue.isEmpty()) {
            Node minNode = queue.poll();

            if (minNode.time > intensities[minNode.no]) continue;

            // 최소 노드의 다음 노드들 방문하면서 값 갱신
            for (Node target : graph[minNode.no]) {

                int round_intensity = Math.max(intensities[minNode.no], target.time); // 우회로 intensity 구하기 : 출발지 -> 인접 vs 인접 -> 목표

                // 우회로의 최소 intensity가 더 낮다면 갱신
                if (intensities[target.no] > round_intensity) {
                    intensities[target.no] = round_intensity;
                    queue.offer(new Node(target.no, intensities[target.no]));
                }
            }
        }

        // result 구하기 : 각 산봉우리까지의 최소 intensity 비교
        for (int summit : summits) {
            if (intensities[summit] < answer[1]) {
                answer[0] = summit;
                answer[1] = intensities[summit];
            }
            else if (intensities[summit] == answer[1]) {
                answer[0] = Math.min(answer[0], summit);
            }
        }

        return answer;
    }
}

/*
answer = [산봉우리 번호(동차시 낮은 봉우리), intensity값];

### 풀이
- intensity 자체가 이동했던 간선 중 가장 긴 간선의 길이이다.
- 산봉우리, 출발점을 제외한 쉼터들은 여러번 이동이 가능하다. 
- 결국 산봉오리까지 최소 intensity를 가지는 경로를 왕복하면 intensity는 최솟값을 유지한다. -> 산봉우리까지 편도만 구하면 된다.

1) gates를 순회하며, 출발 지점별로 다익스트라를 한다. "최소 이동거리"가 아닌, "intensity 최솟값"을 갱신하는 배열을 만든다. 
2) 최소 이동거리 배열의 갱신이 완료되면 각 산봉오리까지의 intensity를 한번에 구할 수 있다.
3) 가장 낮은 것을 답으로 채택한다.

+ intensity 동차 시 낮은 산봉우리 번호가 우선이다. 

++ 출발지가 아닌, 봉우리와 intensity만 있으면 되는 문제이다. 그래서 queue에 모든 출발지를 넣고, 섞여서 다익스트라를 구현해도 문제가 안생긴다.
결국 출발지가 어디든 해당 노드에서 목표 노드로 향하는 가장 적은 intensity로 계속해서 갱신하게 된다. 
- 방문 배열을 없애고, 갱신 intensity와 현재 intensity를 비교
- 시작지점들을 0으로 만들기. -> 0으로 만드는 의미가 있나?
*/