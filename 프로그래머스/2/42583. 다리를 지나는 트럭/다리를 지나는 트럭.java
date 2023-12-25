import java.util.LinkedList;
import java.util.Queue;

class Solution {

    public static final int EMPTY_SPACE = 0;

    public int solution(int bridgeLength, int weightLimit, int[] truckWeights) {
        int totalWeight = 0;
        int time = 0;
        Queue<Integer> bridge = new LinkedList<>();

        for (int i = 0; i < bridgeLength; i++) {
            bridge.offer(EMPTY_SPACE);
        }

        // 마지막 트럭 진입 시점까지 걸린 시간 구하기
        for (int readyTruckWeight : truckWeights) {

            /**
             * 7 0 0 0 | <- 4 5 6
             * 0 0 0 4 | <- 5 6
             * 0 0 4 5 | <- 6
             */
            int outTruckWeight = bridge.peek();

            // 트럭 진입 가능 -> 대기중인 트럭을 진입하며 1칸 전진
            if (weightLimit >= totalWeight + readyTruckWeight - outTruckWeight) {
                outTruckWeight = bridge.poll();
                bridge.offer(readyTruckWeight);

                totalWeight -= outTruckWeight;
                totalWeight += readyTruckWeight;
                time ++;
                continue;
            }

            // 트럭이 진입 불가
            // -> 진입 가능할때까지 다리에 트럭만 이동 (0 삽입)
            // -> 진입 가능해지면 대기중인 트럭을 진입하며 1칸 전진
            /**
             *  현재 진입하려는 트럭을 넣을 수 있을때까지 트럭들 이동
             *  예시
             *  0 0 4 5 | <- 6
             *  4 5 0 0 | <- 6
             *  5 0 0 0 | <- 6
             */

//10 < 7 + 4 - 0
            while (weightLimit < totalWeight + readyTruckWeight - outTruckWeight) {
                outTruckWeight = bridge.poll();
                bridge.offer(EMPTY_SPACE);
                totalWeight -= outTruckWeight;
                time ++;

                outTruckWeight = bridge.peek();
            }

            /**
             *  한칸 전진한다.
             *  선두 트럭이 맨 끝에있고, 건너면 다리에 여유가 생기는 상황.
             *  예시
             *  5 0 0 0 | <- 6
             *  0 0 0 6 |
             */
            outTruckWeight = bridge.poll();
            bridge.offer(readyTruckWeight);

            totalWeight -= outTruckWeight;
            totalWeight += readyTruckWeight;
            time ++;
        }

        return time + bridgeLength;
    }
}
