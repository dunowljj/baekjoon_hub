import java.util.*;

class Solution {
    final int[] numbers = new int[9];
    
    public int solution(int N, int number) {
        Set<Integer>[] set = new HashSet[9];
        for (int i = 0; i < 9 ; i++) set[i] = new HashSet<>();
        
        // n[1] = 5, n[2] = 55...
        for (int i = 1; i < numbers.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                numbers[i] += N * Math.pow(10, j);
            }
            
            set[i].add(numbers[i]);
        }
       
        for (int i = 1; i < 9; i++) {
            for (int operand1 : set[i]) {
                for (int j = 1; j + i < 9; j++) {
                    int operand2 = numbers[j];
                    
                    set[i + j].add(operand1 + operand2);
                    set[i + j].add(operand1 * operand2);
                    set[i + j].add(operand1 - operand2);
                    set[i + j].add(operand2 - operand1);
                    if (operand1 != 0) set[i + j].add(operand2 / operand1);
                    if (operand2 != 0) set[i + j].add(operand1 / operand2);
                }
            }
            
            if (set[i].contains(number)) return i;
        }
        
        return -1;
    }
}

/**


55
+/*-
8이 최대 최솟값

**/