import java.util.*;

class Solution {
    public int[] solution(String s) {
        PriorityQueue<Element> pq = new PriorityQueue<Element>((o1, o2) -> o2.count - o1.count);
        int[] counts = new int[100_001]; //counting 배열
        
        // 숫자값만 남도록 자름
        String[] nums = s.replaceAll("\\{", "")
            .replaceAll("\\}", "")
            .split(",");
        
        // 해당 숫자값 counting
        for (String num : nums) {
            int value = Integer.parseInt(num);
            counts[value]++;
        }
        

        // 우선순위큐에 객체로 삽입
        for (int value = 0; value <= 100_000; value++) {
            int count = counts[value];
            
            if (count == 0) continue;
            pq.offer(new Element(value, count));
        }
        
        // 정답 생성
        int[] answer = new int[pq.size()];
        int idx = 0;
        while (!pq.isEmpty()) {
            answer[idx++] = pq.poll().value;
        }
        
        return answer;
    }
}

class Element {
    int value;
    int count;
    
    Element(int value, int count) {
        this.value = value;
        this.count = count;
    }
}
/*
각 수의 개수를 세야한다.
replace100만 -> 카운트 배열 -> 10만개 한바퀴 돌면서 최대 500개 수 우선순위 큐에 저장 -> 순차적으로 배열에 저장
*/