class Solution {
    public String solution(int a, int b) {
        
        int[] dates = {31,29,31,30,31,30,31,31,30,31,30,31};
        String[] days = {"FRI","SAT","SUN","MON","TUE","WED","THU"};
        
        int count = 0;
        for (int i = 0; i < a - 1; i++) {
            count += dates[i];
        }
        
        count += b - 1;
        
        return days[count % 7];
    }
}