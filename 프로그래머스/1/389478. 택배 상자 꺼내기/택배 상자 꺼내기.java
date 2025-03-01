class Solution {
    public int solution(int n, int w, int num) {
        int answer = 0;
        int height = (n / w) + ((n % w == 0) ? 0 : 1);
        
        int[][] storage = new int[height][w];
        int[] targetBox = new int[2];

        
        storeBoxes(n, height, w, num, storage, targetBox);
        
        int targetRow = targetBox[0];
        int targetCol = targetBox[1];
        answer += height - targetRow; 
        if (storage[height - 1][targetCol] == 0) answer--;

        return answer;
    }
    
    private void storeBoxes(int n, int height, int w, int num, int[][] storage, int[] targetBox) {
        int boxNo = 1;
        for (int row = 0; row < height; row++) {
            if (row % 2 == 0) {
                for (int col = 0; col < w; col++) {
                    storage[row][col] = boxNo;
                    
                    if (boxNo == num) {
                        targetBox[0] = row;
                        targetBox[1] = col;
                    }
                    
                    boxNo++;
                    if (boxNo == n + 1) return;
                }
            }
            
            else {
                for (int col = w - 1; col >= 0; col--) {
                    storage[row][col] = boxNo;
                    
                    if (boxNo == num) {
                        targetBox[0] = row;
                        targetBox[1] = col;
                    }
                    
                    boxNo++;
                    if (boxNo == n + 1) return;
                }
            }
        }
    }
}

/**
굳이 2차원 배열을 만들지 않고, 수학적으로 해결해도됨
**/