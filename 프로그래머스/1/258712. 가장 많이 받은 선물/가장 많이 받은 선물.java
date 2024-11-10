import java.util.*;

class Solution {
    public int solution(String[] friends, String[] gifts) {
        Map<String, Map<String, Integer>> giveCounts = new HashMap<>();
        Map<String, Map<String, Integer>> takeCounts = new HashMap<>();
        Map<String, Integer> presentScore = new HashMap<>();
        
        // 주고받은 횟수 초기화
        for (String gift : gifts) {
            String[] giverAndTaker = gift.split(" ");
            
            String giver = giverAndTaker[0];
            String taker = giverAndTaker[1];
            
            Map<String, Integer> counts = giveCounts.getOrDefault(giver, new HashMap<>());
            counts.put(taker, counts.getOrDefault(taker, 0) + 1);
            giveCounts.put(giver, counts);
            
            Map<String, Integer> counts2 = takeCounts.getOrDefault(taker, new HashMap<>());
            counts2.put(giver, counts2.getOrDefault(giver, 0) + 1);
            takeCounts.put(taker, counts2);
        }
        
        // 선물 지수 초기화
        for (String friend : friends) {
            Map<String, Integer> giveMap = giveCounts.getOrDefault(friend, new HashMap<>());
            Map<String, Integer> takeMap = takeCounts.getOrDefault(friend, new HashMap<>());
            
            int giveSum = giveMap.values().stream()
                .reduce((a,b) -> a + b).orElse(0);
                
            int takeSum = takeMap.values().stream()
                .reduce((a,b) -> a + b).orElse(0);
            
            presentScore.put(friend, giveSum - takeSum);
        }
        
        // 선물 가장 많이 받을 친구 찾기
        Map<String, Integer> willTakeCounts = new HashMap<>();
        
        for (String friend : friends) {
            for (String target : friends) {
                if (friend.equals(target)) continue;
                
                int giveCount = giveCounts
                    .getOrDefault(friend, new HashMap<>())
                    .getOrDefault(target, 0);
                
                int takeCount = takeCounts
                    .getOrDefault(friend, new HashMap<>())
                    .getOrDefault(target, 0);
                
                if (giveCount > takeCount) {
                    willTakeCounts.put(friend, willTakeCounts.getOrDefault(friend, 0) + 1);
                } else if (giveCount < takeCount) {
                    willTakeCounts.put(target, willTakeCounts.getOrDefault(target, 0) + 1);
                
                // 기록 없거나 주고받은 횟수가 동일한 경우 -> 선물지수로 비교
                } else if ((giveCount == 0 && takeCount == 0) || giveCount == takeCount) {
                    
                    int friendScore = presentScore.get(friend);
                    int targetScore = presentScore.get(target);
                    
                    if (friendScore > targetScore) {
                         willTakeCounts.put(friend, willTakeCounts.getOrDefault(friend, 0) + 1);
                    } else if (friendScore < targetScore) {
                         willTakeCounts.put(target, willTakeCounts.getOrDefault(target, 0) + 1);
                    } else {
                        // 선물지수도 같다면 주고받지 않는다.
                    }
                }
            }
        }
        
        int maxTake = 0;
        for (int takeCount : willTakeCounts.values()) {
            maxTake = Math.max(maxTake, takeCount);
        }
        
        willTakeCounts.forEach((key, value) -> System.out.println(key + ": " + value));
        return maxTake / 2;
    }
}

/*
이번달까지 기록으로 다음달 선물을 누가 많이 받을지 예측

1) 기록이 있는 경우
- 더 많이 선물을 준 사람이 다음달에 선물하나 받는다.
2) 기록이 없거나 횟수가 같은 경우
- 선물지수가 더 작은 사람이 큰 사람에게 준다.
  - 선물지수 : 이번달까지 준 선물 - 받은 선물
- 선물지수가 같다면 다음 달에 선물을 주고받지 않는다.

*/