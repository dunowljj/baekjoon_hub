import java.util.Arrays;

import static java.util.Comparator.comparingInt;

class Solution {
    public int solution(int[][] routes) {
        int answer = 0;
        Arrays.sort(routes, comparingInt((int[] route) -> route[1]));
        
        int idx = 0;
        while (idx < routes.length) {
            // 나가는 지점에 cctv 설치
            int cctv = routes[idx][1];
            answer ++;
            
            
            // 다음 입구부터 탐색하여 cctv가 겹친다면 스킵한다. 
            // (나가는 지점은 무조건 뒤에 있으므로 시작점이 cctv 설치 지점보다 빠른지 확인한다.)
            idx++;
            while (idx < routes.length && cctv >= routes[idx][0]) {
                idx++;
            }
        }
        
        return answer;
    }
}

/**
 *  -----
 *      --
 *         -----
 *          -----
 *             --
 */