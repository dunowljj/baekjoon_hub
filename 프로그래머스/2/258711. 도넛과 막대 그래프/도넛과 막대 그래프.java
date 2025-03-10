import java.util.*;

class Solution {

    
    private static final int MIN_NODE_NO = 1;
    private static final int MAX_NODE_NO = 1_000_000;
    
    private int[] in;
    private int[] out;
    
    private int[] answer = new int[4]; // [생성 정점의 번호, 도넛 수, 막대 수, 8자 수]
        
    public int[] solution(int[][] edges) {        
        in = new int[MAX_NODE_NO+1];
        out = new int[MAX_NODE_NO+1];
        
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            
            out[a]++;
            in[b]++;
        }
        
        
        int tempVertexxt = -1;
        
        for (int i = MIN_NODE_NO; i <= MAX_NODE_NO; i++) {
            // 생성 정점
            if (in[i] == 0 && out[i] >= 2) {
                answer[0] = i;
                continue;
            }
            
            // 막대
            if (in[i] >= 1 && out[i] == 0) {
                answer[2]++;
                continue;
            }
            
            // 8자
            if (in[i] >= 2 && out[i] >= 2) {
                answer[3]++;
                continue;
            }
        }
        
        // 도넛 수 = 전체 - 막대 수 - 8자 수
        answer[1] = out[answer[0]] - answer[2] - answer[3];

        return answer;
    }
}