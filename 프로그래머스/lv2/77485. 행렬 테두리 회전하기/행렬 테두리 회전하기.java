class Solution {
    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        int[][] matrix = new int[rows][columns];
        
        // init
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = (j + 1) + (i * columns);
            }
        }
        
        for (int i = 0; i < queries.length; i++) {
          	rotate(matrix, queries[i], answer, i);  
        }
        
        return answer;
    }
    
    private void rotate(int[][] matrix, int[] query, int[] answer, int idx) {
     	int min = Integer.MAX_VALUE;
        
        // 왼쪽 위 지점
        int topRow = query[0] - 1;
        int leftCol = query[1] - 1;
        
        // 오른쪽 아래 지점
        int bottomRow = query[2] - 1;
        int rightCol = query[3] - 1;
        
        // 모서리 값 저장 : 오른 위 / 오른 아래 / 왼 아래
        int ru = matrix[topRow][rightCol];
        int rd = matrix[bottomRow][rightCol];
        int ld = matrix[bottomRow][leftCol];
        
        // 최솟값 찾기 - 모서리는 따로 뺴놓으므로 미리 비교
        min = Math.min(min, Math.min(ru, Math.min(rd, ld)));
        
        // right : 왼위 -> 오른 위
        for (int i = rightCol; i >= leftCol + 1; i--) {
			matrix[topRow][i] = matrix[topRow][i - 1];   
           
            min = Math.min(min, matrix[topRow][i]);
        }

        // down : 오른 위 -> 오른 아래
        for (int i = bottomRow; i >= topRow + 1; i--) {
			matrix[i][rightCol] = matrix[i - 1][rightCol];
            
            min = Math.min(min, matrix[i][rightCol]);
        }
        
        // 끝에서 두번째 값 저장해논 모서리로 갱신
        matrix[topRow + 1][rightCol] = ru;

        
        // left : 오른 아래 -> 왼 아래
        for (int i = leftCol; i <= rightCol - 1; i++) {
        	matrix[bottomRow][i] = matrix[bottomRow][i + 1];
            
            min = Math.min(min, matrix[bottomRow][i]);
        }
        
        matrix[bottomRow][rightCol - 1] = rd;
        
		// up : 왼 아래 -> 왼 위
		for (int i = topRow; i <= bottomRow - 1; i++) {
			matrix[i][leftCol] = matrix[i + 1][leftCol];
            
            min = Math.min(min, matrix[i][leftCol]);
		}
		
        matrix[bottomRow - 1][leftCol] = ld;
        
        
        answer[idx] = min;
    }
}
/*
1) 회전 -> 한 칸씩 이동

2) 회전 마다 배열에 최솟값 저장


최대 크기 100 * 100 = 10_000
최대 회전 이동 수 : 100 * 4 - 4 = 396
회전의 개수 : 1 ~ 10_000

## 회전?
방법 1. 큐에 넣고 차례대로 빼서 회전?
방법 2. 4줄을 그냥 구현
방법 3. direction을 지정해서 첫값만 저장해놓고 탐색

1 ≤ x1 < x2 ≤ rows, 1 ≤ y1 < y2 ≤ columns -> x1이 x2보다 작고, y1도 y2보다 작음





*/