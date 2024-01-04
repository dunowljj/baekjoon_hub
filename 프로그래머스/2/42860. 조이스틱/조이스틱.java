import java.lang.Math;
import java.util.*;

class Solution {
    public int solution(String name) {
        int sum = 0;
        int len = name.length();
        int move = len - 1;

        for (int leftEnd = 0; leftEnd < len; leftEnd++) {
            // 상하 이동 값 추가
            sum += Math.min(name.charAt(leftEnd) - 'A', 'Z' - name.charAt(leftEnd) + 1);

            // 연속된 A 찾기
            int rightEnd = leftEnd + 1;
            while (rightEnd < len && name.charAt(rightEnd) == 'A') {
                rightEnd++;
            }

            // 좌우 이동 값 추가
            move = Math.min(move, (len - rightEnd) * 2 + leftEnd);
            move = Math.min(move, (leftEnd) * 2 + len - rightEnd);
        }
        sum += move;

        return sum;
    }
}
/**
 * 1) 첫 문자에서 마지막으로 jump했다가, 다시 jump해서 돌아오는 경우
 * 2) 첫 문자에서 순차적으로 진행하는 경우
 * 3) 첫 문자에서 순차적으로 진행하다가 특정 문자를 찍고 돌아와서 jump하는 경우
 * --> 1,3번은 진행 방향을 도중에 바꾼다. 하지만 최소거리이기 때문에 진행 방향을 2번 바꾸는 경우는 없다.
 * --> 진행 방향을 바꾸면 쓸데없는 중복 이동이 생기기 때문이다.
 *
 * 예시 1: B A B A A A A A B
 *
 * 결국 연속된 A가 가장 큰 경우를 무시하는 경우를 찾아야한다.
 * 예시 2: A B A A A A B A A -> 첫 B를 찍고, 뒤로 돌아서 다음 B를 찍는다.
 * 예시 3: A B A A A B A A A -> 첫 B를 찍고 그대로 전진해야한다.
 */