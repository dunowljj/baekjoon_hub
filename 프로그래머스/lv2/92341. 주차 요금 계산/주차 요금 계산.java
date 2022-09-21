import java.util.*;

class Record {
    int time;
    int carNo;
    
    Record(int time, int carNo) {
        this.time = time;
        this.carNo = carNo;
    }
}

class Solution {
    public int[] solution(int[] fees, String[] records) {
        Map<Integer, Integer> answerMap = new HashMap();
        Set<Integer> carNoSet = new HashSet();
        
        PriorityQueue<Record> queue = new PriorityQueue(new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                if (r1.carNo == r2.carNo) {
                    return r1.time - r2.time;
                }
                return r1.carNo - r2.carNo;
            }
        });
        
        for (int i = 0; i < records.length; i++) {
            String[] inputs = records[i].split(" ");
            String[] timing = inputs[0].split(":");
            int minute = Integer.parseInt(timing[0]) * 60 + Integer.parseInt(timing[1]);
            int carNo = Integer.parseInt(inputs[1]);
            
            queue.offer(new Record(minute, carNo));
            carNoSet.add(carNo);
        }
        
        while (!queue.isEmpty()) {
            Record record = queue.poll();
            int carNo = record.carNo;
            int timeDiff = 0;
            int totalFee = 0;
            // 뽑은 값과 다음값 비교 -> 다음 값과 차번호가 같다면 시간의 차 구하기 -> 합에 더하기
            if (!queue.isEmpty() && queue.peek().carNo == carNo) {
                timeDiff = queue.poll().time - record.time;
            }
            
            // 큐가 비어있거나, 다음 값과 차 번호가 다르다면, 마지막에 출차한 것으로 처리하기 -> 합에 더하고 초기화
            else {
                timeDiff = 1439 - record.time;
            }
            
            //누적 주차시간을 구하는 것이지 각각의 계산을 구하는 것이 아니다.
            answerMap.put(carNo, answerMap.getOrDefault(carNo, 0) + timeDiff);
        }
        
        List<Integer> carNoList = new ArrayList(carNoSet);
        Collections.sort(carNoList);
        
        int[] answer = new int[carNoList.size()];
        for (int i = 0; i < answer.length; i++) {
            int timeDiff = answerMap.get(carNoList.get(i));
            answer[i] = calculateFee(timeDiff, fees);
        }
        
        return answer;
    }
    
    private int calculateFee(int timeDiff, int[] fees) {
        // 기본요금
        if (timeDiff <= fees[0]) {
            return fees[1];
        }
        
        // 기본 시간과 기본 요금 미리 처리
        int total = 0;
        total += fees[1]; 
        timeDiff -= fees[0];
        
        int additionalFee = timeDiff / fees[2] * fees[3];
        int remain = (timeDiff % fees[2] == 0) ? 0 : fees[3];
        
        return total + additionalFee + remain;
    }
}

/*
입차 후 출차된 내역이 없다면, 23:59에 출차

초과한 시간이 단위 시간으로 나누어 떨어지지 않으면, 올림합니다.
=>  154 / 10 -> 15.4 안떨어지므로 16 -> 16 * 600 => 5000 + 9600 

하루 동안의 입/출차만 기록 다음날 x
같은 시각에 같은 차량번호 내역 2번 x
(23:59)마지막 시각에 입차 x
재입차나 재출차 x

해당 차량번호에 대해서 입차~출차까지 시간을 구해서 계산 -> 출차가 없는 경우를 계산해야함

방법 1. 차량번호로 정렬, 같으면 시각을 0시 부터 시간으로 변환해서 정렬 -> 재입차 재출차가 없으므로, 입차, 출차 순으로 자연 정렬된다.
다음 인덱스의 차량과 번호를 비교, 같으면 입/출차 계산 후, 인덱스 추가 // 다르면 출차처리 후 넘어간다 -> 결과 배열에 저장 -> 마지막 인덱스에 걸리는 것 주의
-> 우선순위 큐를 사용했더니 정답을 도출할때 비효율이 초래된다?

방법 2. TreeMap에 순차적 추가, 객체(시간, 입/출여부)를 넣고 IN, OUT 체크를 통해 정산 -> 검색을 통해 리스트 저장후 변환해서 반환
*/