import java.util.*;

class Solution {
    
    private static final int IDX_VERTEX = 0;
    private static final int IDX_DONUT = 1;
    private static final int IDX_BAR = 2;
    private static final int IDX_EIGHT = 3;
    
    private static final int MAX_VERTEX = 1_000_000;
    private static final int LEN = MAX_VERTEX + 1;
    
    int[] answer = new int[4];
    Set<Integer> candidates = new HashSet<>();
    boolean[] visited = new boolean[LEN];
    
    public int[] solution(int[][] edges) {
        
        // 인접리스트 생성 및 초기화
        int[] in = new int[LEN];
        int[] out = new int[LEN];
        
        for (int[] edge : edges) {
            int v1 = edge[0];
            int v2 = edge[1];
            
            out[v1]++;
            in[v2]++;
        }
        
        for (int i = 0; i < LEN; i++) {
            if (in[i] == 0 && out[i] >= 2) {
                answer[IDX_VERTEX] = i;
            }
            
            if (in[i] >= 2 && out[i] == 2) {
                answer[IDX_EIGHT]++;
            }
            
            if (in[i] >= 1 && out[i] == 0) {
                answer[IDX_BAR]++;
            }
        }
        
        // 도넛 그래프 개수 도출
        answer[IDX_DONUT] = out[answer[IDX_VERTEX]] - answer[IDX_EIGHT] - answer[IDX_BAR];

        return answer;
    }
}

/**
그래프 수의 합은 2 이상

answer = [정점 번호, 도넛, 막대, 8자]

정점 in, out을 계산해서 처리하기

in0인 경우  (out이 2이상) -> 임의 정점
in2 이상, out2 -> 1개당 8자 1개
(in1/2,) out0 -> 1개당 막대 1개
이후에 (임의 정점에서의 out 개수) - (8자 개수) - (막대 개수)하면 (도넛의 개수) 이다.


size1 도넛의 경우 in이 2개일 수 있음
*/
