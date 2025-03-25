import java.util.*;

class Solution {
    
    Map<Integer, Integer> xIdxMap = new HashMap<>();
    Map<Integer, Integer> yIdxMap = new HashMap<>();
    
    TreeSet<Integer> yTree = new TreeSet<>();
    TreeSet<Integer> xTree = new TreeSet<>();
    
    int[][] sum;
    public int solution(int n, int[][] data) {
        int answer = 0;
        
        for (int i = 0; i < data.length; i++) {
            xTree.add(data[i][0]);
            yTree.add(data[i][1]);
        }
        
        // treeSet을 활용하여 배열의 인덱스 계산. wedge가 있는 위치에 +1 해주기
        sum = new int[yTree.size() + 1][xTree.size() + 1];
        for (int i = 0; i < data.length; i++) {
            int xIdx = xTree.headSet(data[i][0], true).size(); // [0]라인 공집합 활용을 위해 인덱스를 +1 시킨 상태
            int yIdx = yTree.headSet(data[i][1], true).size();
            
            // System.out.print("["+data[i][1]+","+data[i][0]+"] ");
            data[i][0] = xIdx;
            data[i][1] = yIdx;
            
            sum[yIdx][xIdx] = 1;
        }
        // System.out.println();
        
        
        // 2차원 누적합 계산
        for (int i = 1; i < sum.length; i++) {
            for (int j = 1; j < sum[i].length; j++) {
                sum[i][j] += sum[i - 1][j];
            }
        }
        for (int i = 1; i < sum.length; i++) {
            for (int j = 1; j < sum[i].length; j++) {
                sum[i][j] += sum[i][j - 1];
            }
        }
        
//         for (int i = 0; i < sum.length; i++) {
//             for (int j = 0; j < sum[i].length; j++) {
//                 System.out.print(sum[i][j] + " ");
//             }
//             System.out.println();
//         }
        
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (canSetUp(data, i, j)) {
                    answer++;
                }
            }
        }
        
        return answer;
    }
    
    private boolean canSetUp(int[][] data, int i, int j) {
        int ix = data[i][0];
        int iy = data[i][1];
        
        int jx = data[j][0];
        int jy = data[j][1];
        
        // 넓이 0
        if (ix == jx || iy == jy) return false;
        
        // 압축한 좌표가 인접하면, 그 사이에 아무런 쐐기가 없다는 의미이다.
        if (Math.abs(ix - jx) == 1 || Math.abs(iy - jy) == 1) return true;
        
        // 쐐기가 존재해서는 안되는 구역의 모서리 좌표
        int leftX = Math.min(ix, jx) + 1;
        int topY = Math.min(iy, jy) + 1;
        
        int rightX = Math.max(ix, jx) - 1;
        int bottomY = Math.max(iy, jy) - 1;
        
        int wedgeCount = sum[bottomY][rightX] 
            - sum[bottomY][leftX - 1] 
            - sum[topY - 1][rightX] 
            + sum[topY - 1][leftX - 1];
        
        return wedgeCount == 0;
    }
}
/**
설치할 수 있는 쐐기 수만 구하면 됨.
안겹치게 최대한 많은 텐트를 설치하는게 아니다.
결국 모든 경우에 대해 사이에 쐐기가 있는지만 체크하면된다.

2차원 누적합을 만들면 성능을 개선가능할듯
**/