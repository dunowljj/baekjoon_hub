import java.util.*;

import static java.util.Comparator.comparingInt;

class Solution {
    
    static class Food {
        int index;
        int time;
        
        Food(int index, int time) {
            this.index = index;
            this.time = time;
        }
        
         public int getIndex() {
            return index;
        }
        
        public int getTime() {
            return time;
        }
    }
    
    public int solution(int[] food_times, long k) {
        int n = food_times.length;
            
        // Food : 인덱스와 0을 제외한 시간을 가짐
        PriorityQueue<Food> pq = new PriorityQueue<>(comparingInt(Food::getTime));
        for (int i = 0; i < food_times.length; i++) {
            if (food_times[i] != 0) pq.offer(new Food(i, food_times[i])); 
        }
        
        int beforeTime = 0;
        while (!pq.isEmpty()) {
            Food now = pq.peek();
            int round = now.time - beforeTime; // 순회가능 바퀴 수
            if (round != 0) {
                long second = (long)round * pq.size(); // 바퀴 수 * 방문하는 원소 개수 = 지난 시간(초)
                // long 변환을 꼭 하자. 안하면 피본다.

                if (k >= second) {
                    k -= second;  
                    beforeTime = now.time;

                } else {
                    Food[] foods = pq.stream()
                        .sorted(comparingInt(Food::getIndex))
                        .toArray(Food[]::new);
                      
                    int remain = (int)(k % foods.length);
                    return foods[remain].index + 1;
                }
            }
            
            pq.poll();
        }
        // 다 비워도 k에 도달 불가 -> k 시점에 먹으려는 음식이 없다.
        // k==0인 경우도 마찬가지로 다음 음식이 없다.
        return -1;
    }
}

/*
pq에 index와 함께 시간 값을 넣으면 0을 생략하고 존재하는 값만 탐색할 수 있다.
*/