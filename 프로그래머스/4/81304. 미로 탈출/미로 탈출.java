import java.util.*;

class Solution {
    
    private static boolean[][] visited;
    private static int[][] adj;
    
    private static final int INF = Integer.MAX_VALUE;
    
    public int solution(int n, int start, int end, int[][] roads, int[] traps) {
        int answer = 0;
        
        visited = new boolean[n + 1][1 << 10];
        adj = new int[n + 1][n + 1];
        Map<Integer,Integer> trapNodes = new HashMap<>(); // no(1~n) -> bitDigit(1~10)
        for (int i = 0; i < traps.length; i++) trapNodes.put(traps[i], i);
        
        for (int i = 1; i <= n; i++) {
            Arrays.fill(adj[i], INF);
        }
        
        for (int[] road : roads) {
            int P = road[0];
            int Q = road[1];
            int S = road[2];
            
            adj[P][Q] = Math.min(adj[P][Q], S);
        }
        
        // for (int i = 1; i <= n; i++) {
        //     for (int j = 1; j <= n; j++) {
        //         if (adj[i][j] == INF) System.out.print("INF ");
        //         else System.out.print(adj[i][j]+ " ");
        //     } 
        //     System.out.println();
        // }
        // System.out.println();
        
        Queue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0, 0));
        
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            
            visited[now.no][now.bit] = true;
            
            if (now.no == end) {
                // System.out.println("arrive end; now.no:"+now.no+", end:"+end+", cost:"+now.cost+"\nbit: "+now.bit);
                answer = now.cost;
                break;
            }
            
            for (int i = 1; i <= n; i++) {
                if (now.no == i) continue;
                // System.out.println("now no:"+now.no+", next no:"+i);
                
                
                // 출발-도착 모두 트랩
                // 11, 00, (10 or 01) 3가지 결과 개수 존재
                if (trapNodes.containsKey(now.no) && trapNodes.containsKey(i)) {
                    int nowBitDigit = trapNodes.get(now.no);
                    int nextBitDigit = trapNodes.get(i);
                    boolean isNowActivated = (now.bit & (1 << nowBitDigit)) != 0;
                    boolean isNextActivated = (now.bit & (1 << nextBitDigit)) != 0;
                    
                    int nb = now.bit ^ (1 << nextBitDigit);
                    
                    // 둘 중 하나만 활성화 되어 있는 경우 (역방향)
                    if (isNowActivated ^ isNextActivated) {
                        if (adj[i][now.no] == INF || visited[i][nb]) continue;
                        pq.offer(new Node(i, now.cost + adj[i][now.no], nb));
                        continue;
                    }
                    
                    // 양쪽모두 트랩 활성화되어 있는 경우, 모두 비활성화 된 경우 (정방향)
                    if (adj[now.no][i] == INF || visited[i][nb]) continue;
                    pq.offer(new Node(i, now.cost + adj[now.no][i], nb));
                    continue;
                }
                
                // 도착만 트랩
                if (trapNodes.containsKey(i)) {
                    // System.out.println("[도착 트랩]");
                    int nextBitDigit = trapNodes.get(i);
                    boolean isNextActivated = (now.bit & (1 << nextBitDigit)) != 0;
                    
                    int nb = now.bit ^ (1 << nextBitDigit);
                    
                    // 역방향
                    if (isNextActivated) {
                        if (adj[i][now.no] == INF || visited[i][nb]) continue;
                        pq.offer(new Node(i, now.cost + adj[i][now.no], nb));
                    // 정방향
                    } else {
                        if (adj[now.no][i] == INF || visited[i][nb]) continue;
                        pq.offer(new Node(i, now.cost + adj[now.no][i], nb));
                    }
                    continue;
                }
                
                // 출발만 트랩
                if (trapNodes.containsKey(now.no)) {
                    int nowBitDigit = trapNodes.get(now.no);
                    boolean isNowActivated = (now.bit & (1 << nowBitDigit)) != 0;
                    
                    // System.out.println("[출발 트랩]");
                    int nb = now.bit;
                    
                    // 역방향
                    if (isNowActivated) {
                        if (adj[i][now.no] == INF || visited[i][nb]) continue;
                        pq.offer(new Node(i, now.cost + adj[i][now.no], nb));
                    // 정방향
                    } else {
                        if (adj[now.no][i] == INF || visited[i][nb]) continue;
                        pq.offer(new Node(i, now.cost + adj[now.no][i], nb));
                    }
                    continue;
                }
                
                // 트랩이 아닌 경우
                if (adj[now.no][i] == INF || visited[i][now.bit]) continue;
                pq.offer(new Node(i, now.cost + adj[now.no][i], now.bit));
            }
        }
        
        return answer;
    }
    
    static class Node implements Comparable<Node> {
        int no;
        int cost;
        int bit;
        
        public Node(int no, int cost, int bit) {
            this.no = no;
            this.cost = cost;
            this.bit = bit;
        }
        
        @Override
        public int compareTo(Node node) {
            return this.cost - node.cost;
        }
    }
}
/**
1~3

트랩을 여러번 일부러 밟아서 길을 만들어야 하는 경우도 있을듯
1 -> 2(t) -> 3
2(t) <- 4 

두 방사이 여러길이 존재할수도 있음..

ElogE * 1024
트랩이 1~10사이에 존재하므로, 비트로 나타내면 될듯.

다익스트라를 구현, 큐에 비트로 trap의 전체 상태를 같이 넣어준다.
- 요소를 꺼낼때 기준으로 trap 발동을 시키자.
- 방문체크도 꺼낼때 하고, trap을 갱신하기 이전을 기준으로 방문체크를 하자.

도착지가 trap일때도 갈 수 있는지 체크가 필요함.
*/