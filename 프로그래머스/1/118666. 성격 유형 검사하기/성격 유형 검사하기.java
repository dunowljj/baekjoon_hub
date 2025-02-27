class Solution {
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        int[] surveyCounts = new int[26];
        int n = survey.length;
        
        for (int i = 0; i < n; i++) {
            char disagree = survey[i].charAt(0);
            char agree = survey[i].charAt(1);
            
            int choice = choices[i];
            
            // 비동의
            if (choice < 4) {
                surveyCounts[(int)(disagree - 'A')] += (4 - choice);
            }
            
            // 모르겠음
            else if (choice == 4) {
                continue;
            }
            
            // 동의
            else if (choice > 4) {
                surveyCounts[(int)(agree - 'A')] += (choice - 4);
            }
        }
        
        // for (int i = 0; i < 26; i++) {
        //     System.out.print((char)(i +'A') +" ");
        // }
        // System.out.println();
        // for (int i = 0; i < 26; i++) {
        //     System.out.print(surveyCounts[i] + " ");
        // }
        
        // 결과 종합
        if (surveyCounts[(int)('R'-'A')] >= surveyCounts[(int)('T'-'A')]) {
            answer += "R";
        } else {
            answer += "T";
        }
        
        if (surveyCounts[(int)('C'-'A')] >= surveyCounts[(int)('F'-'A')]) {
            answer += "C";
        } else {
            answer += "F";
        }
        
        if (surveyCounts[(int)('J'-'A')] >= surveyCounts[(int)('M'-'A')]) {
            answer += "J";
        } else {
            answer += "M";
        }
        
        if (surveyCounts[(int)('A'-'A')] >= surveyCounts[(int)('N'-'A')]) {
            answer += "A";
        } else {
            answer += "N";
        }
        
        return answer;
    }
}
/**
점수같으면 사전순 빠른 유형 선택

survey[i] 첫번째 ->비동의
survey[i] 두번째 -> 동의
*/