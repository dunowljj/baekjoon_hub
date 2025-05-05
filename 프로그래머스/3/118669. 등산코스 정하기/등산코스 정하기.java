import java.util.*;

class Solution {
    
    List<Point>[] adj;
    int[] answer = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        adj = new ArrayList[n + 1];
        for (int i = 0; i < adj.length; i++) adj[i] = new ArrayList<>();
        
        boolean[] isGate = new boolean[n + 1];
        boolean[] isSummit = new boolean[n + 1];
        for (int gate : gates) isGate[gate] = true;
        for (int summit : summits) {
            isSummit[summit] = true;
        }
        
        for (int[] path : paths) {
            int i = path[0];
            int j = path[1];
            int w = path[2];
            
            if (isGate[i] || isSummit[j]) {
                adj[i].add(new Point(j, w));
            } else if (isSummit[i] || isGate[j]) {
                adj[j].add(new Point(i, w));
            } else {
                adj[i].add(new Point(j, w));
                adj[j].add(new Point(i, w));
            }
        }
        
        // 각 gate부터 시작. 낮은 intensity부터 탐색하도록 초기화
        int[] intensities = new int[n + 1];
        Arrays.fill(intensities, Integer.MAX_VALUE);
        boolean[] visited = new boolean[n + 1];
        Queue<Point> q = new PriorityQueue<>(Comparator.comparingInt(Point::getIntensity)
                                            .thenComparingInt(Point::getNo));
        
        for (int i = 0; i < gates.length; i++) {
            int gate = gates[i];
            
            q.offer(new Point(gate, 0));
            intensities[gate] = 0;
        }
        
        while (!q.isEmpty()) {
            Point now = q.poll();
            
            if (now.intensity > intensities[now.no]) continue;
            
            // 이게 최소 intensity가 아닐 수 있음. 도착한 후에 intensity를 종합해야함.
            if (isSummit[now.no]) {
                if (answer[1] >= now.intensity) {
                    answer[0] = Math.min(answer[0], now.no);
                    answer[1] = now.intensity;
                }
                continue;
            }
            
            for (Point next : adj[now.no]) { 
                int nextIntensity = Math.max(now.intensity, next.intensity);
                
                if (intensities[next.no] > nextIntensity) {
                    intensities[next.no] = nextIntensity; 
                    q.offer(new Point(next.no, nextIntensity));
                }
            }
        }
        
        return answer;
    }
    
    class Point {
        int no;
        int intensity;
        
        public Point(int no, int intensity) {
            this.no = no;
            this.intensity = intensity;
        }
        
        public int getNo() {
            return no;
        }
        
        public int getIntensity() {
            return intensity;
        }
    }
}
/*
출입구, 쉼터, 산봉우리
양방향, 두 지점 연결하는 등산로 최대 1개
각 출발지 -> 산봉우리까지 가장 낮게 이동가능한 intensity 찾기
지점의 수 n 1~50_000
산봉우리 summit은 n개까지 가능, gates도 마찬가지

- 출입구도 같은 출입구만 방문
- 산봉우리에서 다른 산봉우리를 마주치지 않고, 
- 가장 짧은 크기의 거리로 이동해야한다.

각 5만개 지점에서 모두 탐색을 하면 너무 오래걸린다.
그래프를 단방향으로 바꾼다. gate에서는 나가는 간선만 있도록 하고, summit은 들어오는 간선만 있도록 변경한다.

우선순위큐로 작은 intensity부터 탐색하지만, summit에 도달하는 순간 값이 더 클수도 있기때문에 탐색을 바로 종료해선 안됨. 번호순 정렬을 해도 =비교 추가를 안하면 정확한 값을 찾지 못한다.
방문체크를 하되, 방문하려는 곳의 최소 intensity가 현재보다 크다면, 현재 위치에서 진입할 수 있어야함.

*/