import java.util.*;

class Solution {
    
    List<Long> answer;
    Map<Long,Long> reserveMap = new HashMap<>();
    
    public long[] solution(long k, long[] room_number) {
        answer = new ArrayList<>();
        
        for (long requestRoomNo : room_number) {
           long find = findRoom(requestRoomNo);
           answer.add(find);
           reserveMap.put(find, find + 1);
        }
        
        // for (Map.Entry<Long,Long> e : reserveMap.entrySet()) {
        //     System.out.println("k:"+e.getKey()+",v:"+e.getValue());
        // }
        
        return answer.stream().mapToLong(Long::longValue).toArray();
    }
    
    // 요청에 대한 빈방을 찾는다. 빈방을 찾으면서 경로를 압축한다.
    private Long findRoom(long requestNo) {
        
        // 빈방
        if (!reserveMap.containsKey(requestNo)) {
            return requestNo;
        }
        
        // 빈방 아님
        long find = findRoom(reserveMap.get(requestNo));
        reserveMap.put(requestNo, find + 1);
        return find;
    }
    
//     private Long find(long requestNo) {
//         if (!reserveMap.containsKey(requestNo)) {
//             return requestNo;
//         }

//         return find(reserveMap.get(requestNo));
//     }

//     private void reserve(long requestNo) {
//         reserveMap.put(reqeustNo, find(reuestNo + 1));
//     }
}

/**
1~k번 방 존재
처음에 모두 비어있음
1명씩 신청, 방 번호 제출 -> 방이 있으면 배정 -> 이미 배정되어있으면 더 큰 방 중 가장 작은 방 배정

특정 방을 가르키면, 다음 빈 방을 가르키도록 유도해야함.

### 시간제한 분석
k 범위 1 ~ 10^12
- 배열 선언 메모리 초과
- HashSet에 값을 넣고, 1씩 증가시킨다? -> 요청이 1번방만 20만번 들어온다면,  (1+20만)*10만 -> 시간초과
- HashMap<Long,Long> Key:예약된 방, Value:다음 예약할 방. 단, 경로를 압축시키면서 갱신하자.


*/
