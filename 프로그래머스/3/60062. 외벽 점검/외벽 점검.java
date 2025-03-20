import java.util.*;

class Solution {
    
    private List<int[]> friendsCombs = new ArrayList<>();
    private int answer = Integer.MAX_VALUE;
    
    
    public int solution(int n, int[] weak, int[] dist) {
        int wLen = weak.length;
        int len = dist.length;
        
        // 친구를 배치하는 모든 조합 생성
        makeCombs(0, len, new int[len], new boolean[len], dist);
        
        int[] longWeak = new int[wLen * 2];
        int longLen = longWeak.length;
        System.arraycopy(weak, 0, longWeak, 0, wLen);
        System.arraycopy(weak, 0, longWeak, wLen, wLen);
        for (int i = wLen; i < longWeak.length; i++) {
            longWeak[i] += n;
        }
//         for (int i = 0; i < longLen; i++) 
//         System.out.print(longWeak[i] + " ");
        
        // 0~3[4] -> 0~7[8]
        
        for (int[] fComb : friendsCombs) {
        
            for (int start = 0; start < wLen; start++) {
                // 시계방향 탐색
                // [1, 5, 6, 10] -> [1, 5, 6, 10, 1+12, 5+12, 6+12, 10+12]
                int i = 0; // 친구 이동거리 조합의 인덱스
                int j = 0; // 탐색하려는 취약지점 인덱스
                int friendWork = 0;
                while (i < fComb.length && j < wLen) {
                    int next = longWeak[start + j + 1];
                    int now = longWeak[start + j];
                    
                    int nextLoc = now + fComb[i];
                    
                    // 친구가 탐색하지 못한 다음 위치 찾기
                    // 다음 while j문 순회시 탐색 시작 위치가 now로 설정되게 함
                    while (nextLoc >= next) {
                        j++;
                        if (j == wLen) break;
                        else next = longWeak[start + j + 1];
                    }
                    
                    i++;
                    j++;
                }     
                
                // 모두 순회 성공
                if (j == wLen) {  
                    answer = Math.min(answer, i);
                }
                
                // 반시계방향 탐색
                // [1, 5, 6, 10] -> [1, 5, 6, 10, 1+12, 5+12, 6+12, 10+12]
                // 7->6->5->4 / 6->5->4->3 / .. 4->3->2->1
                i = 0;
                j = 0;
                int reverseStart = longLen - 1 - start;
                while (i < fComb.length && j < wLen) {
                    int next = longWeak[reverseStart - j - 1];
                    int now = longWeak[reverseStart - j];
                    
                    int nextLoc = now - fComb[i];
                    
                    // 친구가 탐색하지 못한 다음 위치 찾기
                    // 다음 while j문 순회시 탐색 시작 위치가 now로 설정되게 함
                    while (nextLoc <= next) {
                        j++;
                        
                        if (j == wLen) break;
                        else next = longWeak[reverseStart - j - 1];
                    }
                    
                    i++;
                    j++;
                }     
                
                // 모두 순회 성공
                if (j == wLen) {
                    answer = Math.min(answer, i);
                }
            }
        }
        
        if (answer == Integer.MAX_VALUE) answer = -1;
        
        return answer;
    }
    
    public void makeCombs(int depth, int len, int[] fComb, boolean[] visited, int[] dist) {
        if (depth == len) {
            int[] c = new int[len];
            System.arraycopy(fComb, 0, c, 0, len);
            friendsCombs.add(c);
            
            // for (int i = 0; i < len; i++) {
            //     System.out.print(fComb[i]+ " ");
            // }
            // System.out.println();
            
            return;
        }
        
        for (int i = 0; i < len; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            
            fComb[depth] = dist[i];
            makeCombs(depth + 1, len, fComb, visited, dist);
            visited[i] = false;
        }
    }
}
/**
정북 방향 지점 0
취약지점 : 시계방향으로 떨어진 거리
친구들의 출발지점은 아무곳이나 될 수 있다.

전부 점검하고, 친구들을 최소로 투입하려면?

취약지점 최대 15 : 각 지점에서 양방향 -> 30가지?
친구 최대 8 -> 8!가지 경우의 수  : 40320

각 취약지점에서 양방향으로 탐색을 해보기
*/