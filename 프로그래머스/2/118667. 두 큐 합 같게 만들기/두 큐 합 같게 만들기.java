import java.util.*;

class Solution {
    
    private final int IMPOSSIBLE = -1;
    
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;
        long sum = 0;
        int n = queue1.length; // 조건에서 1,2의 길이는 같다.
        int maxJob = 3 * n;
        
        Queue<Integer> left = new LinkedList<>();
        Queue<Integer> right = new LinkedList<>();
        long lSum = 0;
        long rSum = 0;
        
        for (int i = 0; i < n; i++) {
            sum += queue1[i];
            sum += queue2[i];
            lSum += queue1[i];
            rSum += queue2[i];
            
            left.offer(queue1[i]);
            right.offer(queue2[i]);
        }
        
        if (sum % 2 == 1) return IMPOSSIBLE;
        
        long target = sum / 2;
        int job = 0;
        while (job++ <= maxJob) {
            if (lSum > rSum) {
                int poll = left.poll();
                lSum -= poll;
                rSum += poll;
                right.offer(poll);

                answer++;

            } else if (lSum == rSum) {
                System.out.println(answer);
                return answer;

            } else {
                int poll = right.poll(); 
                lSum += poll;
                rSum -= poll;
                left.offer(poll);

                answer++;
            }
        }
        
        // 총 길이 1.5배만큼 탐색 -> 한쪽큐에서 다른 큐로 모두 옮긴 다음, 다시 원래큐로 모두 옮길 수 있을 때
        // 결과를 못찾으면 실패다.
        return IMPOSSIBLE;
    }
}
/**
길이 1~30만

poll하면 무조건 다른 큐에 넣어야한다. 
[3, 2, 7, 2] [4, 6, 5, 1]
결국 일자 통행과 같으며, 앞구간의 합이 뒷구간의 합과 같아야함.

절반 크기의 slide window 문제이기도 한듯. %연산활용해서도 풀어보자.
slide를 하되, slide위치에 따라 계산해줘야할듯

*/