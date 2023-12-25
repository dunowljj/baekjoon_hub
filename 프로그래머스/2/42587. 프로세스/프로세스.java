import java.util.*;
import java.util.stream.*;

class Solution {
    public int solution(int[] priorities, int location) {
        final int n = priorities.length;
        
        Queue<Process> readyQueue = new LinkedList<>();
        Queue<Process> completed = new LinkedList<>();
        
        // 우선수위 큐 혹은 힙을 사용 <우선순위, 남은갯수>
        TreeMap<Integer, Integer> treeMap = new TreeMap<>(
            Arrays.stream(priorities)
                .boxed()
                .collect(Collectors.groupingBy(
                    p -> p,
                    Collectors.summingInt(p -> 1)
                ))
        );
        
        IntStream.range(0, n)
            .forEach(i -> readyQueue.offer(new Process(priorities[i], i)));
        
        while (!readyQueue.isEmpty()) {
            Process poll = readyQueue.poll();
            int priority = poll.priority;
            
            // 더 큰 우선순위가 없는 경우
            if (treeMap.higherKey(priority) == null)  {
                int count = treeMap.get(priority);
            
                if (count == 1) treeMap.remove(priority);
                else treeMap.put(priority, count - 1);
            
                completed.offer(poll);
            // 더 큰 우선순위가 있는 경우 -> 다시 맨뒤에 작업 대기시키기
            } else {
                readyQueue.offer(poll);
            }
        }
        
        int order = 1;
        while (!completed.isEmpty()) {
            Process poll = completed.poll();
            
            if (poll.location == location) {
                return order;  
            }
            
            order++;
        }
        
        return -1;
    }
    
    static class Process {
        int priority;
        int location;
        
        public Process(int priority, int location) {
            this.priority = priority;
            this.location = location;
        }
    }
}
/**
방법 1 우선순위를 미리 Map에 카운트하고 큐 사용하기
방법 2 

1 2 3 4 5
-> 5 1 2 3 4
-> 4 1 2 3
n + n - 1 + n - 2... 2 -> n^2 -> 100^2

*/