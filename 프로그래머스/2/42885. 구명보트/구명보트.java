import java.util.Arrays;

public class Solution {
    public int solution(int[] people, int limit) {
        Arrays.sort(people);

        int count = 0;
        int p1 = 0;
        int p2 = people.length - 1;
        while (p1 < p2) {
            if (limit >= people[p1] + people[p2]) {
                p1++;
                p2--;
                count++;
            } else {
                p2--;
                count++;
            }
        }

        // 한명 남은 경우
        if (p1 == p2) count++;

        return count;
    }
}

/**
 * 보트는 작아서 2명씩밖에 못탄다.
 * 가장 무거운 사람을 먼저처리하고, 남은 사람중 가장 가벼운 사람이 같이 탈 수 있는지 체크한다.
 *
 * 풀이 : 포인터 2개. 작은쪽과 큰쪽을 더하기
 *
 * 예시1 : [20,30,55,80]
 * 작은 값 20,30 부터 데려가면 55, 80의 경우 2번 왕복해야한다.
 * 큰 값을 먼저 처리해야 한다.
 */