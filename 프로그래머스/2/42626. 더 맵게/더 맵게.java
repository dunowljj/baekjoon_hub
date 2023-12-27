import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    public static final int CANNOT_MAKE = -1;

    public int solution(int[] scoville, int K) {
        int answer = 0;

        Queue<Long> scovilles = new PriorityQueue<>();
        for (int value : scoville) scovilles.offer((long) value);

        while (scovilles.size() >= 2) {
            Long min = scovilles.poll();
            if (min >= K) return answer;

            long mixed = min + (scovilles.poll() * 2);
            scovilles.offer(mixed);

            answer ++;
        }

        // while을 순회하고 1개의 요소만 남은 경우 || 처음부터 1개의 요소만 주어진 경우를 고려
        // 하나 남은 스코빌 지수가 K 이상이라면 정답을 반환, 그렇지 않다면 -1을 반환한다.
        return (scovilles.poll() >= K) ? answer : CANNOT_MAKE;
    }
}
/**
 * 길이는 100만 이하
 * K는 10억 이하
 * 만들 수 없는 경우 -1 리턴
 */