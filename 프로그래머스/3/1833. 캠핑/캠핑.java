import java.util.*;

class Solution {
    public int solution(int n, int[][] data) {
        int answer = 0;
        
        Arrays.sort(data, (o1, o2) -> {
             if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            }
            return o1[0] - o2[0];
        });
            
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j< data.length; j++) {
                // 넓이 0
                if (data[i][0] == data[j][0] || data[i][1] == data[j][1]) continue;
                
                if (canSetUp(data, i, j)) answer++;
            }
        }
    
        return answer;
        
    }
    
    private boolean canSetUp(int[][] data, int i, int j) {
        for (int k = i + 1; k < j; k++) {
            if (
                (data[i][0] < data[k][0] && data[k][0] < data[j][0]) &&
                (Math.min(data[i][1],data[j][1]) < data[k][1] && data[k][1] < Math.max(data[i][1],data[j][1]))
            ) return false;
        }
        
        return true;
    }
    
}
/**
설치할 수 있는 쐐기 수만 구하면 됨.
안겹치게 최대한 많은 텐트를 설치하는게 아니다.
결국 모든 경우에 대해 사이에 쐐기가 있는지만 체크하면된다.

2차원 누적합List를 만들면 성능을 개선가능할듯
**/