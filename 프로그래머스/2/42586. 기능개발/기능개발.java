import java.util.*;
import java.util.stream.*;

class Solution {
    
    private static final int MAX_JOB_PERCENTAGE = 100;
    
    public int[] solution(int[] progresses, int[] speeds) {
        int n = progresses.length;
        
        List<Integer> requiredDays = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            int remainPercentage = MAX_JOB_PERCENTAGE - progresses[i];
            int speed = speeds[i];
            
            int requiredDay = getRequiredDay(remainPercentage, speed);
            requiredDays.add(requiredDay);
        }
        
        // 길이 1개인 경우?
        List<Integer> deployedSizes = new ArrayList<>();
        int deploySize = 1;
        int deployed = requiredDays.get(0);
        for (int i = 1; i < n; i++) {
            
            // i번째 작업이 배포되는 경우 -> 배포되는 개수를 센다.
            if (requiredDays.get(i) <= deployed) {
                deploySize ++;
            }
            
            // i번째 작업이 다음에 배포될 경우 
            // -> 여태 카운팅한 개수를 추가 및 초기화, 새로운 배포 기준 소요시간 저장
            else {
                deployedSizes.add(deploySize);
                deploySize = 1;
                deployed = requiredDays.get(i);  
            }
        }
        
        /** 
         * 1) 앞선 작업과 마지막 작업까지 묶어서 배포되는 경우  
         * 2) 혹은 마지막 케이스가 홀로 배포되는 경우 (배열 길이가 1개인 경우도 포함)
         * 각각 마지막 배포가 정답에 반영되지 않아서 따로 처리.
         */ 
        deployedSizes.add(deploySize);
        
        return deployedSizes.stream()
            .mapToInt(Integer::intValue)
            .toArray();
    }
    
    public int getRequiredDay(int remainPercentage, int speed) { 
        int quotient = remainPercentage / speed;
        int remainder = remainPercentage % speed;
        
        return quotient + ((remainder == 0) ? 0 : 1);
    }
}

/**
각 배포마다 몇개의 기능이 배포되는지?

1) 두 배열을 통해 남은 소요 작업 일수를 계산한 리스트를 만들기.
2) 앞부터 순회, 소요 작업 일수가 같거나 작다면 같이 배포된다.
3) 처음 배포된 기능 기준으로 소요 작업 일수를 체크한다. 
--> 자료구조가 굳이 필요한지 모르겠다. 변수 하나를 두고, 이전 값을 저장하자.
*/
