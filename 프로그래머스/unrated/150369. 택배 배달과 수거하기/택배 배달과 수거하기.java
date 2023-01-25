import java.util.*;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long totalMove = 0L;
        Stack<BoxInfo> dStack = new Stack<>();
        Stack<BoxInfo> pStack = new Stack<>();
            
        for (int i = 0; i < n; i++) {
            int delivery = deliveries[i];
            int pickup = pickups[i];
            
            if (delivery != 0) dStack.push(new BoxInfo(i, delivery));
            if (pickup != 0) pStack.push(new BoxInfo(i, pickup));
        }
        
        int deliveryItem = 0;
        int remainCapacity = 0;
            
        // 두 스택에 모두 남은 값이 있는 경우 -> 배송이나 배달이 먼저 한쪽이 끝나면 탈출한다.
        while (!dStack.isEmpty() && !pStack.isEmpty()) {
            BoxInfo dInfo = dStack.peek();
            BoxInfo pInfo = pStack.peek();
            
            deliveryItem = cap;
            remainCapacity = cap;
            
            int distance = Math.max(dInfo.distance, pInfo.distance); // 가장 먼 거리
            totalMove += 2 * distance;
            
            // 싣어논 배송상품 모두 전달
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
            
            // 여유 공간만큼 박스 수거
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
        
        while (!dStack.isEmpty()) {
            BoxInfo dInfo = dStack.peek();
            
            totalMove += 2 * dInfo.distance;
            
            deliveryItem = cap;
            
            // 싣어논 배송상품 모두 전달
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
        
        while (!pStack.isEmpty()) {
            BoxInfo pInfo = pStack.peek();
                        
            totalMove += 2 * pInfo.distance;
            
            remainCapacity = cap;
            
            // 여유 공간만큼 박스 수거
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
        
        return totalMove;
    }
}

class BoxInfo {
    int distance;
    int count;

    // index + 1이 편도 거리
    BoxInfo(int distance, int count) {
        this.distance = distance + 1;
        this.count = count;
    }
}

/*
- 우선적으로 맨끝 집부터 처리한다.
- 종료를 알기 위해 배달/수거의 총량을 체크한다. 배달의 경우 가는길에 뒷집부터 처리한다.
- 남은 배달/수거의 위치를 고려해야 이동거리를 계산할 수 있다.
- 배달 포인터와 수거 포인터를 따로 만들기

단순하게 생각하면 어디까지 가야할까?를 알아내는 것이다.
1) 가장 멀리있는 배달/수거를 찾는다. 해당 지점으로 이동거리를 계산한다.
2) cap의 개수만큼 남은 배달/수거를 제거한다.
3) 반복해서 모든 배달/수거가 끝나면 종료한다.

*/