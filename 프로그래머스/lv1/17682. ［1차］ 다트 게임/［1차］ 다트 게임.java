import java.util.*;

class Solution {
    public int solution(String dartResult) {
        int answer = 0;
        List<Integer> list = new ArrayList();
        String num = "";
        
        for (int i = 0; i < dartResult.length(); i++) {
            char curr = dartResult.charAt(i);
            int score = 0;
            
            if ('0' <= curr && curr <= '9') {
                num += curr;
                continue;
            }
            
            if (curr == 'S') {
                score = (int)Math.pow(Integer.parseInt(num), 1);
                num = "";
                list.add(score);
                continue;
            } else if (curr == 'D') {
                score = (int)Math.pow(Integer.parseInt(num), 2);
                num = "";
                list.add(score);
                continue;
            } else if (curr == 'T') {
                score = (int)Math.pow(Integer.parseInt(num), 3);
                num = "";
                list.add(score);
                continue;
            }
            
            if (curr == '*') {
                if (list.size() == 1) {
                    list.set(0, list.get(0) * 2);
                }
                
                else if (list.size() == 2){
                    list.set(0, list.get(0) * 2);
                    list.set(1, list.get(1) * 2);
                }
                
                else if (list.size() == 3) {
                    list.set(1, list.get(1) * 2);
                    list.set(2, list.get(2) * 2);
                }
            }
            
            if (curr == '#') {
                if (list.size() == 1) {
                    list.set(0, list.get(0) * -1);
                }
                
                else if (list.size() == 2){
                    list.set(1, list.get(1) * -1);
                }
                
                else if (list.size() == 3) {
                    list.set(2, list.get(2) * -1);
                }
            }
        }
        
        return list.stream().reduce(0, (x,y) -> x + y);
    }
}
/*
*,# -> 해당, 바로 전 점수 2배 / 해당 마이너스
효과 중첩 가능!
점수마다 하나만 가능, 없을수도 있음
*/