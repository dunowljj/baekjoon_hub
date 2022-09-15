import java.util.Queue;
import java.util.LinkedList;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        
        long sum1 = 0L;
        Queue<Integer> q1 = new LinkedList();
        for (int i = 0; i < queue1.length; i++) {
            int curr = queue1[i];
            sum1 += curr;
            q1.offer(curr);
        }
        
        long sum2 = 0L;
        Queue<Integer> q2 = new LinkedList();
        for (int i = 0; i < queue2.length; i++) {
            int curr = queue2[i];
            sum2 += curr;
            q2.offer(curr);
        }
        
        int len = q1.size() * 4;
        for (int i = 0; i < len; i++) {
            if (sum1 > sum2) {
               if (q1.size() == 1) return -1;
                
                int curr = q1.poll();
                sum1 -= curr;

                q2.offer(curr);
                sum2 += curr;
            }
            
            else if (sum1 == sum2) {
                return i;
            }
            
            else {
                 if (q2.size() == 1) return -1;
                
                int curr = q2.poll();
                sum2 -= curr;

                q1.offer(curr);
                sum1 += curr;
            }
        }
        return -1;
    }
}   
/*
방향이 정해져있기 때문에 하나의 큐에서 뽑아서 맨 뒤에 넣으면서 연속된 합이 반으로 나뉠수 있는 경우를 구하는 것과 같다.
큐를 한바퀴(두 큐의 길이의 합만큼) 작업했을때 합을 구하지 못하면, 불가능이다.
값에 따라 한바퀴 이상 돌려야하는 경우가 있다.


최소횟수?
합이 큰 경우에서 작은 경우로 옮기면서 합계산
두 큐의 사이즈만큼 반복, 두 큐 초기 길이는 같음.

큐 길이가 1?
[1] [2]
-> [1, 2] [] 1회
-> [2] [1] 2회 -> 종료



*/