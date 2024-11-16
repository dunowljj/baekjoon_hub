class Solution {
    
    private static int answer = 0;
    
    public int solution(int[] numbers, int target) {
        findRoute(numbers, target, 0, 0);
        return answer;
    }
    
    public void findRoute(int[] numbers, int target, int index, int result) {
        if (index == numbers.length) {
            
            if (target == result) {
                answer++;
            }
            
            return;
        }
        
        findRoute(numbers, target, index + 1, result + numbers[index]);
        findRoute(numbers, target, index + 1, result - numbers[index]);
    }
}