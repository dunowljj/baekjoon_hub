import java.util.*;

class Solution {
    
    public int solution(String[][] book_time) {
        int[][] book_minutes = new int[book_time.length][2];
        int answer = 0;
        
        // 종료 시간이 빠른 순서로 정렬
        PriorityQueue<Integer> endQueue = new PriorityQueue<>((m1, m2) -> m1 - m2);
        
        
        for (int i = 0; i < book_time.length; i++) {
            int startMinutes = getMinutes(book_time[i][0]);
            int endMinutes = getMinutes(book_time[i][1]) + 10;
            
            book_minutes[i][0] = startMinutes;
            book_minutes[i][1] = endMinutes;
        }
        
        // 시작 시간이 빠른 순서로 정렬, 시작 시간이 같으면 종료 시간이 빠른 순으로 정렬
        Arrays.sort(book_minutes, new Comparator<int[]>() {
            @Override
            public int compare(int[] t1, int[] t2) {
                if (t1[0] == t2[0]) {
                    return t1[1] - t2[1];
                }
                return t1[0] - t2[0];
            }
        });
        
        for (int[] time : book_minutes) {
            
            // 큐에 값이 있고, 구간이 겹치지 않을 때
            if (!endQueue.isEmpty() && time[0] >= endQueue.peek()) {
                endQueue.poll();
            }
        
            endQueue.offer(time[1]);
        }
                                                                        
        return endQueue.size();
    }
    
    private int getMinutes(String time) {
        String[] hoursAndMinutes = time.split(":");
        return Integer.parseInt(hoursAndMinutes[0]) * 60 + Integer.parseInt(hoursAndMinutes[1]);
    }
}

/*
동시에 겹치는 구간의 개수를 어떻게 구할까?
24 * 60개의 배열을 선언해서 카운트하면 해결되긴 한다.

-> 정렬활용 방법으로 수정
1. 분단위 int 값으로 시간을 변경한 배열을 만든다.
2. 만든 배열을 시작 시간이 빠른 순으로 정렬한다. 시작 시간이 같으면 끝 시간이 빠른 순으로 정렬한다.
3. 끝 시간을 넣기위한 우선순위 큐를 만든다. 빠른 시간이 우선된다.
4. 끝 시간과 시작 시간을 비교해서 범위가 겹치는지 판별한다. 끝 시간을 큐에 넣고, 겹치지 않는 경우만 끝 시간을 큐에서 뺀다.
5. 큐의 사이즈가 정답이 된다.

*/