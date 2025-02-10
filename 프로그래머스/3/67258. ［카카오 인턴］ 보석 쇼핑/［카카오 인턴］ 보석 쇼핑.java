import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        Map<String, Integer> gemCounts = new HashMap<>();
        for (String gem : gems) {
            gemCounts.put(gem, 0);
        }
        
        int gemTypeCount = gemCounts.size(); // 모든 보석의 종류의 수
        int nowTypeCount = 0;
        int idx = 0;
        gemCounts.clear();
        
        while (idx < gems.length && gemCounts.size() < gemTypeCount) {
            String gemType = gems[idx];
            
            countGem(gemType,  gemCounts);
            idx++;
        }
        
        int front = 0; // 시작; 제거하려는 보석의 위치
        int back = idx; // 끝 +1; 추가하려는 보석의 위치.
        // 즉, front <= x < back 구간 보석들만 카운팅된다.
        int minLen = back - front; 
        int[] answer = {front, back - 1};
        while (front < back && back <= gems.length) {
            
            // 앞부터 보석 제거
            while (front < back && gemCounts.size() == gemTypeCount) {
                if (minLen > (back - front)) {
                    minLen = back - front;
                    answer[0] = front; 
                    answer[1] = back - 1;
                    // System.out.println("minLen:"+ minLen);
                    // System.out.println("front:"+ front);
                    // System.out.println("back:"+ back);
                    // System.out.println();
                }
                
                uncountGem(gems[front], gemCounts);
                front++;
            }
            
            if (back == gems.length) break;
            
            // 뒤부터 보석 추가 -> 막 back이 gems.length가 된 경우, 앞부터 보석 제거 로직을 실행해야한다. 순서를 바꾸면 안됨.
            while (back < gems.length && gemCounts.size() < gemTypeCount) {
                
                String gemType = gems[back];
                
                countGem(gemType,  gemCounts);
                back++;
            }
        }
        
        // (인덱스 -> 번호) 변환
        answer[0] += 1;
        answer[1] += 1;
        return answer;
    }
    
    private void countGem(String gemType, Map<String, Integer> gemCounts) {
        int count = gemCounts.getOrDefault(gemType, 0);
        gemCounts.put(gemType, count + 1);
    }
    
    private void uncountGem(String gemType, Map<String, Integer> gemCounts) {
        int count = gemCounts.get(gemType);
        if (count == 1) gemCounts.remove(gemType);
        else gemCounts.put(gemType, count - 1);
    }
}
/**
투 포인터 + 보석 카운팅

1. 순회하며 보석 종류 세기
2. gems의 0부터 모든 보석한종류 이상 포함시까지 순회
- 순회하면서 각 보석 수 카운팅
3. 앞쪽 포인터를 제거하면서 가능여부 확인, 불가능되면 뒤쪽 포인트 이동

*/