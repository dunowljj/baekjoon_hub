import java.util.*;

class Solution {
    public String solution(String[] participants, String[] completions) {
        String answer = "";
        Map<String, Integer> completionCounts = new HashMap<>();
        
        for (String c : completions) {
            completionCounts.put(c, completionCounts.getOrDefault(c, 0) + 1);
        }
        
        for (String paricipant : participants) {            
            if (completionCounts.containsKey(paricipant)) {
                checkCompletion(completionCounts, paricipant);
            } else {
                answer = paricipant;
                break;
            }
        }
        
        return answer;
    }
    
    private void checkCompletion(Map<String, Integer> completionCounts, String participant) {
        if (completionCounts.get(participant) == 1) {
            completionCounts.remove(participant); 
        } else {
            completionCounts.put(participant, completionCounts.get(participant) - 1);
        }
    }
}