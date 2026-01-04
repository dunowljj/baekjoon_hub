import java.util.*;

class Solution {
    
    int[][] dpMax;
    int[][] dpMin;
    
    public int solution(String arr[]) {
        int answer = -1;
        int n = (arr.length / 2) + (arr.length % 2);
        // System.out.println(n);
        
        dpMax = new int[n][n];
        dpMin = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            Arrays.fill(dpMax[i], Integer.MIN_VALUE);
            Arrays.fill(dpMin[i], Integer.MAX_VALUE);
        }
        
        dpMax[0][0] = Integer.parseInt(arr[0]);
        dpMin[0][0] = Integer.parseInt(arr[0]);
        
        for (int i = 1; i < n; i++) {
            int num = Integer.parseInt(arr[2 * i]);
            if (arr[2 * i - 1].equals("-")) num *= -1;
            
            dpMax[i][i] = num;
            dpMin[i][i] = num;
            
            // System.out.println(num);
        }
        
        for (int step = 1; step < n; step++) {
            // System.out.println("step:"+step);
            for (int i = 0; i < n - step; i++) {
    
                int end = i + step;
                for (int j = i; j < end; j++) {
                    
                    // System.out.println("j:"+j);
                    // System.out.println(arr[(2*j)-1]);
                    // System.out.println();
                    
                    if (i == 0 || arr[(2*j)-1].equals("+")) {
                         dpMax[i][end] = Math.max(dpMax[i][end], dpMax[i][j] + dpMax[j+1][end]);
                         dpMin[i][end] = Math.min(dpMin[i][end], dpMin[i][j] + dpMin[j+1][end]);
                    }
                    
                    else if (arr[(2*j)-1].equals("-")) {
                        dpMax[i][end] = Math.max(dpMax[i][end], dpMax[i][j] - dpMin[j+1][end]);
                        dpMin[i][end] = Math.min(dpMin[i][end], dpMin[i][j] - dpMax[j+1][end]);
                    }
                }
            }
        }
        
        return dpMax[0][n - 1];
    }
}
/**
- 길이 3~201
- 최대 101개 숫자
- 맨 앞, 맨 뒤는 숫자

memo를 하면서 dfs를 하면, 시간이 충분할까?

100 * 99 * 98..
*/