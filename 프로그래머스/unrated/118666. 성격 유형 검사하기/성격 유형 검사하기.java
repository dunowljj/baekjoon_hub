class Solution {
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        int[] score = new int['T' + 1];

        for (int i = 0; i < choices.length; i++) {
            int idx = 0;
            
            if (choices[i] < 4) {
                idx = survey[i].charAt(0);
                score[idx] += 4 - choices[i];
            } else {
                idx = survey[i].charAt(1);
                score[idx] += choices[i] % 4;
            }
                      
        }
        
        char kind1 = score['R'] >= score['T'] ? 'R' : 'T';
        char kind2 = score['C'] >= score['F'] ? 'C' : 'F';
        char kind3 = score['J'] >= score['M'] ? 'J' : 'M';
        char kind4 = score['A'] >= score['N'] ? 'A' : 'N';
        
        answer = "" + kind1 + kind2 + kind3 + kind4;
        return answer;
    }
}