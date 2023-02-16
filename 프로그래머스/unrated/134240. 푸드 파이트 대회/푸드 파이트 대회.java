import java.util.*;

class Solution {
    public String solution(int[] food) {
        StringBuilder front = new StringBuilder();
        StringBuilder back = new StringBuilder();
        
        for (int i = 1; i < food.length; i++) {
            for (int j = 0; j < food[i] / 2; j++) {
                front.append(i);
                back.append(i);
            }
        }
        
        back.reverse();
        front.append(0).append(back);
        
        return front.toString();
    }
}