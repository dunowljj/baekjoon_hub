import java.util.*;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long totalMove = 0L;
        
        /**
         * 거리가 먼 집이 마지막에 push되도록 초기화
         */
        Stack<Info> dStack = new Stack<>();
        Stack<Info> pStack = new Stack<>();
            
        for (int i = 0; i < n; i++) {
            int delivery = deliveries[i];
            int pickup = pickups[i];
            
            if (delivery != 0) dStack.push(new Info(i, delivery));
            if (pickup != 0) pStack.push(new Info(i, pickup));
        }
        
        /**
         * 두 스택에 모두 남은 값이 있는 경우 -> 배송이나 배달 중 한쪽만 먼저 끝나면 탈출한다.
         */
        int deliveryItem = 0;
        int remainCapacity = 0;
            
        while (!dStack.isEmpty() && !pStack.isEmpty()) {
            Info dInfo = dStack.peek();
            Info pInfo = pStack.peek();
            
            deliveryItem = cap;
            remainCapacity = cap;
            
            int distance = Math.max(dInfo.distance, pInfo.distance) + 1; // 가장 먼 거리
            totalMove += 2 * distance;
            
            // 싣어논 배송상품 모두 전달
            deliver(dInfo, dStack, deliveryItem);
            
            // 여유 공간만큼 박스 수거
            collect(pInfo, pStack, remainCapacity);
        }
        
        /**
         * 배달이나 수거 중 하나가 먼저 끝난 경우
         */
        
        // 배달만 남은 경우
        while (!dStack.isEmpty()) {
            Info dInfo = dStack.peek();
            
            totalMove += 2 * (dInfo.distance + 1);
            
            deliveryItem = cap;
            
            // 싣어논 배송상품 모두 전달
            deliver(dInfo, dStack, deliveryItem);
        }
        
        
        // 수거만 남은 경우
        while (!pStack.isEmpty()) {
            Info pInfo = pStack.peek();
                        
            totalMove += 2 * (pInfo.distance + 1);
            
            remainCapacity = cap;
            
            // 여유 공간만큼 박스 수거
            collect(pInfo, pStack, remainCapacity);
        }
        
        return totalMove;
    }
    
    private void deliver(Info dInfo, Stack<Info> dStack, int deliveryItem) {
        while (deliveryItem != 0) {
            int delivered = Math.min(dInfo.count, deliveryItem);

            dInfo.count -= delivered;
            deliveryItem -= delivered;

            if (dInfo.count == 0) {
                dStack.pop();                    

                if (dStack.isEmpty()) break;

                dInfo = dStack.peek();
            }
        }
    }
    
    private void collect(Info pInfo, Stack<Info> pStack, int remainCapacity) {
        while (remainCapacity != 0) {
            int pickuped = Math.min(pInfo.count, remainCapacity);

            pInfo.count -= pickuped;
            remainCapacity -= pickuped;

            if (pInfo.count == 0) {
                pStack.pop();

                if (pStack.isEmpty()) break;

                pInfo = pStack.peek();
            }
        }
    }
    
    // 사실상 함수로 만들면 같은 내용인데, 의미를 담아아해서 리팩터링 하는 법을 모르겠다.

}

class Info {
    int distance;
    int count;

    Info(int distance, int count) {
        this.distance = distance;
        this.count = count;
    }
}

/*
### 풀이 과정
맨 뒤는 어짜피 들려야 한다. 
- 배달 혹은 수거가 필요한 맨 뒤쪽의 집만 찾으면 된다.
- 가는길에 배달, 오는 길에 수거를 한다면 cap만큼 배달, 수거를 모두 할 수 있다.

남은 배달/수거의 위치를 고려해야 이동거리를 계산할 수 있다.
- 배달 포인터와 수거 포인터를 따로 만들기 --> 스택으로 변경

### 과정

1) 남은 배달/수거에 대한 정보(집과의 거리, 배달/수거 개수) 를 담은 두 개의 스택을 생성
2) 거리가 멀리있는 배달/수거가 마지막에 쌓이도록 stack에 초기화한다.
3) 두 스택의 최신값 중 거리가 더 먼 곳이 이번 배달/수거에 이동할 거리이다.
4) cap의 개수만큼 남은 배달/수거를 제거한다. (배달/수거 완료시 pop해서 제거)
5) 반복해서 모든 배달/수거가 끝나면 종료한다.
*/