import java.util.*;

class Solution {
    
    
    public int[] solution(String[] operations) {
        MinMaxHeap heap = new MinMaxHeap();
        
        for (String operation : operations) {
            String[] split = operation.split(" ");
            
            // insert
            if (split[0].equals("I")) {
                heap.insert(Integer.parseInt(split[1]));
            }
            
            // delete
            if (split[0].equals("D")) {
                if (split[1].charAt(0) == '-') heap.deleteMin();
                else heap.deleteMax();
            }
        }
        
        if (heap.size() == 0) return new int[]{0, 0};
        else return new int[]{heap.peekMax(), heap.peekMin()};
    }
    
    
    class MinMaxHeap {
        Map<Integer, Integer> countMap = new HashMap<>();
        Queue<Integer> minHeap = new PriorityQueue<>();
        Queue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        MinMaxHeap(){}
        
        public void insert(int num) {
            minHeap.add(num);
            maxHeap.add(num);

            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        public void deleteMin() {
            // 비어있다면 무시
            while (!minHeap.isEmpty()) {
                int min = minHeap.peek();

                // 사용해서 안되는 정보
                if (!countMap.containsKey(min)) {
                    minHeap.poll();

                // 사용가능
                } else {
                    countMap.put(min, countMap.get(min) - 1);
                    if (countMap.get(min) == 0) countMap.remove(min);
                    minHeap.poll();
                    return;
                }
            }
        }

        public void deleteMax() {
            // 비어있다면 무시
            while (!maxHeap.isEmpty()) {
                int max = maxHeap.peek();

                // 사용해서 안되는 정보
                if (!countMap.containsKey(max)) {
                    maxHeap.poll();

                // 사용가능
                } else {
                    countMap.put(max, countMap.get(max) - 1);
                    if (countMap.get(max) == 0) countMap.remove(max);
                    maxHeap.poll();
                    return;
                }
            }
        }

        public int peekMin() {
            while (!minHeap.isEmpty()) {
                int min = minHeap.peek();

                // 사용해서 안되는 정보
                if (!countMap.containsKey(min)) {
                    minHeap.poll();

                // 사용가능
                } else {
                    break;
                }
            }

            return minHeap.peek();
        }

        public int peekMax() {
            while (!maxHeap.isEmpty()) {
                int max = maxHeap.peek();

                // 사용해서 안되는 정보
                if (!countMap.containsKey(max)) {
                    maxHeap.poll();
                // 사용가능
                } else {
                    break;
                }
            }

        return  maxHeap.peek();
        }
        
        public int size() {
            return countMap.size();
        }
    }
}
/**

**/