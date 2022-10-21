class Solution {
    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        
        for (int i = 0; i < n; i++) {
            arr1[i] |= arr2[i];
            
            answer[i] = encode(arr1[i], n);
        }
        
        return answer;
    }
    
    private String encode(int value, int n) {
        String code = "";
        for (int i = n - 1; i >= 0; i--) {
            if ((value & (1 << i)) == 0) {
                code += " ";
            } else {
                code += "#";
            }
        }
        return code;
    }
}