import java.util.*;

class Solution {
    private static final Map<Long, Long> map = new HashMap<>();
    
    public long[] solution(long k, long[] room_number) {
        int n = room_number.length;
        long[] answer = new long[room_number.length];
        
        for (int i = 0; i < n; i++) {
            long requestNumber = room_number[i];
            answer[i] = find(requestNumber);
        }        
        
        return answer;
    }
    
    private long find(long roomNumber) {
        if (!map.containsKey(roomNumber)) {
            map.put(roomNumber, roomNumber + 1);
            return roomNumber;
        }
        
        // 경로 압축
        long next = find(map.get(roomNumber));
        map.put(roomNumber, next);
        return next;
    }
}
/**
k 10^12로 1조다. 4b kb mb gb
TreeSet쓰면 초기화 비용이 너무 크다. 배열을 쓴다면 4gb?

Set에 넣고 체크하기엔, 시간 오래걸린다.
10만개 요소가 연속하여 set에 있고, 그중 제일 낮은 방을 예약하려한다면 10만개 탐색해야한다.
연결시켜서 마지막 요소의 참조를 공유하게 할수는 없나? 값을 넣으면서 다음 요소중 가장 큰 요소를 부모로 삼게한다.

**/